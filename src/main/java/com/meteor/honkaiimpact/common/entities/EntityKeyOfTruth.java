package com.meteor.honkaiimpact.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityKeyOfTruth extends Entity {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TARGET = "target";
    private static final String TAG_TYPE = "type";

    private static PlayerEntity owner;
    public boolean shoot = false;
    private int countdown = 5;
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityKeyOfTruth.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityKeyOfTruth.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityKeyOfTruth.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Integer> TARGET = EntityDataManager.createKey(EntityKeyOfTruth.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityKeyOfTruth.class,
            DataSerializers.VARINT);

    public EntityKeyOfTruth(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntityKeyOfTruth(World worldIn, PlayerEntity owner) {
        super(ModEntities.KEY_OF_TRUTH, worldIn);
        this.owner = owner;
    }

    @Override
    public void registerData() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(DAMAGE, 0F);
        dataManager.register(PITCH, 0F);
        dataManager.register(TARGET, -1);
        dataManager.register(TYPE, 0);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (owner == null) {
            this.remove();
            return;
        }

        if (owner != null && getTarget() == -1 && owner.getLastAttackedEntity() != null && owner.getLastAttackedEntity().canEntityBeSeen(owner))
            setTarget(owner.getLastAttackedEntity().getEntityId());

        if (getTarget() == -1)
            for (LivingEntity living : getEntitiesAround()) {
                if (living == owner)
                    continue;
                if (living instanceof IMob) {
                    setTarget(living.getEntityId());
                    break;
                }
            }

        Entity target = world.getEntityByID(getTarget());

        if (target == null)
            remove();

        if (target != null && target.isLiving()) {
            this.faceEntity(target, 360F, 360F);
        } else {
            this.remove();
        }

        setRotation(MathHelper.wrapDegrees(-this.rotationYaw + 180));
        setPitch(-this.rotationPitch + 360);

        if (ticksExisted % 10 == 0) {
            this.shoot = true;
            this.countdown = 4;
            //world.playSound(posX, posY, posZ, ModSounds.shoot, SoundCategory.PLAYERS, 0.45F, 1F, true);
        }

        if (this.countdown > 0) {
            this.countdown--;
            if (target != null && target.isLiving())
                target.attackEntityFrom(DamageSource.causePlayerDamage(owner).setDamageBypassesArmor().setDamageIsAbsolute(), 5F);
        }

        if (this.countdown == 0)
            this.shoot = false;

        if (ticksExisted >= 45)
            this.remove();
    }

    public List<LivingEntity> getEntitiesAround() {
        BlockPos source = this.getPosition();
        float range = 12F;
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
        double d0 = entityIn.getPosX() - this.getPosX();
        double d2 = entityIn.getPosZ() - this.getPosZ();
        double d1;
        if (entityIn instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entityIn;
            d1 = livingentity.getPosYEye() - this.getPosYEye();
        } else {
            d1 = (entityIn.getBoundingBox().minY + entityIn.getBoundingBox().maxY) / 2.0D - this.getPosYEye();
        }

        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f1, maxPitchIncrease);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f, maxYawIncrease);
    }

    private float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float f = MathHelper.wrapDegrees(targetAngle - angle);
        if (f > maxIncrease) {
            f = maxIncrease;
        }

        if (f < -maxIncrease) {
            f = -maxIncrease;
        }

        return angle + f;
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        setRotation(cmp.getFloat(TAG_ROTATION));
        setDamage(cmp.getFloat(TAG_DAMAGE));
        setPitch(cmp.getFloat(TAG_PITCH));
        setTarget(cmp.getInt(TAG_TARGET));
        setKeyType(cmp.getInt(TAG_TYPE));
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_DAMAGE, getDamage());
        cmp.putFloat(TAG_PITCH, getPitch());
        cmp.putInt(TAG_TARGET, getTarget());
        cmp.putInt(TAG_TYPE, getKeyType());
    }

    public int getKeyType() {
        return dataManager.get(TYPE);
    }

    public void setKeyType(int rot) {
        dataManager.set(TYPE, rot);
    }

    public int getTarget() {
        return dataManager.get(TARGET);
    }

    public void setTarget(int rot) {
        dataManager.set(TARGET, rot);
    }

    public float getRotation() {
        return dataManager.get(ROTATION);
    }

    public void setRotation(float rot) {
        dataManager.set(ROTATION, rot);
    }

    public float getPitch() {
        return dataManager.get(PITCH);
    }

    public void setPitch(float rot) {
        dataManager.set(PITCH, rot);
    }

    public float getDamage() {
        return dataManager.get(DAMAGE);
    }

    public void setDamage(float delay) {
        dataManager.set(DAMAGE, delay);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}

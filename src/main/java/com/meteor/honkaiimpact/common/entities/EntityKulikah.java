package com.meteor.honkaiimpact.common.entities;

import com.meteor.honkaiimpact.common.core.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityKulikah extends BoatEntity {

    private static final String TAG_PITCH = "pitch";
    private static final String TAG_ROTATION = "rotation";

    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.FLOAT);

    public EntityKulikah(EntityType<? extends EntityKulikah> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public EntityKulikah(World worldIn, double x, double y, double z) {
        super(ModEntities.MOTOR, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @Override
    protected SoundEvent getPaddleSound() {
        return null;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ROTATION, 0F);
        this.dataManager.register(PITCH, 0F);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        return 0.225D;
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public Status getBoatStatus() {
        BoatEntity.Status boatentity$status = this.getUnderwaterStatus();
        if (boatentity$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return boatentity$status;
        } else if (this.checkInWater()) {
            return BoatEntity.Status.IN_WATER;
        } else {
            float f = this.getBoatGlide();
            if (f > 0.0F) {
                this.boatGlide =  f;
                return BoatEntity.Status.ON_LAND;
            } else {
                return BoatEntity.Status.IN_AIR;
            }
        }
    }

    @Override
    public void updateMotion() {
        double d0 = -0.03999999910593033D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        this.momentum = 0.05F;

        if (this.previousStatus == BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.IN_AIR && this.status != BoatEntity.Status.ON_LAND) {
            this.waterLevel = this.getBoundingBox().minY + (double)this.getHeight();
            this.setPosition(this.getPosX(), (double)(this.getWaterLevelAbove() - this.getHeight()) + 0.101D, this.getPosZ());
            this.setMotion(this.getMotion().mul(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = BoatEntity.Status.IN_WATER;
        } else {
            if (this.status == BoatEntity.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getBoundingBox().minY + 0.1D) / (double)this.getHeight();
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.UNDER_WATER) {
                d2 = 0.009999999776482582D;
                this.momentum = 0.8F;
            } else if (this.status == BoatEntity.Status.IN_AIR) {
                this.momentum = 0.9F;
            } else if (this.status == BoatEntity.Status.ON_LAND) {
                this.momentum = 0.9F;
                if (this.getControllingPassenger() instanceof PlayerEntity) {
                    this.boatGlide /= 2.0F;
                }
            }

            Vec3d vec3d = this.getMotion();
            this.setMotion(vec3d.x * (double)this.momentum, vec3d.y + d1, vec3d.z * (double)this.momentum);
            this.deltaRotation *= this.momentum;
            if (d2 > 0.0D) {
                Vec3d vec3d1 = this.getMotion();
                this.setMotion(vec3d1.x, (vec3d1.y + d2 * 0.06153846016296973D) * 0.75D, vec3d1.z);
            }
        }
    }

    @Override
    public void controlBoat() {
        if (this.isBeingRidden()) {
            float f = 0.0F;
            if (this.leftInputDown) {
                this.deltaRotation -= 1F;
            }

            if (this.rightInputDown) {
                this.deltaRotation += 1F;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
                f += 0.005F;
            }

            this.rotationYaw += this.deltaRotation;
            if (this.forwardInputDown) {
                f += 0.05F * 1.25F;
            }

            if (this.backInputDown) {
                f -= 0.006F;
            }

            this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (!player.isSecondaryUseActive()) {
            if (!this.world.isRemote && this.outOfControlTicks < 60.0F) {
                player.startRiding(this);
            }
            world.playSound(getPosX(), getPosY(), getPosZ(), ModSounds.rideon, SoundCategory.PLAYERS, 5F, 1F, false);
            return true;
        }
        return false;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 1 && passenger instanceof PlayerEntity;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        setRotation(compound.getFloat(TAG_ROTATION));
        setPitch(compound.getFloat(TAG_PITCH));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putFloat(TAG_ROTATION, getRotation());
        compound.putFloat(TAG_PITCH, getPitch());
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

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


}

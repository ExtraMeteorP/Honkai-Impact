package com.meteor.honkaiimpact.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityMotor extends BoatEntity {

    private static final String TAG_PITCH = "pitch";
    private static final String TAG_ROTATION = "rotation";

    private static final DataParameter<Integer> CYCLONE_TICKS = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.VARINT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityMotor.class,
            DataSerializers.FLOAT);

    public int ridingTicks = 0;

    public EntityMotor(EntityType<? extends EntityMotor> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

    public EntityMotor(World worldIn, double x, double y, double z) {
        super(ModEntities.MOTOR, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CYCLONE_TICKS, 0);
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


    public Item getItemBoat() {
        return Items.OAK_BOAT;
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = null;
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof PlayerEntity) {
            player = (PlayerEntity) this.getPassengers().get(0);

            if (this.getCycloneTicks() > 0) {
                setPitch(-5);
                if (this.getCycloneTicks() > 6)
                    setRotation(-5);
            }

            if (this.rightInputDown) {
                setRotation(-5);
            } else if (this.leftInputDown) {
                setRotation(5);
            } else {
                setRotation(0);
                setPitch(0);
            }

            if(this.forwardInputDown || this.backInputDown)
                ridingTicks++;

            if (this.forwardInputDown && this.collidedHorizontally) {
                this.setMotion(this.getMotion().x, this.getMotion().y+0.08f, this.getMotion().z);
            }

            for (LivingEntity living : getEntitiesAround()) {
                if (living == player)
                    continue;
                if (!living.canEntityBeSeen(player))
                    continue;
                if ((living instanceof IMob || player.getLastAttackedEntity() == living) && ticksExisted % 13 == 0
                ) {
                    EntityKeyOfTruth key = new EntityKeyOfTruth(world, player);
                    key.setPosition(player.getPosX() - Math.random() * 2F + 1F, player.getPosY() + 2.2F,
                            player.getPosZ() - Math.random() * 2F + 1F);
                    if (Math.random() < 0.5F)
                        key.setPosition(living.getPosX() - Math.random() * 2F + 1F, living.getPosY() + 2.2F,
                                living.getPosZ() - Math.random() * 2F + 1F);
                    key.rotationYaw = player.rotationYaw;
                    key.setPitch(-player.rotationPitch);
                    key.setRotation(MathHelper.wrapDegrees(-player.rotationYaw + 180));
                    if (!world.isRemote) {
                        world.addEntity(key);
                    }
                    break;
                }
            }

            if (ridingTicks >= 125) {

                if (this.getCycloneTicks() > 6) {
                    player.rotationYaw--;
                    player.setRotationYawHead(player.getRotationYawHead() - 1);
                    this.applyYawToEntity(player);
                }

                if (player.getHealth() < player.getMaxHealth() * 0.5F)
                    player.heal(0.5F);

                for (LivingEntity living : getEntitiesAround(player.getPosition(), 3F, player.world)) {
                    if (living == player) {
                        continue;
                    }
                    if (this.getCycloneTicks() > 10) {
                        living.attackEntityFrom(DamageSource.causePlayerDamage(player), 10F);
                        player.setLastAttackedEntity(living);
                    }
                }

                if (getCycloneTicks() > 0) {
                    setCycloneTicks(getCycloneTicks() - 1);
                    this.deltaRotation += 5F;
                }

            }

        }
    }

    public List<LivingEntity> getEntitiesAround() {
        return getEntitiesAround(this.getPosition(), 12F, this.world);
    }

    public static List<LivingEntity> getEntitiesAround(BlockPos source, float range, World world) {
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
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
        double d1 = this.hasNoGravity() ? 0.0D : d0;
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
                f += 0.05F;
            }

            if (this.backInputDown) {
                f -= 0.006F;
            }

            this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        setCycloneTicks(compound.getInt("cycloneTicks"));
        setRotation(compound.getFloat(TAG_ROTATION));
        setPitch(compound.getFloat(TAG_PITCH));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("cycloneTicks", getCycloneTicks());
        compound.putFloat(TAG_ROTATION, getRotation());
        compound.putFloat(TAG_PITCH, getPitch());
    }

    public void setCycloneTicks(int i) {
        this.dataManager.set(CYCLONE_TICKS, i);
    }

    public int getCycloneTicks() {
        return this.dataManager.get(CYCLONE_TICKS);
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

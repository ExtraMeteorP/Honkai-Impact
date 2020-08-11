package com.meteor.honkaiimpact.common.entities;

import com.meteor.honkaiimpact.common.handler.SupplyHandler;
import com.meteor.honkaiimpact.common.network.NetworkHandler;
import com.meteor.honkaiimpact.common.network.ParticlePack;
import com.mojang.datafixers.types.templates.CompoundList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Random;

public class EntitySupplyChest extends Entity {

    private static final String TAG_ROTATION = "rotation";
    private static final String TAG_PITCH = "pitch";
    private static final String TAG_TYPE = "type";
    private static final String TAG_TIMES = "times";
    private static final String TAG_CURTIMES = "curtimes";

    public static final int STANDARD_SUPPLY = 0;
    public static final int FOCUSED_SUPPLY = 1;

    public float prevLidAngle = 0F;
    public float lidAngle = 0F;
    private int animationTicks = 0;
    private PlayerEntity player;

    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntitySupplyChest.class,
            DataSerializers.VARINT);

    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntitySupplyChest.class,
            DataSerializers.FLOAT);

    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntitySupplyChest.class,
            DataSerializers.FLOAT);

    private static final DataParameter<Integer> TIMES = EntityDataManager.createKey(EntitySupplyChest.class,
            DataSerializers.VARINT);

    private static final DataParameter<Integer> CURTIMES = EntityDataManager.createKey(EntitySupplyChest.class,
            DataSerializers.VARINT);

    public EntitySupplyChest(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntitySupplyChest(World worldIn, PlayerEntity player,int type, int times) {
        super(ModEntities.SUPPLYCHEST, worldIn);
        setKeyType(type);
        setTimes(times);
        this.player = player;
    }

    @Override
    public void registerData() {
        dataManager.register(ROTATION, 0F);
        dataManager.register(PITCH, 0F);
        dataManager.register(TYPE, 0);
        dataManager.register(TIMES, 0);
        dataManager.register(CURTIMES, 0);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevLidAngle = this.lidAngle;

        /**
         * 开箱动画
         * **/
        this.lidAngle = Math.min(100, this.ticksExisted * 2.5F);
        setPitch(this.lidAngle);
        /**
         * 结算东西
         * **/
        if(this.lidAngle >= 60 && this.animationTicks <= 8 * getTimes()){
            if(this.world.isRemote && this.animationTicks % 2 == 0){
                world.addParticle(ParticleTypes.END_ROD, this.getPosX(), this.getPosY()+0.3F, this.getPosZ(), (0.5F-Math.random()) * 0.1F, 0.1F, (0.5F-Math.random()) * 0.1F);
            }
        }
        if(this.lidAngle >= 100){
            this.animationTicks++;
            if(this.animationTicks % 8 == 0 && this.getCurTimes() < this.getTimes()){
                ItemStack stack = ItemStack.EMPTY;
                if(player != null) {
                    if (getKeyType() == FOCUSED_SUPPLY)
                        stack = SupplyHandler.drawFromFocusedPool(player);
                    else if (getKeyType() == STANDARD_SUPPLY)
                        stack = SupplyHandler.drawFromStandardPool(player);
                }

               if(!world.isRemote){
                   NetworkHandler.sendToNearby(world, this.getPosition(), new ParticlePack(this.getPosX(), this.getPosY(),this.getPosZ(), stack.getRarity() == Rarity.EPIC));
               }

                if(!stack.isEmpty()){
                    ItemEntity itemStack = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), stack);
                    itemStack.setMotion((0.5F-Math.random()) * 0.15F, 0.42F, (0.5F-Math.random()) * 0.15F);
                    if(!this.world.isRemote) {
                        world.addEntity(itemStack);
                    }
                }
                setCurTimes(getCurTimes()+1);
            }

            if(getCurTimes() >= getTimes() && this.animationTicks >= 8 * getTimes() + 25) {
                for(int i = 0; i < 20; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.addParticle(ParticleTypes.POOF, this.getPosXRandom(1.0D), this.getPosYRandom(), this.getPosZRandom(1.0D), d0, d1, d2);
                }
                this.remove();
            }
        }
    }

    @Override
    public void readAdditional(CompoundNBT cmp) {
        setRotation(cmp.getFloat(TAG_ROTATION));
        setPitch(cmp.getFloat(TAG_PITCH));
        setKeyType(cmp.getInt(TAG_TYPE));
        setTimes(cmp.getInt(TAG_TIMES));
        setCurTimes(cmp.getInt(TAG_CURTIMES));
    }

    @Override
    public void writeAdditional(CompoundNBT cmp) {
        cmp.putFloat(TAG_ROTATION, getRotation());
        cmp.putFloat(TAG_PITCH, getPitch());
        cmp.putInt(TAG_TYPE, getKeyType());
        cmp.putInt(TAG_TIMES, getTimes());
        cmp.putInt(TAG_CURTIMES, getCurTimes());
    }

    public int getKeyType() {
        return dataManager.get(TYPE);
    }

    public void setKeyType(int rot) {
        dataManager.set(TYPE, rot);
    }

    public int getTimes() {
        return dataManager.get(TIMES);
    }

    public void setTimes(int rot) {
        dataManager.set(TIMES, rot);
    }

    public int getCurTimes() {
        return dataManager.get(CURTIMES);
    }

    public void setCurTimes(int rot) {
        dataManager.set(CURTIMES, rot);
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

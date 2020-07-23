package com.meteor.honkaiimpact.common.entities;

import com.meteor.honkaiimpact.common.core.CurioIntegration;
import com.meteor.honkaiimpact.common.core.ModSounds;
import com.meteor.honkaiimpact.common.handler.HerrscherHandler;
import com.meteor.honkaiimpact.common.items.ModItems;
import com.meteor.honkaiimpact.common.items.stigmata.ItemStigmata;
import com.meteor.honkaiimpact.common.potion.ModPotions;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntitySlash extends Entity {

    private PlayerEntity owner;
    private float damage = 3F;

    public EntitySlash(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public EntitySlash(World worldIn) {
        super(ModEntities.SLASH, worldIn);
    }

    public EntitySlash(World worldIn, PlayerEntity owner) {
        super(ModEntities.SLASH, worldIn);
        this.owner = owner;
    }

    @Override
    protected void registerData() {

    }

    @Override
    public void tick(){
        super.tick();

        if(this.ticksExisted == 1){
            world.playSound(getPosX(), getPosY(), getPosZ(), ModSounds.slash, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
        }

        for(LivingEntity entity : getEntitiesAround()){
            if(entity instanceof IMob){
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 3));
            }
        }

        if(this.ticksExisted == 6 || this.ticksExisted == 15 || this.ticksExisted == 23){
            damageAllAround(damage);
        }

        if(this.ticksExisted == 27 || this.ticksExisted == 34){
            damageAllAround(damage * 2);
        }

        if(this.ticksExisted >= 35)
            this.remove();
    }

    public void damageAllAround(float dmg){
        for(LivingEntity entity : getEntitiesAround()){
            if(owner != null){
                if(entity == owner)
                    continue;
                HerrscherHandler.thunderAttack(entity, owner, dmg);
                entity.hurtResistantTime = 0;
                if(ItemStigmata.has2SetStigmata(owner, ItemStigmata.EnumStigmataType.BENARES))
                    entity.addPotionEffect(new EffectInstance(ModPotions.lightningconducting, 140, 0));
            }
        }
    }

    public List<LivingEntity> getEntitiesAround() {
        BlockPos source = this.getPosition();
        float range = 3.5F;
        return world.getEntitiesWithinAABB(LivingEntity.class,
                new AxisAlignedBB(source.getX() + 0.5 - range, source.getY() + 0.5 - range, source.getZ() + 0.5 - range,
                        source.getX() + 0.5 + range, source.getY() + 0.5 + range, source.getZ() + 0.5 + range));
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

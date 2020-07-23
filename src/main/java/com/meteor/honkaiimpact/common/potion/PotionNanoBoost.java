package com.meteor.honkaiimpact.common.potion;

import com.meteor.honkaiimpact.common.core.DamageCalculator;
import com.meteor.honkaiimpact.common.items.stigmata.IDamageBooster;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class PotionNanoBoost extends Effect implements IDamageBooster {

    public PotionNanoBoost() {
        super(EffectType.BENEFICIAL, 0X00BFFF);
    }

    @Override
    public DamageCalculator getDamageBoost(PlayerEntity player) {
        return new DamageCalculator(0.6F, 0F, 0F, 0F);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn.isPotionActive(ModPotions.counter))
            entityLivingBaseIn.removePotionEffect(ModPotions.counter);
    }

}

package com.meteor.honkaiimpact.common.potion;

import com.meteor.honkaiimpact.common.items.stigmata.ItemStigmata;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionCounter extends Effect {

    public PotionCounter() {
        super(EffectType.BENEFICIAL, 0X00BFFF);
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event){
        if(event.getSource().getImmediateSource() != null && event.getSource().getImmediateSource() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getSource().getImmediateSource();
            if(ItemStigmata.has2SetStigmata(player, ItemStigmata.EnumStigmataType.WELTYANG)){
                if(!player.isPotionActive(ModPotions.nanoboost)){
                    if(!player.isPotionActive(ModPotions.counter)) {
                        player.addPotionEffect(new EffectInstance(ModPotions.counter, 1200, 0));
                    }else{
                        EffectInstance counter = player.getActivePotionEffect(ModPotions.counter);
                        player.addPotionEffect(new EffectInstance(ModPotions.counter, 1200, Math.min(14, counter.getAmplifier() + 1)));
                    }
                }
            }
        }
    }

}

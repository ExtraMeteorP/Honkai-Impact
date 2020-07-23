package com.meteor.honkaiimpact.common.potion;

import com.meteor.honkaiimpact.common.handler.HerrscherHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionLightningConducting extends Effect {

    public PotionLightningConducting() {
        super(EffectType.HARMFUL, 0x9400D3);
    }

    @SubscribeEvent
    public static void onDamageTaken(LivingHurtEvent event){
        if(event.getEntityLiving().isPotionActive(ModPotions.lightningconducting)){
            if(event.getSource().getImmediateSource() != null && event.getSource().getImmediateSource() instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) event.getSource().getImmediateSource();
                HerrscherHandler.thunderAttack(event.getEntityLiving(), player, event.getAmount() * 0.10F);
            }
            if(event.getSource().equals(HerrscherHandler.damageSource()))
                event.setAmount(event.getAmount() * 1.15F);
        }
    }

}

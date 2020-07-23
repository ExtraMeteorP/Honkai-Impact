package com.meteor.honkaiimpact.common.handler;

import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.common.entities.EntityMotor;
import com.meteor.honkaiimpact.common.network.HerrscherSkillPack;
import com.meteor.honkaiimpact.common.network.MotorUpdatePack;
import com.meteor.honkaiimpact.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class KeyInputHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity p = Minecraft.getInstance().player;
        if(p == null)
            return;
        Entity riding = p.getRidingEntity();
        if(riding == null)
            return;
        if (!(riding instanceof EntityMotor)) {
            return;
        }
        EntityMotor steerable = (EntityMotor) riding;
        steerable.updateInput(HonkaiImpact.keyFlight.isKeyDown(), HonkaiImpact.keyUp.isKeyDown());
        NetworkHandler.INSTANCE.sendToServer(new MotorUpdatePack(HonkaiImpact.keyFlight.isKeyDown(), HonkaiImpact.keyUp.isKeyDown()));
    }

}

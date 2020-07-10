package com.meteor.honkaiimpact;

import com.meteor.honkaiimpact.common.entities.EntityMotor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {

        PlayerEntity p = Minecraft.getInstance().player;
        Entity riding = p.getRidingEntity();
        System.out.println(114514);
        if (!(riding instanceof EntityMotor)) {
            return;
        }
        EntityMotor steerable = (EntityMotor) riding;

        steerable.updateInputs(HonkaiImpact.keyLeft.isKeyDown(), HonkaiImpact.keyRight.isKeyDown(),
                HonkaiImpact.keyForward.isKeyDown(), HonkaiImpact.keyBackward.isKeyDown());
        if(HonkaiImpact.keyFlight.isKeyDown() && steerable.getCycloneTicks() == 0)
            steerable.setCycloneTicks(15);
    }

}

package com.meteor.honkaiimpact.client.handler;

import com.meteor.honkaiimpact.common.handler.HerrscherHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HUDHandler {

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (Minecraft.getInstance().player == null ) {
            return;
        }

        if(HerrscherHandler.isHerrscherOfThunder(Minecraft.getInstance().player) && !Minecraft.getInstance().player.isCreative()) {
            HerrscherGUI gui = new HerrscherGUI();
            gui.render();
        }

        MotorGUI motorGui = new MotorGUI();
        motorGui.render();
    }

}

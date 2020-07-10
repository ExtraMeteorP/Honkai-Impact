package com.meteor.honkaiimpact.client;

import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.LayerHerrscher;
import com.meteor.honkaiimpact.client.renderer.RenderKeyOfTruth;
import com.meteor.honkaiimpact.client.renderer.RenderMotor;
import com.meteor.honkaiimpact.common.core.IProxy;
import com.meteor.honkaiimpact.common.entities.ModEntities;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy implements IProxy {

    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOTOR, RenderMotor::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.KEY_OF_TRUTH, RenderKeyOfTruth::new);
        Minecraft mc = Minecraft.getInstance();
        GameSettings gameSettings = mc.gameSettings;
        HonkaiImpact.keyForward = gameSettings.keyBindForward;
        HonkaiImpact.keyBackward = gameSettings.keyBindBack;
        HonkaiImpact.keyLeft = gameSettings.keyBindLeft;
        HonkaiImpact.keyRight = gameSettings.keyBindRight;
        HonkaiImpact.keyUp = gameSettings.keyBindJump;
        HonkaiImpact.keyFlight = gameSettings.keyBindSprint;

    }

    @Override
    public void registerHandlers() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::loadComplete);
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        DeferredWorkQueue.runLater(() -> {
            initAuxiliaryRender();
        });
    }

    private void initAuxiliaryRender() {
        Map<String, PlayerRenderer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
        PlayerRenderer render;
        render = skinMap.get("default");

        render.addLayer(new LayerHerrscher(render));

        render = skinMap.get("slim");

        render.addLayer(new LayerHerrscher(render));
    }

}

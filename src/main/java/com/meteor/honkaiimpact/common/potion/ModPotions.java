package com.meteor.honkaiimpact.common.potion;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import com.meteor.honkaiimpact.common.libs.LibPotionNames;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions {

    public static final Effect lightningconducting = new PotionLightningConducting().setRegistryName(LibMisc.MOD_ID, LibPotionNames.LIGHTNINGCONDUCTING);
    public static final Effect nanoboost = new PotionNanoBoost().setRegistryName(LibMisc.MOD_ID, LibPotionNames.NANOBOOST);
    public static final Effect counter = new PotionCounter().setRegistryName(LibMisc.MOD_ID, LibPotionNames.COUNTER);

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Effect> evt) {
        evt.getRegistry().register(lightningconducting);
        evt.getRegistry().register(nanoboost);
        evt.getRegistry().register(counter);
    }

}

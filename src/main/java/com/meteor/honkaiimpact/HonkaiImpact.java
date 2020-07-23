package com.meteor.honkaiimpact;

import com.meteor.honkaiimpact.client.ClientProxy;
import com.meteor.honkaiimpact.common.HonkaiImpactGroup;
import com.meteor.honkaiimpact.common.ServerProxy;
import com.meteor.honkaiimpact.common.core.CurioIntegration;
import com.meteor.honkaiimpact.common.core.IProxy;
import com.meteor.honkaiimpact.common.core.ModSounds;
import com.meteor.honkaiimpact.common.entities.ModEntities;
import com.meteor.honkaiimpact.common.items.ModItems;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibMisc.MOD_ID)
public class HonkaiImpact {

    public static ItemGroup itemGroup = new HonkaiImpactGroup();

    public static IProxy proxy;

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyForward;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyBackward;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyLeft;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyRight;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyUp;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyFlight;

    public HonkaiImpact() {
        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
        proxy.registerHandlers();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addGenericListener(EntityType.class, ModEntities::registerEntities);
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        modBus.addGenericListener(SoundEvent.class, ModSounds::registerSounds);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        CurioIntegration.init();
    }

}

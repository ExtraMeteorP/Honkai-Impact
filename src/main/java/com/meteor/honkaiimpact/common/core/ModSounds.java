package com.meteor.honkaiimpact.common.core;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds {

    public static final SoundEvent cyclone = makeSoundEvent("cyclone");
    public static final SoundEvent rideon = makeSoundEvent("rideon");
    public static final SoundEvent shoot = makeSoundEvent("shoot");
    public static final SoundEvent slash = makeSoundEvent("slash");

    private static SoundEvent makeSoundEvent(String name) {
        ResourceLocation loc = new ResourceLocation(LibMisc.MOD_ID, name);
        return new SoundEvent(loc).setRegistryName(loc);
    }

    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        IForgeRegistry<SoundEvent> r = evt.getRegistry();
        r.register(cyclone);
        r.register(rideon);
        r.register(shoot);
        r.register(slash);
    }

    private ModSounds() {}
}

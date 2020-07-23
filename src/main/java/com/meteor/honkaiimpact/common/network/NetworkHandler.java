package com.meteor.honkaiimpact.common.network;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(LibMisc.MOD_ID + ":networking"),
                () -> "1.0",
                (s) -> true,
                (s) -> true
        );
        INSTANCE.registerMessage(
                nextID(),
                HerrscherEnergyUpdatePack.class,
                HerrscherEnergyUpdatePack::toBytes,
                HerrscherEnergyUpdatePack::new,
                HerrscherEnergyUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                HerrscherSkillPack.class,
                HerrscherSkillPack::toBytes,
                HerrscherSkillPack::new,
                HerrscherSkillPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MotorUpdatePack.class,
                MotorUpdatePack::toBytes,
                MotorUpdatePack::new,
                MotorUpdatePack::handler
        );
    }

}

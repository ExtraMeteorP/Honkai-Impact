package com.meteor.honkaiimpact.common.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityHandler {

    @CapabilityInject(IHerrscherEnergy.class)
    public static Capability<IHerrscherEnergy> HERRSCHERENERGY_CAPABILITY;

    public static void register(){
        CapabilityManager.INSTANCE.register(
                IHerrscherEnergy.class,
                new Capability.IStorage<IHerrscherEnergy>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IHerrscherEnergy> capability, IHerrscherEnergy instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IHerrscherEnergy> capability, IHerrscherEnergy instance, Direction side, INBT nbt) {

                    }
                },
                () -> null
        );
    }

}

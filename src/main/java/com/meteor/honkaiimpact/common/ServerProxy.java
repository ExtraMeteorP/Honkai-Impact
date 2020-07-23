package com.meteor.honkaiimpact.common;

import com.meteor.honkaiimpact.common.capability.CapabilityHandler;
import com.meteor.honkaiimpact.common.core.IProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class ServerProxy implements IProxy {

    @Override
    public void registerHandlers() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        CapabilityHandler.register();
    }

}

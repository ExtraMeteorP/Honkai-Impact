package com.meteor.honkaiimpact.common.handler;

import com.meteor.honkaiimpact.common.items.ModItems;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onDragonDied(LivingDropsEvent event){
        if(event.getEntityLiving() instanceof EnderDragonEntity){
            if(!event.getEntityLiving().world.isRemote)
                event.getEntityLiving().entityDropItem(new ItemStack(ModItems.gemofconquest.get()));
        }
    }

    @SubscribeEvent
    public static void playerEvent(PlayerContainerEvent.Close event){
        if(HerrscherHandler.isHerrscherOfThunder(event.getPlayer())){

        }
    }

}

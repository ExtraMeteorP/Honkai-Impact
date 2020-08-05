package com.meteor.honkaiimpact.common;

import com.meteor.honkaiimpact.common.items.ModItems;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class Trades {

    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            List<VillagerTrades.ITrade> expert = event.getTrades().get(5);

            expert.add(new BasicTrade(8, new ItemStack(ModItems.focusedsupplycard.get()), 5, 4));
            expert.add(new BasicTrade(10, new ItemStack(ModItems.standardsupplycard.get()), 5, 5));
        }
    }

    @SubscribeEvent
    public static void onBossDied(LivingDeathEvent event) {
        if(!event.getEntityLiving().isNonBoss()){
            event.getEntityLiving().entityDropItem(new ItemStack(ModItems.focusedsupplycard.get(), 2));
            event.getEntityLiving().entityDropItem(new ItemStack(ModItems.standardsupplycard.get(), 2));
        }
    }

}

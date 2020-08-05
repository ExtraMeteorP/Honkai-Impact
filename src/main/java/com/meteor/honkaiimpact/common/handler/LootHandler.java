package com.meteor.honkaiimpact.common.handler;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;

public class LootHandler {

    public static void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:chests/";
        String name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "abandoned_mineshaft":
                case "desert_pyramid":
                case "jungle_temple":
                case "simple_dungeon":
                case "spawn_bonus_chest":
                case "stronghold_corridor":
                case "village_blacksmith":
                    evt.getTable().addPool(getInjectPool(file));
                    break;
                default:
                    break;
            }
        }
    }

    public static LootPool getInjectPool(String entryName) {
        return LootPool.builder()
                .addEntry(getInjectEntry(entryName, 1))
                .bonusRolls(0, 1)
                .name("honkaiimpact_inject")
                .build();
    }

    private static LootEntry.Builder<?> getInjectEntry(String name, int weight) {
        ResourceLocation table = new ResourceLocation(LibMisc.MOD_ID, "inject/" + name);
        return TableLootEntry.builder(table)
                .weight(weight);
    }

}

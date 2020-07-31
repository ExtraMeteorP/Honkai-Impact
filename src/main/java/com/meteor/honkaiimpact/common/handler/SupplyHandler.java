package com.meteor.honkaiimpact.common.handler;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplyHandler {

    private static final ResourceLocation focusNormalLootTable = new ResourceLocation(LibMisc.MOD_ID, "focusedsupplynormal");
    private static final ResourceLocation focusEpicLootTable = new ResourceLocation(LibMisc.MOD_ID, "focusedsupplyepic");

    private static final ResourceLocation standardNormalLootTable = new ResourceLocation(LibMisc.MOD_ID, "standardsupplynormal");
    private static final ResourceLocation standardEpicLootTable = new ResourceLocation(LibMisc.MOD_ID, "standardsupplyepic");

    private static final String TAG_FOCUSEDSUPPLY = "focusedSupplyTimes";
    private static final String TAG_STANDARDSUPPLY = "standardSupplyTimes";

    public static ItemStack generateStackFromLootTable(World world, ResourceLocation rl){
        ItemStack stack = ItemStack.EMPTY;
        do {
            LootContext ctx = new LootContext.Builder((ServerWorld) world).build(LootParameterSets.EMPTY);
            List<ItemStack> stacks = ((ServerWorld) world).getServer().getLootTableManager()
                    .getLootTableFromLocation(rl).generate(ctx);
            if (stacks.isEmpty()) {
                break;
            } else {
                Collections.shuffle(stacks);
                stack = stacks.get(0);
            }
        } while (stack.isEmpty());

        return stack;
    }

    public static ItemStack generateFocusedNormalSupply(World world){
        return generateStackFromLootTable(world, focusNormalLootTable);
    }

    public static ItemStack generateFocusedEpicSupply(World world){
        return generateStackFromLootTable(world, focusEpicLootTable);
    }

    public static List<ItemStack> generateFocusedPool(World world){
        List<ItemStack> pool = new ArrayList<>();
        if(!world.isRemote) {

            for(int i = 0; i < 9; i++) {
                ItemStack stack = generateFocusedNormalSupply(world);
                if (!stack.isEmpty()) {
                    pool.add(stack);
                }
            }

            ItemStack epic = generateFocusedEpicSupply(world);
            if (!epic.isEmpty()) {
                pool.add(epic);
            }
        }
        return pool;
    }

    public static ItemStack generateStandardNormalSupply(World world){
        return generateStackFromLootTable(world, standardNormalLootTable);
    }

    public static ItemStack generateStandardEpicSupply(World world){
        return generateStackFromLootTable(world, standardEpicLootTable);
    }

    public static List<ItemStack> generateStandardPool(World world){
        List<ItemStack> pool = new ArrayList<>();
        if(!world.isRemote) {

            for(int i = 0; i < 99; i++) {
                ItemStack stack = generateStandardNormalSupply(world);
                if (!stack.isEmpty()) {
                    pool.add(stack);
                }
            }

            ItemStack epic = generateStandardEpicSupply(world);
            if (!epic.isEmpty()) {
                pool.add(epic);
            }
        }
        return pool;
    }

    public static ItemStack drawFromFocusedPool(PlayerEntity player){
        CompoundNBT compoundNBT = player.getPersistentData();
        if(!compoundNBT.contains(TAG_FOCUSEDSUPPLY))
            compoundNBT.putInt(TAG_FOCUSEDSUPPLY, 0);
        int times = compoundNBT.getInt(TAG_FOCUSEDSUPPLY);
        if(times == 10) {
            compoundNBT.putInt(TAG_FOCUSEDSUPPLY, 0);
            return generateFocusedEpicSupply(player.world);
        }
        List<ItemStack> pool = generateFocusedPool(player.world);
        Collections.shuffle(pool);
        ItemStack supply = pool.get(0);
        if(supply.getRarity() == Rarity.EPIC){
            compoundNBT.putInt(TAG_FOCUSEDSUPPLY, 0);
        }else{
            compoundNBT.putInt(TAG_FOCUSEDSUPPLY, times + 1);
        }
        return supply;
    }

    public static ItemStack drawFromStandardPool(PlayerEntity player){
        CompoundNBT compoundNBT = player.getPersistentData();
        if(!compoundNBT.contains(TAG_STANDARDSUPPLY))
            compoundNBT.putInt(TAG_STANDARDSUPPLY, 0);
        int times = compoundNBT.getInt(TAG_STANDARDSUPPLY);
        if(times == 100) {
            compoundNBT.putInt(TAG_STANDARDSUPPLY, 0);
            return generateStandardEpicSupply(player.world);
        }
        List<ItemStack> pool = generateStandardPool(player.world);
        Collections.shuffle(pool);
        ItemStack supply = pool.get(0);
        if(supply.getRarity() == Rarity.EPIC){
            compoundNBT.putInt(TAG_STANDARDSUPPLY, 0);
        }else{
            compoundNBT.putInt(TAG_STANDARDSUPPLY, times + 1);
        }
        return supply;
    }

}

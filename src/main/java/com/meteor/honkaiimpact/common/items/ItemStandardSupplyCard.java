package com.meteor.honkaiimpact.common.items;

import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.common.handler.SupplyHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemStandardSupplyCard extends Item {

    public ItemStandardSupplyCard() {
        super(new Properties().group(HonkaiImpact.itemGroup).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        int times = playerIn.isSneaking() ? Math.min(10, itemstack.getCount()) : 1;
        if(!world.isRemote) {
            for(int i = 0; i < times; i++) {
                ItemStack stack = SupplyHandler.drawFromStandardPool(playerIn);

                if (!stack.isEmpty()) {
                    playerIn.entityDropItem(stack);
                }
            }
            itemstack.shrink(times);
            return ActionResult.resultSuccess(itemstack);
        }
        return super.onItemRightClick(world, playerIn, handIn);
    }

}

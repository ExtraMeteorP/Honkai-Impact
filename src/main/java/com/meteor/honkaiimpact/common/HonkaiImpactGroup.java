package com.meteor.honkaiimpact.common;

import com.meteor.honkaiimpact.common.items.ModItems;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class HonkaiImpactGroup extends ItemGroup {

    public HonkaiImpactGroup() {
        super(LibMisc.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.gemofconquest.get());
    }
}

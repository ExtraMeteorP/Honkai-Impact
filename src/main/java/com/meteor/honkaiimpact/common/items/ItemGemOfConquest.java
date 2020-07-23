package com.meteor.honkaiimpact.common.items;

import com.meteor.honkaiimpact.HonkaiImpact;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class ItemGemOfConquest extends Item {

    public ItemGemOfConquest() {
        super(new Properties().group(HonkaiImpact.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
    }

}

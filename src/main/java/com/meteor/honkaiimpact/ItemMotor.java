package com.meteor.honkaiimpact;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class ItemMotor extends Item {

    public ItemMotor() {
        super(new Properties().group(ItemGroup.TOOLS).rarity(Rarity.EPIC));
    }

}

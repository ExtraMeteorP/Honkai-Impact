package com.meteor.honkaiimpact.common.items;

import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.common.core.DamageCalculator;
import com.meteor.honkaiimpact.common.items.stigmata.IDamageBooster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;

public class ItemDomainOfSanction extends SwordItem implements IDamageBooster {

    public ItemDomainOfSanction() {
        super(ItemTier.DIAMOND, 4, -2F, new Properties().group(HonkaiImpact.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
    }

    @Override
    public DamageCalculator getDamageBoost(PlayerEntity player) {
        return new DamageCalculator(0F,0F,0.5F,0F,0F);
    }
}

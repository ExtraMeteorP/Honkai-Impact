package com.meteor.honkaiimpact.common.items.stigmata;

import com.meteor.honkaiimpact.common.core.DamageCalculator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemBenaresT extends ItemBenares {

    public ItemBenaresT() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    public void addHiddenTooltip(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benarest").applyTextStyle(TextFormatting.AQUA));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benarestdesc0"));
    }

    @Override
    public DamageCalculator getDamageBoost(PlayerEntity player){
        return new DamageCalculator(0.2F,0F,0F,0F, 0F);
    }

}

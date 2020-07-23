package com.meteor.honkaiimpact.common.items.stigmata;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemWeltYang extends ItemStigmata{

    public ItemWeltYang() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    public void addSetTooltip(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.weltyang2set").applyTextStyle(TextFormatting.AQUA));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.weltyang2setdesc0"));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.weltyang2setdesc1"));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.weltyang3set").applyTextStyle(TextFormatting.AQUA));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.weltyang3setdesc0"));
    }

    @Override
    public EnumStigmataType getStigmataType(){
        return EnumStigmataType.WELTYANG;
    }

}

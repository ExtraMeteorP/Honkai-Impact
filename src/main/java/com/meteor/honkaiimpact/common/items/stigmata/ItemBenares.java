package com.meteor.honkaiimpact.common.items.stigmata;

import com.meteor.honkaiimpact.common.core.CurioIntegration;
import com.meteor.honkaiimpact.common.handler.HerrscherHandler;
import com.meteor.honkaiimpact.common.items.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ItemBenares extends ItemStigmata{

    public ItemBenares() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    public void addSetTooltip(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benares2set").applyTextStyle(TextFormatting.AQUA));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benares2setdesc0"));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benares2setdesc1"));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benares3set").applyTextStyle(TextFormatting.AQUA));
        stacks.add(new TranslationTextComponent("honkaiimpactmisc.benares3setdesc0"));
    }

    @Override
    public EnumStigmataType getStigmataType(){
        return EnumStigmataType.BENARES;
    }

    @SubscribeEvent
    public static void onDamageTaken(LivingHurtEvent event){
        if(event.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if(has3SetStigmata(player, EnumStigmataType.BENARES)){
                event.setAmount(event.getAmount() * 0.75F);
            }
        }
    }

}

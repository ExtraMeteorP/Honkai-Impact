package com.meteor.honkaiimpact.common.items.stigmata;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.honkaiimpact.HonkaiImpact;
import com.meteor.honkaiimpact.common.core.CurioIntegration;
import com.meteor.honkaiimpact.common.core.DamageCalculator;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ItemStigmata extends Item implements IDamageBooster{

    public ItemStigmata() {
        super(new Item.Properties().group(HonkaiImpact.itemGroup).rarity(Rarity.EPIC).maxStackSize(1).setNoRepair());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {
        if (Screen.hasShiftDown()) {
            addHiddenTooltip(stack, world, stacks, flags);
            addSetTooltip(stack, world, stacks, flags);
        } else {
            stacks.add(new TranslationTextComponent("honkaiimpactmisc.shiftinfo"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addHiddenTooltip(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {

    }

    @OnlyIn(Dist.CLIENT)
    public void addSetTooltip(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {

    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return CurioIntegration.initCap(stack);
    }

    public void onWornTick(ItemStack stack, LivingEntity entity) {}

    public void onEquipped(ItemStack stack, LivingEntity entity) {}

    public void onUnequipped(ItemStack stack, LivingEntity entity) {}

    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public Multimap<String, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        return HashMultimap.create();
    }

    public enum EnumStigmataType{
        DEFAULT, BENARES, WELTYANG, HANNAH;
    }

    public EnumStigmataType getStigmataType(){
        return EnumStigmataType.DEFAULT;
    }

    public static boolean isSameSet(ItemStack stack, EnumStigmataType type){
        return !stack.isEmpty() && stack.getItem() instanceof ItemStigmata && ((ItemStigmata)stack.getItem()).getStigmataType().equals(type);
    }

    /**
     * 是否有两件套
     */
    public static boolean has2SetStigmata(LivingEntity player, EnumStigmataType type){
        AtomicBoolean b = new AtomicBoolean(false);
        CuriosAPI.getCuriosHandler(player).ifPresent((handler) -> {
            CurioStackHandler stackHandlerT = handler.getStackHandler("stigmatat");
            CurioStackHandler stackHandlerM = handler.getStackHandler("stigmatam");
            CurioStackHandler stackHandlerB = handler.getStackHandler("stigmatab");
            final boolean sT = stackHandlerT != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            final boolean sM = stackHandlerM != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            final boolean sB = stackHandlerB != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            b.set(sT && sM || sM && sB || sT && sB);
        });
        return b.get();
    }

    /**
     * 是否有三件套
     */
    public static boolean has3SetStigmata(LivingEntity player, EnumStigmataType type){
        AtomicBoolean b = new AtomicBoolean(false);
        CuriosAPI.getCuriosHandler(player).ifPresent((handler) -> {
            CurioStackHandler stackHandlerT = handler.getStackHandler("stigmatat");
            CurioStackHandler stackHandlerM = handler.getStackHandler("stigmatam");
            CurioStackHandler stackHandlerB = handler.getStackHandler("stigmatab");
            final boolean sT = stackHandlerT != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            final boolean sM = stackHandlerM != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            final boolean sB = stackHandlerB != null && isSameSet(stackHandlerT.getStackInSlot(0), type);
            b.set(sT && sM && sB);
        });
        return b.get();
    }

    @Override
    public DamageCalculator getDamageBoost(PlayerEntity player){
        return new DamageCalculator(0F,0F,0F,0F);
    }

    @SubscribeEvent
    public static void onDamageAccounted(LivingHurtEvent event){
        /**
         * 全伤害和物理伤害结算
         */
        if(event.getSource().getImmediateSource() != null) {
            if(event.getSource().getImmediateSource() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getSource().getImmediateSource();
                DamageCalculator c = getDamageCalculator(player);
                event.setAmount(event.getAmount() * (1F + c.getAllDMG()) * (1F + c.getPhysicalDMG()));
            }
        }
    }

    public static DamageCalculator getDamageCalculator(PlayerEntity player){
        AtomicReference<Float> allDMG = new AtomicReference<>(0F);
        AtomicReference<Float> physicalDMG = new AtomicReference<>(0F);
        AtomicReference<Float> thunderDMG = new AtomicReference<>(0F);
        AtomicReference<Float> iceDMG = new AtomicReference<>(0F);
        CuriosAPI.getCuriosHandler(player).ifPresent((handler) -> {
            CurioStackHandler stackHandlerT = handler.getStackHandler("stigmatat");
            CurioStackHandler stackHandlerM = handler.getStackHandler("stigmatam");
            CurioStackHandler stackHandlerB = handler.getStackHandler("stigmatab");
            if (stackHandlerT != null) {
                if (!stackHandlerT.getStackInSlot(0).isEmpty()) {
                    IDamageBooster stigmata = (IDamageBooster) stackHandlerT.getStackInSlot(0).getItem();
                    allDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getAllDMG())));
                    physicalDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getPhysicalDMG())));
                    thunderDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getThunderDMG())));
                    iceDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getIceDMG())));
                }
            }

            if (stackHandlerM != null) {
                if (!stackHandlerM.getStackInSlot(0).isEmpty()) {
                    IDamageBooster stigmata = (IDamageBooster) stackHandlerM.getStackInSlot(0).getItem();
                    allDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getAllDMG())));
                    physicalDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getPhysicalDMG())));
                    thunderDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getThunderDMG())));
                    iceDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getIceDMG())));
                }
            }

            if (stackHandlerB != null) {
                if (!stackHandlerB.getStackInSlot(0).isEmpty()) {
                    IDamageBooster stigmata = (IDamageBooster) stackHandlerB.getStackInSlot(0).getItem();
                    allDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getAllDMG())));
                    physicalDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getPhysicalDMG())));
                    thunderDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getThunderDMG())));
                    iceDMG.updateAndGet(v -> new Float((float) (v + stigmata.getDamageBoost(player).getIceDMG())));
                }
            }
        });

        if(!player.getActivePotionEffects().isEmpty())
            for(EffectInstance effect : player.getActivePotionEffects()){
                if(effect.getPotion() instanceof IDamageBooster){
                    IDamageBooster booster = (IDamageBooster) effect.getPotion();
                    allDMG.updateAndGet(v -> new Float((float) (v + booster.getDamageBoost(player).getAllDMG())));
                    physicalDMG.updateAndGet(v -> new Float((float) (v + booster.getDamageBoost(player).getPhysicalDMG())));
                    thunderDMG.updateAndGet(v -> new Float((float) (v + booster.getDamageBoost(player).getThunderDMG())));
                    iceDMG.updateAndGet(v -> new Float((float) (v + booster.getDamageBoost(player).getIceDMG())));
                }
            }

        return new DamageCalculator(allDMG.get(), physicalDMG.get(), thunderDMG.get(), iceDMG.get());
    }

}

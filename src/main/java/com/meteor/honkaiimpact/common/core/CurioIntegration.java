package com.meteor.honkaiimpact.common.core;

import com.google.common.collect.Multimap;
import com.meteor.honkaiimpact.common.capability.SimpleCapProvider;
import com.meteor.honkaiimpact.common.items.stigmata.ItemStigmata;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import java.util.function.Predicate;

public class CurioIntegration extends EquipmentHandler{

    public static void sendImc(InterModEnqueueEvent evt) {
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("stigmatab"));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("stigmatam"));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("stigmatat"));
    }

    @Override
    public LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living) {
        return CuriosAPI.getCuriosHandler(living).map(h -> {
            IItemHandlerModifiable[] invs = h.getCurioMap().values().toArray(new IItemHandlerModifiable[0]);
            return new CombinedInvWrapper(invs);
        });
    }

    @Override
    public ItemStack findItem(Item item, LivingEntity living) {
        return CuriosAPI.getCurioEquipped(item, living)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
        return CuriosAPI.getCurioEquipped(pred, living)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public ICapabilityProvider initCap(ItemStack stack) {
        return new SimpleCapProvider<>(CuriosCapability.ITEM, new Wrapper(stack));
    }

    public static class Wrapper implements ICurio {
        private final ItemStack stack;

        Wrapper(ItemStack stack) {
            this.stack = stack;
        }

        private ItemStigmata getItem() {
            return (ItemStigmata) stack.getItem();
        }

        @Override
        public void onCurioTick(String identifier, int index, LivingEntity entity) {
            getItem().onWornTick(stack, entity);
        }

        @Override
        public void onEquipped(String identifier, LivingEntity entity) {
            getItem().onEquipped(stack, entity);
        }

        @Override
        public void onUnequipped(String identifier, LivingEntity entity) {
            getItem().onUnequipped(stack, entity);
        }

        @Override
        public boolean canEquip(String identifier, LivingEntity entity) {
            return getItem().canEquip(stack, entity);
        }

        @Override
        public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
            return getItem().getEquippedAttributeModifiers(stack);
        }

        @Override
        public boolean shouldSyncToTracking(String identifier, LivingEntity entity) {
            return true;
        }

        @Override
        public void playEquipSound(LivingEntity entity) {}

        @Override
        public boolean canRightClickEquip() {
            return true;
        }

    }

}

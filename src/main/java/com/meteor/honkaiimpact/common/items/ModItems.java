package com.meteor.honkaiimpact.common.items;

import com.meteor.honkaiimpact.common.items.stigmata.*;
import com.meteor.honkaiimpact.common.libs.LibItemNames;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, LibMisc.MOD_ID);

    public static RegistryObject<Item> motor = ITEMS.register(LibItemNames.MOTOR, () -> {
        return new ItemMotor();
    });

    public static RegistryObject<Item> gemofconquest = ITEMS.register(LibItemNames.GEMOFCONQUEST, () -> {
        return new ItemGemOfConquest();
    });

    public static RegistryObject<Item> benarest = ITEMS.register(LibItemNames.BENAREST, () -> {
        return new ItemBenaresT();
    });

    public static RegistryObject<Item> benaresm = ITEMS.register(LibItemNames.BENARESM, () -> {
        return new ItemBenaresM();
    });

    public static RegistryObject<Item> benaresb = ITEMS.register(LibItemNames.BENARESB, () -> {
        return new ItemBenaresB();
    });

    public static RegistryObject<Item> weltyangt = ITEMS.register(LibItemNames.WELTYANGT, () -> {
        return new ItemWeltYangT();
    });

    public static RegistryObject<Item> weltyangm = ITEMS.register(LibItemNames.WELTYANGM, () -> {
        return new ItemWeltYangM();
    });

    public static RegistryObject<Item> weltyangb = ITEMS.register(LibItemNames.WELTYANGB, () -> {
        return new ItemWeltYangB();
    });

}

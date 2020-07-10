package com.meteor.honkaiimpact;

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

}

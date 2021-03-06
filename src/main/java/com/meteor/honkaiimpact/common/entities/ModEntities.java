package com.meteor.honkaiimpact.common.entities;

import com.meteor.honkaiimpact.common.libs.LibEntityNames;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModEntities {

    public static final EntityType<EntityMotor> MOTOR = EntityType.Builder.<EntityMotor>create(
            EntityMotor::new, EntityClassification.MISC)
            .size(1.675F, 1.875F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityKeyOfTruth> KEY_OF_TRUTH = EntityType.Builder.<EntityKeyOfTruth>create(
            EntityKeyOfTruth::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntitySlash> SLASH = EntityType.Builder.<EntitySlash>create(
            EntitySlash::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntityKulikah> KULIKAH = EntityType.Builder.<EntityKulikah>create(
            EntityKulikah::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static final EntityType<EntitySupplyChest> SUPPLYCHEST = EntityType.Builder.<EntitySupplyChest>create(
            EntitySupplyChest::new, EntityClassification.MISC)
            .size(0.1F, 0.1F)
            .setUpdateInterval(10)
            .setTrackingRange(64)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        IForgeRegistry<EntityType<?>> r = evt.getRegistry();
        register(r, LibEntityNames.KEYOFTRUTH, KEY_OF_TRUTH);
        register(r, LibEntityNames.MOTOR, MOTOR);
        register(r, LibEntityNames.SLASH, SLASH);
        register(r, LibEntityNames.SUPPLYCHEST, SUPPLYCHEST);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, new ResourceLocation(LibMisc.MOD_ID, name), thing);
    }

}

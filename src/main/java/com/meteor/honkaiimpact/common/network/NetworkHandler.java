package com.meteor.honkaiimpact.common.network;

import com.meteor.honkaiimpact.common.libs.LibMisc;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(LibMisc.MOD_ID + ":networking"),
                () -> "1.0",
                (s) -> true,
                (s) -> true
        );
        INSTANCE.registerMessage(
                nextID(),
                HerrscherEnergyUpdatePack.class,
                HerrscherEnergyUpdatePack::toBytes,
                HerrscherEnergyUpdatePack::new,
                HerrscherEnergyUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                HerrscherSkillPack.class,
                HerrscherSkillPack::toBytes,
                HerrscherSkillPack::new,
                HerrscherSkillPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MotorUpdatePack.class,
                MotorUpdatePack::toBytes,
                MotorUpdatePack::new,
                MotorUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                ParticlePack.class,
                ParticlePack::toBytes,
                ParticlePack::new,
                ParticlePack::handler
        );
    }

    public static void sendToNearby(World world, BlockPos pos, Object toSend) {
        if (world instanceof ServerWorld) {
            ServerWorld ws = (ServerWorld) world;

            ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                        .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(World world, Entity e, Object toSend) {
        sendToNearby(world, e.getPosition(), toSend);
    }

}

package com.meteor.honkaiimpact.common.network;

import com.meteor.honkaiimpact.common.entities.EntityMotor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Rarity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePack {

    private double posx;
    private double posy;
    private double posz;
    private boolean isRare;

    public ParticlePack(PacketBuffer buffer) {
        posx = buffer.readDouble();
        posy = buffer.readDouble();
        posz = buffer.readDouble();
        isRare = buffer.readBoolean();
    }

    public ParticlePack(double posx, double posy, double posz, boolean isRare) {
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        this.isRare = isRare;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeDouble(this.posx);
        buf.writeDouble(this.posy);
        buf.writeDouble(this.posz);
        buf.writeBoolean(this.isRare);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                for (int i = 0; i < 5; i++)
                    if (isRare)
                        player.world.addParticle(ParticleTypes.DRAGON_BREATH, posx, posy + 0.35, posz, (0.5F - Math.random()) * 0.25F, 0.13F, (0.5F - Math.random()) * 0.25F);
                    else
                        player.world.addParticle(ParticleTypes.ENCHANTED_HIT, posx, posy + 0.65, posz, (0.5F - Math.random()) * 0.25F, 0.13F, (0.5F - Math.random()) * 0.25F);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}

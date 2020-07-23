package com.meteor.honkaiimpact.common.network;

import com.meteor.honkaiimpact.common.capability.CapabilityHandler;
import com.meteor.honkaiimpact.common.capability.IHerrscherEnergy;
import com.meteor.honkaiimpact.common.entities.EntityMotor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MotorUpdatePack {

    private boolean ctrlInputDown;
    private boolean upInputDown;

    public MotorUpdatePack(PacketBuffer buffer) {
        ctrlInputDown = buffer.readBoolean();
        upInputDown = buffer.readBoolean();
    }

    public MotorUpdatePack(boolean ctrlInputDown, boolean upInputDown) {
        this.ctrlInputDown = ctrlInputDown;
        this.upInputDown = upInputDown;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(this.ctrlInputDown);
        buf.writeBoolean(this.upInputDown);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            Entity riding = player.getRidingEntity();
            if(riding != null && riding instanceof EntityMotor){
                EntityMotor motor = (EntityMotor) riding;
                motor.updateInput(ctrlInputDown, upInputDown);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}

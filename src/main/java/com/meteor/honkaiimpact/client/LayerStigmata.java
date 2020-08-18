package com.meteor.honkaiimpact.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

import javax.annotation.Nonnull;

public class LayerStigmata extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public LayerStigmata(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(@Nonnull MatrixStack ms, @Nonnull IRenderTypeBuffer buffers, int light, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ms.push();
        getEntityModel().bipedBody.translateRotate(ms);
        if(player != null) {
            CuriosAPI.getCuriosHandler(player).ifPresent((handler) -> {
                CurioStackHandler stackHandlerT = handler.getStackHandler("stigmatat");
                CurioStackHandler stackHandlerM = handler.getStackHandler("stigmatam");
                CurioStackHandler stackHandlerB = handler.getStackHandler("stigmatab");
                if (stackHandlerT != null && !stackHandlerT.getStackInSlot(0).isEmpty()) {
                    ms.push();
                    ms.translate(0F, 0.1F, 0.122F);
                    ItemStack stigmata = stackHandlerT.getStackInSlot(0);
                    ms.scale(0.2F, -0.2F, -0.2F);
                    renderItem(stigmata, ms, buffers, 0xF000F0);
                    ms.pop();
                }

                if (stackHandlerM != null && !stackHandlerM.getStackInSlot(0).isEmpty()) {
                    ms.push();
                    ms.translate(0F, 0.31F, 0.122F);
                    ItemStack stigmata = stackHandlerM.getStackInSlot(0);
                    ms.scale(0.2F, -0.2F, -0.2F);
                    renderItem(stigmata, ms, buffers, 0xF000F0);
                    ms.pop();
                }

                if (stackHandlerB != null && !stackHandlerB.getStackInSlot(0).isEmpty()) {
                    ms.push();
                    ms.translate(0F, 0.52F, 0.122F);
                    ItemStack stigmata = stackHandlerB.getStackInSlot(0);
                    ms.scale(0.2F, -0.2F, -0.2F);
                    renderItem(stigmata, ms, buffers, 0xF000F0);
                    ms.pop();
                }
            });
        }
        ms.pop();
    }

    public static void renderItem(ItemStack stack, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, ms, buffers);
    }

}

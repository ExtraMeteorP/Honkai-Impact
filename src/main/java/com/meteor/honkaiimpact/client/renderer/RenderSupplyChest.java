package com.meteor.honkaiimpact.client.renderer;

import com.meteor.honkaiimpact.client.model.ModelMotor;
import com.meteor.honkaiimpact.client.model.ModelSupplyChest;
import com.meteor.honkaiimpact.common.entities.EntityMotor;
import com.meteor.honkaiimpact.common.entities.EntitySupplyChest;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSupplyChest extends EntityRenderer<EntitySupplyChest> {

    private EntityModel<EntitySupplyChest> motorModel = new ModelSupplyChest();

    public RenderSupplyChest(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(EntitySupplyChest entity) {
        return new ResourceLocation(LibMisc.MOD_ID, "textures/entity/supplychest.png");
    }

    @Override
    public void render(EntitySupplyChest entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 1.500D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-entityIn.getRotation()));
        this.motorModel.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.motorModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.motorModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}

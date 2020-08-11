package com.meteor.honkaiimpact.client.model;

import com.meteor.honkaiimpact.common.entities.EntitySupplyChest;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelSupplyChest extends EntityModel {

    private final ModelRenderer homo;
    private final ModelRenderer lip;
    public final ModelRenderer lib;
    private final ModelRenderer bone2;
    private final ModelRenderer bone7;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer bone4;
    private final ModelRenderer bone3;
    private final ModelRenderer bone8;
    private final ModelRenderer bone9;
    private final ModelRenderer bone10;
    private final ModelRenderer bone11;
    private final ModelRenderer bone12;

	public ModelSupplyChest() {
        textureWidth = 128;
        textureHeight = 128;

        homo = new ModelRenderer(this);
        homo.setRotationPoint(0.0F, 24.0F, 0.0F);
        homo.setTextureOffset(0, 58).addBox(-5.5F, -10.0F, -7.01F, 11.0F, 9.0F, 0.0F, 0.0F, false);

        lip = new ModelRenderer(this);
        lip.setRotationPoint(0.0F, 12.0F, 7.0F);
        lip.setTextureOffset(0, 54).addBox(-7.0F, -2.0F, -14.0F, 14.0F, 2.0F, 1.0F, 0.0F, false);
        lip.setTextureOffset(0, 51).addBox(-7.0F, -2.0F, -1.0F, 14.0F, 2.0F, 1.0F, 0.0F, false);
        lip.setTextureOffset(42, 0).addBox(6.0F, -2.0F, -13.0F, 1.0F, 2.0F, 12.0F, 0.0F, false);
        lip.setTextureOffset(40, 40).addBox(-7.0F, -2.0F, -13.0F, 1.0F, 2.0F, 12.0F, 0.0F, false);
        lip.setTextureOffset(0, 15).addBox(-6.0F, -2.0F, -13.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);

        lib = new ModelRenderer(this);
        lib.setRotationPoint(0.0F, 24.0F, 0.0F);


        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
        lib.addChild(bone2);
        bone2.setTextureOffset(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
        bone2.setTextureOffset(40, 28).addBox(-7.0F, -12.0F, 6.0F, 14.0F, 11.0F, 1.0F, 0.0F, false);
        bone2.setTextureOffset(36, 15).addBox(-7.0F, -12.0F, -7.0F, 14.0F, 11.0F, 1.0F, 0.0F, false);
        bone2.setTextureOffset(26, 28).addBox(6.0F, -12.0F, -6.0F, 1.0F, 11.0F, 12.0F, 0.0F, false);
        bone2.setTextureOffset(0, 28).addBox(-7.0F, -12.0F, -6.0F, 1.0F, 11.0F, 12.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(0.0F, 1.0F, 0.0F);
        bone2.addChild(bone7);


        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone7.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, 0.7854F);
        bone5.setTextureOffset(0, 21).addBox(-5.9F, -12.0F, 6.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone7.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.7854F);
        bone6.setTextureOffset(0, 15).addBox(-5.9F, -12.0F, -7.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone7.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 0.0F, -0.7854F);
        bone4.setTextureOffset(0, 18).addBox(1.9F, -12.0F, 6.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone7.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 0.0F, -0.7854F);
        bone3.setTextureOffset(0, 24).addBox(1.9F, -12.0F, -7.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(0.0F, 1.0F, 0.0F);
        bone2.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 1.5708F, 0.0F);


        bone9 = new ModelRenderer(this);
        bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone8.addChild(bone9);
        setRotationAngle(bone9, 0.0F, 0.0F, 0.7854F);
        bone9.setTextureOffset(0, 9).addBox(-5.9F, -12.0F, 6.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone10 = new ModelRenderer(this);
        bone10.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone8.addChild(bone10);
        setRotationAngle(bone10, 0.0F, 0.0F, 0.7854F);
        bone10.setTextureOffset(0, 6).addBox(-5.9F, -12.0F, -7.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone11 = new ModelRenderer(this);
        bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone8.addChild(bone11);
        setRotationAngle(bone11, 0.0F, 0.0F, -0.7854F);
        bone11.setTextureOffset(0, 3).addBox(1.9F, -12.0F, 6.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);

        bone12 = new ModelRenderer(this);
        bone12.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone8.addChild(bone12);
        setRotationAngle(bone12, 0.0F, 0.0F, -0.7854F);
        bone12.setTextureOffset(0, 0).addBox(1.9F, -12.0F, -7.1F, 4.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float partialTicks, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
        if(entity instanceof EntitySupplyChest) {
            EntitySupplyChest chest = (EntitySupplyChest) entity;
            lip.rotateAngleX = -(chest.prevLidAngle + (chest.lidAngle-chest.prevLidAngle) * partialTicks) / 180F * (float) Math.PI;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        homo.render(matrixStack, buffer, packedLight, packedOverlay);
        lip.render(matrixStack, buffer, packedLight, packedOverlay);
        lib.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

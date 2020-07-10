package com.meteor.honkaiimpact.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class ModelHerrscher extends ModelArmor {

    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone3;
    private final ModelRenderer rightarm;
    private final ModelRenderer right;
    private final ModelRenderer s1;
    private final ModelRenderer s2;
    private final ModelRenderer s3;
    private final ModelRenderer hand;
    private final ModelRenderer leftarm;
    private final ModelRenderer right2;
    private final ModelRenderer s4;
    private final ModelRenderer s5;
    private final ModelRenderer s6;
    private final ModelRenderer hand2;
    private final ModelRenderer sword;

    public ModelHerrscher() {
        super(EquipmentSlotType.CHEST);
        textureWidth = 128;
        textureHeight = 128;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, -5.0F, 7.0F);
        setRotationAngle(bone, -0.5236F, -1.5708F, 0.0F);
        bone.setTextureOffset(0, 0).addBox(-9.0F, -5.0F, 4.0F, 3.0F, 3.0F, 7.0F, 0.0F, false);
        bone.setTextureOffset(34, 0).addBox(-18.0F, -3.7679F, 9.3301F, 12.0F, 1.0F, 1.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, -5.0F, -7.0F);
        setRotationAngle(bone2, 0.5236F, -1.5708F, 0.0F);
        bone2.setTextureOffset(0, 12).addBox(5.0F, -5.0F, -11.0F, 3.0F, 3.0F, 7.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, -5.0F, 7.0F);
        bone3.setTextureOffset(0, 24).addBox(-11.0F, -10.0F, 8.0F, 24.0F, 24.0F, 0.0F, 0.0F, false);

        rightarm = new ModelRenderer(this);
        rightarm.setRotationPoint(10.0F, 2.0F, -2.0F);
        setRotationAngle(rightarm, 0.0F, -0.0873F, -0.8727F);


        right = new ModelRenderer(this);
        right.setRotationPoint(0.0F, 0.0F, 0.0F);
        rightarm.addChild(right);
        right.setTextureOffset(29, 8).addBox(-16.0F, -17.0F, 0.0F, 8.0F, 2.0F, 6.0F, 0.0F, false);
        right.setTextureOffset(29, 16).addBox(-15.0F, -17.3F, 1.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        right.setTextureOffset(0, 12).addBox(-15.0F, -16.3F, 1.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        right.setTextureOffset(51, 3).addBox(-9.0F, -17.5F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(48, 24).addBox(-9.0F, -17.5F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(29, 0).addBox(-6.0F, -17.5F, 2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        s1 = new ModelRenderer(this);
        s1.setRotationPoint(0.0F, -17.0F, 1.0F);
        rightarm.addChild(s1);
        setRotationAngle(s1, -0.5236F, 0.0F, 0.0F);
        s1.setTextureOffset(51, 0).addBox(-14.0F, 0.875F, -0.7835F, 5.0F, 2.0F, 1.0F, 0.0F, false);

        s2 = new ModelRenderer(this);
        s2.setRotationPoint(0.0F, -12.0F, 2.5F);
        rightarm.addChild(s2);
        setRotationAngle(s2, 0.5236F, 0.0F, 0.0F);
        s2.setTextureOffset(48, 49).addBox(-14.0F, -2.0F, 4.1962F, 5.0F, 2.0F, 1.0F, 0.0F, false);

        s3 = new ModelRenderer(this);
        s3.setRotationPoint(-10.0F, 3.0F, 0.0F);
        rightarm.addChild(s3);
        setRotationAngle(s3, 0.0F, 0.0F, 0.5236F);
        s3.setTextureOffset(19, 11).addBox(-12.0F, -20.0F, 2.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        s3.setTextureOffset(0, 18).addBox(-12.433F, -18.75F, 3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        s3.setTextureOffset(0, 12).addBox(-12.433F, -18.75F, 1.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        hand = new ModelRenderer(this);
        hand.setRotationPoint(2.25F, -3.0F, 0.0F);
        rightarm.addChild(hand);
        setRotationAngle(hand, 0.0F, 0.0F, -0.1745F);
        hand.setTextureOffset(45, 18).addBox(-19.0F, -16.25F, 1.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        hand.setTextureOffset(12, 48).addBox(-18.0F, -15.5F, 1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);
        hand.setTextureOffset(51, 8).addBox(-17.8316F, -14.7849F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        hand.setTextureOffset(26, 26).addBox(-17.5702F, -14.9151F, -21.0F, 2.0F, 1.0F, 22.0F, 0.0F, false);

        leftarm = new ModelRenderer(this);
        leftarm.setRotationPoint(-4.0F, 8.0F, 3.0F);
        setRotationAngle(leftarm, 3.1416F, -0.0873F, -2.1817F);


        right2 = new ModelRenderer(this);
        right2.setRotationPoint(10.0F, 0.0F, 0.0F);
        leftarm.addChild(right2);
        right2.setTextureOffset(29, 0).addBox(-16.0F, -17.0F, 0.0F, 8.0F, 2.0F, 6.0F, 0.0F, false);
        right2.setTextureOffset(0, 6).addBox(-15.0F, -17.3F, 1.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        right2.setTextureOffset(0, 0).addBox(-15.0F, -16.3F, 1.0F, 6.0F, 2.0F, 4.0F, 0.0F, false);
        right2.setTextureOffset(45, 16).addBox(-9.0F, -17.5F, 4.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        right2.setTextureOffset(12, 20).addBox(-9.0F, -17.5F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        right2.setTextureOffset(16, 0).addBox(-6.0F, -17.5F, 2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        s4 = new ModelRenderer(this);
        s4.setRotationPoint(10.0F, -17.0F, 1.0F);
        leftarm.addChild(s4);
        setRotationAngle(s4, -0.5236F, 0.0F, 0.0F);
        s4.setTextureOffset(36, 49).addBox(-14.0F, 0.875F, -0.7835F, 5.0F, 2.0F, 1.0F, 0.0F, false);

        s5 = new ModelRenderer(this);
        s5.setRotationPoint(10.0F, -12.0F, 2.5F);
        leftarm.addChild(s5);
        setRotationAngle(s5, 0.5236F, 0.0F, 0.0F);
        s5.setTextureOffset(24, 49).addBox(-14.0F, -2.0F, 4.1962F, 5.0F, 2.0F, 1.0F, 0.0F, false);

        s6 = new ModelRenderer(this);
        s6.setRotationPoint(0.0F, 3.0F, 0.0F);
        leftarm.addChild(s6);
        setRotationAngle(s6, 0.0F, 0.0F, 0.5236F);
        s6.setTextureOffset(19, 5).addBox(-12.0F, -20.0F, 2.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        s6.setTextureOffset(0, 6).addBox(-12.433F, -18.75F, 3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        s6.setTextureOffset(0, 0).addBox(-12.433F, -18.75F, 1.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        hand2 = new ModelRenderer(this);
        hand2.setRotationPoint(12.25F, -3.0F, 0.0F);
        leftarm.addChild(hand2);
        setRotationAngle(hand2, 0.0F, 0.0F, -0.1745F);
        hand2.setTextureOffset(0, 18).addBox(-19.0F, -16.25F, 1.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        hand2.setTextureOffset(0, 48).addBox(-18.0F, -15.5F, 1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        sword = new ModelRenderer(this);
        sword.setRotationPoint(4.3172F, -1.0607F, 3.3892F);
        leftarm.addChild(sword);
        setRotationAngle(sword, 0.0F, 0.4363F, -0.5236F);
        sword.setTextureOffset(12, 18).addBox(-6.5816F, -17.7849F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        sword.setTextureOffset(0, 0).addBox(-6.2344F, -17.9F, -21.9962F, 3.0F, 1.0F, 23.0F, 0.0F, false);
    }

    public void renderLeftArm(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.leftarm.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void renderRightArm(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.rightarm.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void renderStigma(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone3.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}

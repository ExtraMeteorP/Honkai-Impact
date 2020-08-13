package com.meteor.honkaiimpact.client.handler;

import com.meteor.honkaiimpact.common.handler.HerrscherHandler;
import com.meteor.honkaiimpact.common.libs.LibMisc;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;

public class HUDHandler {

    public static void onOverlayRender(RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (Minecraft.getInstance().player == null ) {
            return;
        }

        if(HerrscherHandler.isHerrscherOfThunder(Minecraft.getInstance().player) && !Minecraft.getInstance().player.isCreative()) {
            HerrscherGUI gui = new HerrscherGUI();
            gui.render();
        }

        MotorGUI motorGui = new MotorGUI();
        motorGui.render();


//        BufferedImage image = null;
//        try {
//            URL url = new URL("https://i.loli.net/2020/08/11/174N2YPewRm3t6D.jpg");
//            image = ImageIO.read(url);
//        } catch (IOException e) {
//
//        }
//
//        DynamicTexture dynamicTexture = null;
//        try {
//            dynamicTexture = new DynamicTexture(NativeImage.read(getImageStream(image)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int width = image.getWidth();
//        int x = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 - width / 2;
//        int y = Minecraft.getInstance().getMainWindow().getScaledHeight() - 56;
//
//        Minecraft.getInstance().getTextureManager().loadTexture(new ResourceLocation(LibMisc.MOD_ID, "textures/buffer"), dynamicTexture);
//        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(LibMisc.MOD_ID, "textures/buffer"));
//        AbstractGui.blit(x,y,0,0, image.getWidth(), image.getHeight(),image.getWidth(), image.getHeight() );
    }

    public static InputStream getImageStream(BufferedImage bimage){
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png",imOut);
            is= new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

}

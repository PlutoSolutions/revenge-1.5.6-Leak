/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public interface IComponent {
    public static void drawLine(Point start, Point end, int thickness, Color color) {
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        GlStateManager.glLineWidth((float)thickness);
        GL11.glEnable((int)2848);
        bb.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bb.pos((double)start.x, (double)start.y, 0.0).color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f).endVertex();
        bb.pos((double)end.x, (double)end.y, 0.0).color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f).endVertex();
        Tessellator.getInstance().draw();
    }

    public static void fillRect(Rectangle rect, Color c1, Color c2, Color c3, Color c4) {
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bb.pos((double)rect.x, (double)(rect.y + rect.height), 0.0).color((float)c4.getRed() / 255.0f, (float)c4.getGreen() / 255.0f, (float)c4.getBlue() / 255.0f, (float)c4.getAlpha() / 255.0f).endVertex();
        bb.pos((double)(rect.x + rect.width), (double)(rect.y + rect.height), 0.0).color((float)c3.getRed() / 255.0f, (float)c3.getGreen() / 255.0f, (float)c3.getBlue() / 255.0f, (float)c3.getAlpha() / 255.0f).endVertex();
        bb.pos((double)(rect.x + rect.width), (double)rect.y, 0.0).color((float)c2.getRed() / 255.0f, (float)c2.getGreen() / 255.0f, (float)c2.getBlue() / 255.0f, (float)c2.getAlpha() / 255.0f).endVertex();
        bb.pos((double)rect.x, (double)rect.y, 0.0).color((float)c1.getRed() / 255.0f, (float)c1.getGreen() / 255.0f, (float)c1.getBlue() / 255.0f, (float)c1.getAlpha() / 255.0f).endVertex();
        Tessellator.getInstance().draw();
    }

    public static void fillRect(Rectangle rect, Color color) {
        Gui.drawRect((int)rect.x, (int)rect.y, (int)(rect.x + rect.width), (int)(rect.y + rect.height), (int)color.getRGB());
    }

    public static void drawImage(Rectangle r, ResourceLocation resource) {
        GlStateManager.enableTexture2D();
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        Gui.drawModalRectWithCustomSizedTexture((int)r.x, (int)r.y, (float)0.0f, (float)0.0f, (int)r.width, (int)r.height, (float)r.width, (float)r.height);
        GlStateManager.disableTexture2D();
    }

    public static void drawString(String text, Point pos, Color color) {
        GlStateManager.enableTexture2D();
        Revenge.fontManager.drawStringWithShadow(text, pos.x, pos.y, color.getRGB());
        GlStateManager.disableTexture2D();
    }

    public void draw();

    public void handleButton(int var1);

    public void keyTyped(int var1, char var2);

    public int getAbsoluteHeight();

    public void addChild(IComponent var1);

    public Rectangle getRect();

    public void setRect(Rectangle var1);
}


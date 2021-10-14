/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.util;

import com.rianix.revenge.util.CFont;
import java.awt.Font;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFontRenderer
extends CFont {
    private final int[] colorCode = new int[32];
    private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    protected CFont.CharData[] boldChars = new CFont.CharData[256];
    protected CFont.CharData[] italicChars = new CFont.CharData[256];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        float shadowWidth = this.drawString(text, x + 1.0, y + 1.0, color, true);
        return Math.max(shadowWidth, this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return this.drawString(text, x - (float)this.getStringWidth(text) / 2.0f, y, color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        float shadowWidth = this.drawString(text, (double)(x - (float)this.getStringWidth(text) / 2.0f) + 1.0, (double)y + 1.0, color, true);
        return this.drawString(text, x - (float)this.getStringWidth(text) / 2.0f, y, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        x -= 1.0;
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        boolean bold = false;
        boolean italic = false;
        x *= 2.0;
        y = (y - 3.0) * 2.0;
        int size = text.length();
        GL11.glPushMatrix();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc((int)770, (int)771);
        GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture((int)this.tex.getGlTextureId());
        GL11.glEnable((int)2848);
        GL11.glHint((int)3155, (int)4354);
        GL11.glBegin((int)4);
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                if (colorIndex < 16) {
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorCode = this.colorCode[colorIndex];
                    GlStateManager.color((float)((float)(colorCode >> 16 & 0xFF) / 255.0f), (float)((float)(colorCode >> 8 & 0xFF) / 255.0f), (float)((float)(colorCode & 0xFF) / 255.0f), (float)alpha);
                } else if (colorIndex == 21) {
                    GlStateManager.color((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
                    currentData = this.charData;
                }
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            this.drawChar(currentData, character, (float)x, (float)y);
            x += (double)(currentData[character].width - 8);
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glHint((int)3155, (int)4354);
        GL11.glPopMatrix();
        return (float)x / 2.0f;
    }

    @Override
    public int getStringWidth(String text) {
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    currentData = italic ? this.boldItalicChars : this.boldChars;
                } else if (colorIndex == 20) {
                    italic = true;
                    currentData = bold ? this.boldItalicChars : this.italicChars;
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            width += currentData[character].width - 8;
        }
        return width >> 1;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            int shadow = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + shadow;
            int green = (index >> 1 & 1) * 170 + shadow;
            int blue = (index & 1) * 170 + shadow;
            if (index == 6) {
                red += 85;
            }
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }
}


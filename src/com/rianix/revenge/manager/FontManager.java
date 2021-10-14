/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package com.rianix.revenge.manager;

import com.rianix.revenge.util.CFontRenderer;
import com.rianix.revenge.util.MathUtil;
import java.awt.Color;
import java.awt.Font;
import net.minecraft.client.Minecraft;

public class FontManager {
    private final CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, true);
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean customFont = true;

    public void setCustomFont(boolean customFont) {
        this.customFont = customFont;
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        if (this.customFont) {
            this.cFontRenderer.drawStringWithShadow(text, x, y, color);
            return;
        }
        this.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public int getStringWidth(String text) {
        if (this.customFont) {
            return this.cFontRenderer.getStringWidth(text);
        }
        return this.mc.fontRenderer.getStringWidth(text);
    }

    public void drawRainbowString(String text, float x, float y, int startColor, float factor, boolean shadow) {
        Color currentColor = new Color(startColor);
        float hueIncrement = 1.0f / factor;
        String[] rainbowStrings = text.split("\u00a7.");
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            char currentChar = text.charAt(i);
            char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                shouldRainbow = false;
            } else if ((String.valueOf(currentChar) + nextChar).equals("\u00a7+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
                continue;
            }
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                String escapeString = text.substring(i);
                this.drawStringWithShadow(escapeString, x + (float)currentWidth, y, Color.WHITE.getRGB());
                break;
            }
            this.drawStringWithShadow(String.valueOf(currentChar).equals("\u00a7") ? "" : String.valueOf(currentChar), x + (float)currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB());
            if (String.valueOf(currentChar).equals("\u00a7")) {
                shouldContinue = true;
            }
            currentWidth += this.getStringWidth(String.valueOf(currentChar));
            if (String.valueOf(currentChar).equals(" ")) continue;
            currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
            currentHue += hueIncrement;
        }
    }
}


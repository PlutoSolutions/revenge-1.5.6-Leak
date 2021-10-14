/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui.color;

import java.awt.Color;

public class ColorHolder {
    private float h;
    private float s;
    private float b;
    private float a;

    public ColorHolder(Color color) {
        this.importFromAWTColor(color);
    }

    public ColorHolder(float h, float s, float b, float a) {
        this.h = h;
        this.s = s;
        this.b = b;
        this.a = a;
    }

    public float getHue() {
        return this.h;
    }

    public void setHue(float h) {
        this.h = h;
    }

    public float getSaturation() {
        return this.s;
    }

    public void setSaturation(float s) {
        this.s = s;
    }

    public float getBrightness() {
        return this.b;
    }

    public void setBrightness(float b) {
        this.b = b;
    }

    public float getAlpha() {
        return this.a;
    }

    public void setAlpha(float a) {
        this.a = a;
    }

    public Color toAWTColor() {
        Color c = Color.getHSBColor(this.h, this.s, this.b);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)this.a);
    }

    public void importFromAWTColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.h = hsb[0];
        this.s = hsb[1];
        this.b = hsb[2];
        this.a = color.getAlpha();
    }
}


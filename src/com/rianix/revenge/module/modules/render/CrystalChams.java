/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.event.Event;
import com.rianix.revenge.event.events.RenderEntityModelEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingDouble;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.lwjgl.opengl.GL11;

public class CrystalChams
extends Module {
    public SettingBoolean chams = this.register("Chams", false);
    public SettingBoolean wireframe = this.register("Wireframe", false);
    public SettingDouble lineWidth = this.register("LineWidth", 1.0, 0.1f, 3.0);
    public SettingColor color = this.register("Color", Color.GREEN, false);

    public CrystalChams() {
        super("CrystalChams", "", 0, Module.Category.RENDER);
    }

    public void onRenderModel(RenderEntityModelEvent event) {
        if (event.getEra() != Event.Era.PRE || !(event.entity instanceof EntityEnderCrystal) || !this.wireframe.getValue()) {
            return;
        }
        Color color = new Color(Revenge.moduleManager.getModuleByClass(CrystalChams.class).color.getAWTValue(true).getRed(), Revenge.moduleManager.getModuleByClass(CrystalChams.class).color.getAWTValue(true).getGreen(), Revenge.moduleManager.getModuleByClass(CrystalChams.class).color.getAWTValue(true).getBlue(), Revenge.moduleManager.getModuleByClass(CrystalChams.class).color.getAWTValue(true).getAlpha());
        boolean fancyGraphics = CrystalChams.mc.gameSettings.fancyGraphics;
        CrystalChams.mc.gameSettings.fancyGraphics = false;
        float gamma = CrystalChams.mc.gameSettings.gammaSetting;
        CrystalChams.mc.gameSettings.gammaSetting = 10000.0f;
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GlStateManager.blendFunc((int)770, (int)771);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GlStateManager.glLineWidth((float)((float)this.lineWidth.getValue()));
        event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}


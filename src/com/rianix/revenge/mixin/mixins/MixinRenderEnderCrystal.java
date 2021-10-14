/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderEnderCrystal
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.event.Event;
import com.rianix.revenge.event.events.RenderEntityModelEvent;
import com.rianix.revenge.module.modules.render.CrystalChams;
import com.rianix.revenge.module.modules.render.CrystalScale;
import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderEnderCrystal.class})
public class MixinRenderEnderCrystal {
    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        CrystalScale cScale = Revenge.moduleManager.getModuleByClass(CrystalScale.class);
        CrystalChams cChams = Revenge.moduleManager.getModuleByClass(CrystalChams.class);
        if (cScale.isToggled()) {
            GlStateManager.scale((float)((float)cScale.scale.getValue()), (float)((float)cScale.scale.getValue()), (float)((float)cScale.scale.getValue()));
        }
        if (cChams.isToggled() && cChams.wireframe.getValue()) {
            RenderEntityModelEvent event = new RenderEntityModelEvent(Event.Era.PRE, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            cChams.onRenderModel(event);
        }
        if (cChams.isToggled() && cChams.chams.getValue()) {
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            GL11.glEnable((int)2960);
            Color color = new Color(cChams.color.getAWTValue(true).getRed(), cChams.color.getAWTValue(true).getGreen(), cChams.color.getAWTValue(true).getBlue(), cChams.color.getAWTValue(true).getAlpha());
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)10754);
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
        } else {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        if (cScale.isToggled()) {
            GlStateManager.scale((float)((float)(1.0 / cScale.scale.getValue())), (float)((float)(1.0 / cScale.scale.getValue())), (float)((float)(1.0 / cScale.scale.getValue())));
        }
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelPlayer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.util;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.render.PopChams;
import com.rianix.revenge.util.Global;
import com.rianix.revenge.util.TessellatorUtil;
import java.awt.Color;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PopChamsUtil
implements Global {
    EntityOtherPlayerMP player;
    ModelPlayer playerModel;
    Long startTime;
    double alphaFill;
    double alphaLine;

    public PopChamsUtil(EntityOtherPlayerMP player, ModelPlayer playerModel, Long startTime, double alphaFill, double alphaLine) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.player = player;
        this.playerModel = playerModel;
        this.startTime = startTime;
        this.alphaFill = alphaFill;
        this.alphaLine = alphaFill;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (this.player == null || PopChamsUtil.mc.world == null || PopChamsUtil.mc.player == null) {
            return;
        }
        GL11.glLineWidth((float)1.0f);
        Color lineColorS = new Color(Revenge.moduleManager.getModuleByClass(PopChams.class).lineColor.getAWTValue(true).getRed(), Revenge.moduleManager.getModuleByClass(PopChams.class).lineColor.getAWTValue(true).getGreen(), Revenge.moduleManager.getModuleByClass(PopChams.class).lineColor.getAWTValue(true).getBlue(), Revenge.moduleManager.getModuleByClass(PopChams.class).lineColor.getAWTValue(true).getAlpha());
        Color fillColorS = new Color(Revenge.moduleManager.getModuleByClass(PopChams.class).fillColor.getAWTValue(true).getRed(), Revenge.moduleManager.getModuleByClass(PopChams.class).fillColor.getAWTValue(true).getGreen(), Revenge.moduleManager.getModuleByClass(PopChams.class).fillColor.getAWTValue(true).getBlue(), Revenge.moduleManager.getModuleByClass(PopChams.class).fillColor.getAWTValue(true).getAlpha());
        int lineA = lineColorS.getAlpha();
        int fillA = fillColorS.getAlpha();
        long time = System.currentTimeMillis() - this.startTime - ((Number)Revenge.moduleManager.getModuleByClass(PopChams.class).fadestart.getValue()).longValue();
        if (System.currentTimeMillis() - this.startTime > ((Number)Revenge.moduleManager.getModuleByClass(PopChams.class).fadestart.getValue()).longValue()) {
            double normal = this.normalize(time, 0.0, Revenge.moduleManager.getModuleByClass(PopChams.class).fadetime.getValue());
            normal = MathHelper.clamp((double)normal, (double)0.0, (double)1.0);
            normal = -normal + 1.0;
            lineA *= (int)normal;
            fillA *= (int)normal;
        }
        Color lineColor = PopChamsUtil.newAlpha(lineColorS, lineA);
        Color fillColor = PopChamsUtil.newAlpha(fillColorS, fillA);
        if (this.player != null && this.playerModel != null) {
            TessellatorUtil.prepareGL();
            GL11.glPushAttrib((int)1048575);
            GL11.glEnable((int)2881);
            GL11.glEnable((int)2848);
            if (this.alphaFill > 1.0) {
                this.alphaFill -= Revenge.moduleManager.getModuleByClass(PopChams.class).fadetime.getValue();
            }
            Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)this.alphaFill);
            if (this.alphaLine > 1.0) {
                this.alphaLine -= Revenge.moduleManager.getModuleByClass(PopChams.class).fadetime.getValue();
            }
            Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int)this.alphaLine);
            PopChamsUtil.glColor(fillFinal);
            GL11.glPolygonMode((int)1032, (int)6914);
            PopChamsUtil.renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1.0f);
            PopChamsUtil.glColor(outlineFinal);
            GL11.glPolygonMode((int)1032, (int)6913);
            PopChamsUtil.renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1.0f);
            GL11.glPolygonMode((int)1032, (int)6914);
            GL11.glPopAttrib();
            TessellatorUtil.releaseGL();
        }
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static void renderEntity(EntityLivingBase entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (mc.getRenderManager() == null) {
            return;
        }
        float partialTicks = mc.getRenderPartialTicks();
        double x = entity.posX - PopChamsUtil.mc.getRenderManager().viewerPosX;
        double y = entity.posY - PopChamsUtil.mc.getRenderManager().viewerPosY;
        double z = entity.posZ - PopChamsUtil.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= 0.125;
        }
        float interpolateRotation = PopChamsUtil.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float interpolateRotation2 = PopChamsUtil.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float rotationInterp = interpolateRotation2 - interpolateRotation;
        float renderPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        PopChamsUtil.renderLivingAt(x, y, z);
        float f8 = PopChamsUtil.handleRotationFloat(entity, partialTicks);
        PopChamsUtil.prepareRotations(entity);
        float f9 = PopChamsUtil.prepareScale(entity, scale);
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYawHead, entity.rotationPitch, f9, (Entity)entity);
        modelBase.render((Entity)entity, limbSwing, limbSwingAmount, f8, entity.rotationYawHead, entity.rotationPitch, f9);
        GlStateManager.popMatrix();
    }

    public static void prepareTranslate(EntityLivingBase entityIn, double x, double y, double z) {
        PopChamsUtil.renderLivingAt(x - PopChamsUtil.mc.getRenderManager().viewerPosX, y - PopChamsUtil.mc.getRenderManager().viewerPosY, z - PopChamsUtil.mc.getRenderManager().viewerPosZ);
    }

    public static void renderLivingAt(double x, double y, double z) {
        GlStateManager.translate((float)((float)x), (float)((float)y), (float)((float)z));
    }

    public static float prepareScale(EntityLivingBase entity, float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale((float)-1.0f, (float)-1.0f, (float)1.0f);
        double widthX = entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX;
        double widthZ = entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ;
        GlStateManager.scale((double)((double)scale + widthX), (double)(scale * entity.height), (double)((double)scale + widthZ));
        float f = 0.0625f;
        GlStateManager.translate((float)0.0f, (float)-1.501f, (float)0.0f);
        return 0.0625f;
    }

    public static void prepareRotations(EntityLivingBase entityLivingBase) {
        GlStateManager.rotate((float)(180.0f - entityLivingBase.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
    }

    public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
        float f;
        for (f = yawOffset - prevYawOffset; f < -180.0f; f += 360.0f) {
        }
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return prevYawOffset + partialTicks * f;
    }

    public static Color newAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static float handleRotationFloat(EntityLivingBase livingBase, float partialTicks) {
        return 0.0f;
    }
}


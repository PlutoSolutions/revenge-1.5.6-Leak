/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelPlayer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.module.modules.render;

import com.mojang.authlib.GameProfile;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.PopChamsUtil;
import com.rianix.revenge.util.TessellatorUtil;
import java.awt.Color;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PopChams
extends Module {
    EntityOtherPlayerMP player;
    ModelPlayer playerModel;
    Long startTime;
    double alphaFill;
    double alphaLine;
    public SettingBoolean self = this.register("Self", true);
    public SettingColor lineColor = this.register("LineColor", Color.GREEN, false);
    public SettingColor fillColor = this.register("FillColor", Color.GREEN, false);
    public SettingInteger fadestart = this.register("FadeStart", 200, 0, 3000);
    public SettingDouble fadetime = this.register("FadeTime", 0.5, 0.0, 2.0);
    public SettingBoolean onlyOneEsp = this.register("OnlyOneEsp", false);
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        SPacketEntityStatus packet;
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
            packet.getEntity((World)PopChams.mc.world);
            if (this.self.getValue() || packet.getEntity((World)PopChams.mc.world).getEntityId() != PopChams.mc.player.getEntityId()) {
                GameProfile profile = new GameProfile(PopChams.mc.player.getUniqueID(), "");
                this.player = new EntityOtherPlayerMP((World)PopChams.mc.world, profile);
                this.player.copyLocationAndAnglesFrom(packet.getEntity((World)PopChams.mc.world));
                this.playerModel = new ModelPlayer(0.0f, false);
                this.startTime = System.currentTimeMillis();
                this.playerModel.bipedHead.showModel = false;
                this.playerModel.bipedBody.showModel = false;
                this.playerModel.bipedLeftArmwear.showModel = false;
                this.playerModel.bipedLeftLegwear.showModel = false;
                this.playerModel.bipedRightArmwear.showModel = false;
                this.playerModel.bipedRightLegwear.showModel = false;
                this.alphaFill = this.fillColor.getAWTValue(true).getAlpha();
                this.alphaLine = this.lineColor.getAWTValue(true).getAlpha();
                if (!this.onlyOneEsp.getValue()) {
                    PopChamsUtil popChamsUtil = new PopChamsUtil(this.player, this.playerModel, this.startTime, this.alphaFill, this.alphaLine);
                }
            }
        }
    }, new Predicate[0]);

    public PopChams() {
        super("PopChams", "", 0, Module.Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (this.onlyOneEsp.getValue()) {
            if (this.player == null || PopChams.mc.world == null || PopChams.mc.player == null) {
                return;
            }
            GL11.glLineWidth((float)1.0f);
            Color lineColorS = new Color(this.lineColor.getAWTValue(true).getRed(), this.lineColor.getAWTValue(true).getGreen(), this.lineColor.getAWTValue(true).getBlue(), this.lineColor.getAWTValue(true).getBlue());
            Color fillColorS = new Color(this.fillColor.getAWTValue(true).getRed(), this.fillColor.getAWTValue(true).getGreen(), this.fillColor.getAWTValue(true).getBlue(), this.fillColor.getAWTValue(true).getAlpha());
            int lineA = lineColorS.getAlpha();
            int fillA = fillColorS.getAlpha();
            long time = System.currentTimeMillis() - this.startTime - ((Number)this.fadestart.getValue()).longValue();
            if (System.currentTimeMillis() - this.startTime > ((Number)this.fadestart.getValue()).longValue()) {
                double normal = this.normalize(time, 0.0, this.fadetime.getValue());
                normal = MathHelper.clamp((double)normal, (double)0.0, (double)1.0);
                normal = -normal + 1.0;
                lineA *= (int)normal;
                fillA *= (int)normal;
            }
            Color lineColor = PopChams.newAlpha(lineColorS, lineA);
            Color fillColor = PopChams.newAlpha(fillColorS, fillA);
            if (this.player != null && this.playerModel != null) {
                TessellatorUtil.prepareGL();
                GL11.glPushAttrib((int)1048575);
                GL11.glEnable((int)2881);
                GL11.glEnable((int)2848);
                if (this.alphaFill > 1.0) {
                    this.alphaFill -= this.fadetime.getValue();
                }
                Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)this.alphaFill);
                if (this.alphaLine > 1.0) {
                    this.alphaLine -= this.fadetime.getValue();
                }
                Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int)this.alphaLine);
                PopChams.glColor(fillFinal);
                GL11.glPolygonMode((int)1032, (int)6914);
                PopChams.renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1.0f);
                PopChams.glColor(outlineFinal);
                GL11.glPolygonMode((int)1032, (int)6913);
                PopChams.renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1.0f);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glPopAttrib();
                TessellatorUtil.releaseGL();
            }
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
        double x = entity.posX - PopChams.mc.getRenderManager().viewerPosX;
        double y = entity.posY - PopChams.mc.getRenderManager().viewerPosY;
        double z = entity.posZ - PopChams.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= 0.125;
        }
        float interpolateRotation = PopChams.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float interpolateRotation2 = PopChams.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float rotationInterp = interpolateRotation2 - interpolateRotation;
        float renderPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        PopChams.renderLivingAt(x, y, z);
        float f8 = PopChams.handleRotationFloat(entity, partialTicks);
        PopChams.prepareRotations(entity);
        float f9 = PopChams.prepareScale(entity, scale);
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9, (Entity)entity);
        modelBase.render((Entity)entity, limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9);
        GlStateManager.popMatrix();
    }

    public static void prepareTranslate(EntityLivingBase entityIn, double x, double y, double z) {
        PopChams.renderLivingAt(x - PopChams.mc.getRenderManager().viewerPosX, y - PopChams.mc.getRenderManager().viewerPosY, z - PopChams.mc.getRenderManager().viewerPosZ);
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


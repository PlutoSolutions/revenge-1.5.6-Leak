/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PenisEsp
extends Module {
    boolean enabled;
    private float spin;
    private float cumsize;
    private float amount;

    public PenisEsp() {
        super("PenisEsp", "Fan module", 0, Module.Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.spin = 0.0f;
        this.cumsize = 0.0f;
        this.amount = 0.0f;
        this.enabled = true;
    }

    @Override
    public void onDisable() {
        this.enabled = false;
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (this.enabled) {
            for (Object o : PenisEsp.mc.world.loadedEntityList) {
                if (!(o instanceof EntityPlayer)) continue;
                EntityPlayer player = (EntityPlayer)o;
                double x2 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)mc.getRenderPartialTicks();
                double x = x2 - PenisEsp.mc.getRenderManager().viewerPosX;
                double y2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)mc.getRenderPartialTicks();
                double y = y2 - PenisEsp.mc.getRenderManager().viewerPosY;
                double z2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)mc.getRenderPartialTicks();
                double z = z2 - PenisEsp.mc.getRenderManager().viewerPosZ;
                GL11.glPushMatrix();
                RenderHelper.disableStandardItemLighting();
                RenderUtil.drawPenis(player, x, y, z, this.spin, this.cumsize, this.amount);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
        }
    }
}


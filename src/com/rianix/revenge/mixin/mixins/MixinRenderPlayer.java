/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.manager.FriendsManager;
import com.rianix.revenge.module.modules.render.PlayerChams;
import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    public ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
        if (Revenge.moduleManager.getModuleByClass(PlayerChams.class).isToggled() && Revenge.moduleManager.getModuleByClass(PlayerChams.class).mode.getValue().equals("Textured")) {
            Color color = new Color(Revenge.moduleManager.getModuleByClass(PlayerChams.class).color.getAWTValue(true).getRed(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).color.getAWTValue(true).getGreen(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).color.getAWTValue(true).getBlue(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).color.getAWTValue(true).getAlpha());
            Color friendsColor = new Color(Revenge.moduleManager.getModuleByClass(PlayerChams.class).friendsColor.getAWTValue(true).getRed(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).friendsColor.getAWTValue(true).getGreen(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).friendsColor.getAWTValue(true).getBlue(), Revenge.moduleManager.getModuleByClass(PlayerChams.class).friendsColor.getAWTValue(true).getAlpha());
            if (FriendsManager.isFriend(entity.getName())) {
                GL11.glColor4f((float)((float)friendsColor.getRed() / 255.0f), (float)((float)friendsColor.getGreen() / 255.0f), (float)((float)friendsColor.getBlue() / 255.0f), (float)((float)friendsColor.getAlpha() / 255.0f));
            } else {
                GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            }
            return new ResourceLocation("minecraft:steve_skin1.png");
        }
        return entity.getLocationSkin();
    }
}


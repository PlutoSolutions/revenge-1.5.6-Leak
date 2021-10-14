/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.manager.FriendsManager;
import com.rianix.revenge.module.modules.render.PlayerChams;
import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderLivingBase.class})
public abstract class MixinRenderLivingBase<T extends EntityLivingBase>
extends Render<T> {
    PlayerChams plC = Revenge.moduleManager.getModuleByClass(PlayerChams.class);

    public MixinRenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn);
    }

    @Redirect(method={"renderModel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModelHook(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (this.plC.isToggled() && entityIn instanceof EntityPlayer && this.plC.mode.getValue().equals("Colored")) {
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            GL11.glEnable((int)2960);
            Color color = new Color(this.plC.color.getAWTValue(true).getRed(), this.plC.color.getAWTValue(true).getGreen(), this.plC.color.getAWTValue(true).getBlue(), this.plC.color.getAWTValue(true).getAlpha());
            Color friendsColor = new Color(this.plC.friendsColor.getAWTValue(true).getRed(), this.plC.friendsColor.getAWTValue(true).getGreen(), this.plC.friendsColor.getAWTValue(true).getBlue(), this.plC.friendsColor.getAWTValue(true).getAlpha());
            if (this.plC.xqz.getValue()) {
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glEnable((int)10754);
                if (FriendsManager.isFriend(entityIn.getName())) {
                    GL11.glColor4f((float)((float)friendsColor.getRed() / 255.0f), (float)((float)friendsColor.getGreen() / 255.0f), (float)((float)friendsColor.getBlue() / 255.0f), (float)((float)friendsColor.getAlpha() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                if (FriendsManager.isFriend(entityIn.getName())) {
                    GL11.glColor4f((float)((float)friendsColor.getRed() / 255.0f), (float)((float)friendsColor.getGreen() / 255.0f), (float)((float)friendsColor.getBlue() / 255.0f), (float)((float)friendsColor.getAlpha() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            } else {
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glEnable((int)10754);
                if (FriendsManager.isFriend(entityIn.getName())) {
                    GL11.glColor4f((float)((float)friendsColor.getRed() / 255.0f), (float)((float)friendsColor.getGreen() / 255.0f), (float)((float)friendsColor.getBlue() / 255.0f), (float)((float)friendsColor.getAlpha() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
            }
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
        } else {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (this.plC.isToggled() && this.plC.mode.getValue().equals("WallHack") && entity != null) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (this.plC.isToggled() && this.plC.mode.getValue().equals("WallHack") && entity != null) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }
}


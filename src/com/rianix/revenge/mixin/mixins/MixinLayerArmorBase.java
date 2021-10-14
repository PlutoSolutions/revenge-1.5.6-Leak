/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.layers.LayerArmorBase
 *  net.minecraft.entity.EntityLivingBase
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.render.NoRender;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LayerArmorBase.class})
public class MixinLayerArmorBase {
    @Inject(method={"doRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled() && Revenge.moduleManager.getModuleByClass(NoRender.class).armor.getValue()) {
            ci.cancel();
        }
    }
}


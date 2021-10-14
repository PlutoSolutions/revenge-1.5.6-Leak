/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  net.minecraft.client.Minecraft
 */
package com.rianix.revenge.mixin.mixins;

import com.google.common.base.Stopwatch;
import com.rianix.revenge.manager.ConfigManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public class MixinMinecraft {
    @Inject(method={"shutdown"}, at={@At(value="HEAD")})
    public void onShutdown(CallbackInfo ci) {
        Stopwatch watch = Stopwatch.createStarted();
        ConfigManager.save();
        System.out.printf("revenge save config took %sms", new Object[]{watch.stop()});
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package com.rianix.revenge.mixin.mixins.accessor;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Minecraft.class})
public interface IMinecraftMixin {
    @Accessor(value="rightClickDelayTimer")
    public void setRightClickDelayTimerAccessor(int var1);
}


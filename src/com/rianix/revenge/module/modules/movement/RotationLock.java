/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;

public class RotationLock
extends Module {
    public RotationLock() {
        super("RotationLock", "", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        RotationLock.mc.player.rotationYaw = (float)Math.round(RotationLock.mc.player.rotationYaw / 45.0f) * 45.0f;
    }
}


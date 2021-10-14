/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingDouble;

public class ReverseStep
extends Module {
    SettingDouble speed = this.register("Speed", 5.0, 0.0, 20.0);

    public ReverseStep() {
        super("ReverseStep", "", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava()) {
            return;
        }
        if (ReverseStep.mc.player.onGround) {
            ReverseStep.mc.player.motionY -= this.speed.getValue() / 10.0;
        }
    }
}


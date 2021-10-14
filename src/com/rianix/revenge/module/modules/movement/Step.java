/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;

public class Step
extends Module {
    SettingDouble height = this.register("Height", 2.5, 0.5, 2.0);
    SettingBoolean entity = this.register("EntityStep", false);

    public Step() {
        super("Step", "Step up blocks.", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (this.entity.getValue() && Step.mc.player.getRidingEntity() != null) {
            Step.mc.player.getRidingEntity().stepHeight = (float)this.height.getValue();
        }
        Step.mc.player.stepHeight = (float)this.height.getValue();
    }

    @Override
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
    }
}


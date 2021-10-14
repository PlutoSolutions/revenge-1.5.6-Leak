/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.Potion
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import java.util.Objects;
import net.minecraft.potion.Potion;

public class EffectRemover
extends Module {
    SettingBoolean levitation = this.register("Levitation", true);
    SettingBoolean jumpBoost = this.register("JumpBoost", true);

    public EffectRemover() {
        super("EffectRemover", "Removes the effects from you.", 0, Module.Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (this.levitation.getValue() && EffectRemover.mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation((String)"levitation")))) {
            EffectRemover.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation((String)"levitation"));
        }
        if (this.jumpBoost.getValue() && EffectRemover.mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation((String)"jump_boost")))) {
            EffectRemover.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation((String)"jump_boost"));
        }
    }
}


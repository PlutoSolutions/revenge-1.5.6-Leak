/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemExpBottle
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.mixin.mixins.accessor.IMinecraftMixin;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastPlace
extends Module {
    SettingBoolean everything = this.register("Everything", false);
    SettingBoolean blocks = this.register("Blocks", false);
    SettingBoolean crystals = this.register("Crystals", true);
    SettingBoolean exp = this.register("Exp", true);

    public FastPlace() {
        super("FastPlace", "", 0, Module.Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (this.everything.getValue()) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (this.exp.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || FastPlace.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (this.blocks.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock || FastPlace.mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (this.crystals.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal || FastPlace.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
    }
}


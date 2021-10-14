/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.SoundEvents
 *  net.minecraftforge.event.entity.PlaySoundAtEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundRemover
extends Module {
    SettingBoolean fireworks = this.register("Fireworks", false);
    SettingBoolean bats = this.register("Bats", false);
    SettingBoolean snowballs = this.register("Snowballs", false);

    public SoundRemover() {
        super("SoundRemover", "Removes sounds", 0, Module.Category.MISC);
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundAtEntityEvent event) {
        if (this.bats.getValue() && event.getSound().equals((Object)SoundEvents.ENTITY_BAT_AMBIENT) || event.getSound().equals((Object)SoundEvents.ENTITY_BAT_DEATH) || event.getSound().equals((Object)SoundEvents.ENTITY_BAT_HURT) || event.getSound().equals((Object)SoundEvents.ENTITY_BAT_LOOP) || event.getSound().equals((Object)SoundEvents.ENTITY_BAT_TAKEOFF)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
        if (this.fireworks.getValue() && event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_BLAST) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_BLAST_FAR) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_LARGE_BLAST) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_SHOOT) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_LAUNCH) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_TWINKLE) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_TWINKLE_FAR) || event.getSound().equals((Object)SoundEvents.ENTITY_FIREWORK_LARGE_BLAST_FAR)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
        if (this.snowballs.getValue() && event.getSound().equals((Object)SoundEvents.ENTITY_SNOWBALL_THROW)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
    }
}


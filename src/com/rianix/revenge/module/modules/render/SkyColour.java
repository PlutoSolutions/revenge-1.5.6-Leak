/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogColors
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import java.awt.Color;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColour
extends Module {
    SettingColor color = this.register("Color", Color.GREEN, false);
    SettingBoolean disableFog = this.register("DisableFog", true);

    public SkyColour() {
        super("SkyColour", "", 0, Module.Category.RENDER);
    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float)this.color.getAWTValue(true).getRed() / 255.0f);
        event.setGreen((float)this.color.getAWTValue(true).getGreen() / 255.0f);
        event.setBlue((float)this.color.getAWTValue(true).getBlue() / 255.0f);
    }

    @SubscribeEvent
    public void fog_density(EntityViewRenderEvent.FogDensity event) {
        if (this.disableFog.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity
 *  net.minecraftforge.client.event.RenderBlockOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender
extends Module {
    public SettingBoolean armor = this.register("Armor", true);
    SettingBoolean fog = this.register("Fog", true);
    SettingBoolean weather = this.register("Weather", true);
    SettingBoolean viewBobbing = this.register("ViewBobbing", true);
    SettingBoolean items = this.register("Items", false);
    SettingBoolean overlay = this.register("Overlay", true);

    public NoRender() {
        super("NoRender", "No render things", 0, Module.Category.RENDER);
    }

    @Override
    public void onUpdate() {
        if (this.weather.getValue() && NoRender.mc.world.isRaining()) {
            NoRender.mc.world.setRainStrength(0.0f);
        }
        if (this.items.getValue()) {
            NoRender.mc.world.loadedEntityList.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::func_70106_y);
        }
    }

    @Override
    public void onEnable() {
        if (this.viewBobbing.getValue()) {
            NoRender.mc.gameSettings.viewBobbing = false;
        }
    }

    @Override
    public void onDisable() {
        if (this.viewBobbing.getValue()) {
            NoRender.mc.gameSettings.viewBobbing = true;
        }
    }

    @SubscribeEvent
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (this.fog.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (this.overlay.getValue()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEvent(RenderGameOverlayEvent event) {
        if (this.overlay.getValue() && event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HELMET) || event.getType().equals((Object)RenderGameOverlayEvent.ElementType.PORTAL)) {
            event.setCanceled(true);
        }
    }
}


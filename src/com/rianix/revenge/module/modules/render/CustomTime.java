/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingDouble;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class CustomTime
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            event.cancel();
        }
    }, new Predicate[0]);
    long time = 0L;
    SettingDouble clientTime = this.register("Time", 18000.0, 0.0, 23992.0);

    public CustomTime() {
        super("CustomTime", "Allows you to change game time.", 0, Module.Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.time = CustomTime.mc.world.getWorldTime();
    }

    @Override
    public void onUpdate() {
        CustomTime.mc.world.setWorldTime((long)this.clientTime.getValue());
    }

    @Override
    public void onDisable() {
        CustomTime.mc.world.setWorldTime(this.time);
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == Velocity.mc.player.getEntityId()) {
            event.cancel();
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            event.cancel();
        }
    }, new Predicate[0]);

    public Velocity() {
        super("Velocity", "Removes knockback.", 0, Module.Category.PLAYER);
    }
}


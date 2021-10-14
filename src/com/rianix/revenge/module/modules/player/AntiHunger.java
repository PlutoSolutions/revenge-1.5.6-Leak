/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketPlayer
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiHunger
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof CPacketPlayer) {
            boolean groundCheck;
            CPacketPlayer player = (CPacketPlayer)event.getPacket();
            double differenceY = AntiHunger.mc.player.posY - AntiHunger.mc.player.lastTickPosY;
            boolean bl = groundCheck = differenceY == 0.0;
            if (groundCheck && !AntiHunger.mc.playerController.isHittingBlock) {
                AntiHunger.mc.player.onGround = true;
            }
        }
    }, new Predicate[0]);

    public AntiHunger() {
        super("AntiHunger", "Causes you to not lose hunger", 0, Module.Category.PLAYER);
    }
}


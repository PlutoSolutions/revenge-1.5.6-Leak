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

public class DeathExplorer
extends Module {
    boolean explorer;
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<PacketEvent.Send>(event -> {
        if (this.explorer && event.getPacket() instanceof CPacketPlayer) {
            event.cancel();
        }
    }, new Predicate[0]);

    public DeathExplorer() {
        super("DeathExplorer", "", 0, Module.Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (DeathExplorer.mc.player.getHealth() == 0.0f) {
            DeathExplorer.mc.player.setHealth(20.0f);
            DeathExplorer.mc.player.isDead = false;
            this.explorer = true;
            mc.displayGuiScreen(null);
            DeathExplorer.mc.player.setPositionAndUpdate(DeathExplorer.mc.player.posX, DeathExplorer.mc.player.posY, DeathExplorer.mc.player.posZ);
        }
    }

    @Override
    public void onDisable() {
        if (DeathExplorer.mc.player != null) {
            DeathExplorer.mc.player.respawnPlayer();
        }
        this.explorer = false;
    }
}


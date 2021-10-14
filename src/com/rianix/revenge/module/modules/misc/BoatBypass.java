/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemBoat
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemBoat;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class BoatBypass
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && BoatBypass.mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat || BoatBypass.mc.player.getHeldItemOffhand().getItem() instanceof ItemBoat) {
            event.cancel();
        }
    }, new Predicate[0]);

    public BoatBypass() {
        super("BoatBypass", "Allows you to place the boat on servers where it is patched.", 0, Module.Category.MISC);
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.util.EnumHand
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;

public class AutoFish
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        SPacketSoundEffect packet;
        if (event.getPacket() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)event.getPacket()).getSound().equals((Object)SoundEvents.ENTITY_BOBBER_SPLASH)) {
            if (AutoFish.mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (AutoFish.mc.player.getHeldItemOffhand().getItem() instanceof ItemFishingRod) {
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                AutoFish.mc.player.swingArm(EnumHand.OFF_HAND);
                AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                AutoFish.mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
    }, new Predicate[0]);

    public AutoFish() {
        super("AutoFish", "Fishes automatically.", 0, Module.Category.MISC);
    }
}


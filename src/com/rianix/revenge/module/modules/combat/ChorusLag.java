/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.event.entity.living.LivingEntityUseItemEvent$Finish
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChorusLag
extends Module {
    SettingInteger sDelay = this.register("TickDelay", 18, 0, 500);
    int delay = 0;
    int delay2 = 0;
    boolean ateChorus = false;
    boolean hackPacket = false;
    boolean posTp = false;
    double posX;
    double posY;
    double posZ;
    Queue<CPacketPlayer> packets = new LinkedList<CPacketPlayer>();
    Queue<CPacketConfirmTeleport> packetss = new LinkedList<CPacketConfirmTeleport>();
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketConfirmTeleport && this.ateChorus && this.delay2 < this.sDelay.getValue()) {
            this.packetss.add((CPacketConfirmTeleport)event.getPacket());
            event.cancel();
        }
        if (event.getPacket() instanceof CPacketPlayer && this.ateChorus && this.delay2 < this.sDelay.getValue()) {
            this.packets.add((CPacketPlayer)event.getPacket());
            event.cancel();
        }
    }, new Predicate[0]);

    public ChorusLag() {
        super("ChorusLag", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.ateChorus = false;
        this.hackPacket = false;
        this.posTp = false;
    }

    @Override
    public void onUpdate() {
        if (this.ateChorus) {
            ++this.delay;
            ++this.delay2;
            if (!ChorusLag.mc.player.getPosition().equals((Object)new BlockPos(this.posX, this.posY, this.posZ)) && !this.posTp && ChorusLag.mc.player.getDistance(this.posX, this.posY, this.posZ) > 1.0) {
                ChorusLag.mc.player.setPosition(this.posX, this.posY, this.posZ);
                this.posTp = true;
            }
        }
        if (this.ateChorus && this.delay2 > this.sDelay.getValue()) {
            this.ateChorus = false;
            this.delay = 0;
            this.hackPacket = true;
            this.delay2 = 0;
            this.sendPackets();
        }
        if (this.delay2 == this.sDelay.getValue() - 40) {
            Messages.sendClientMessage("Chorusing in 2 seconds");
        }
    }

    public void sendPackets() {
        while (!this.packets.isEmpty()) {
            ChorusLag.mc.player.connection.sendPacket((Packet)this.packets.poll());
        }
        while (!this.packetss.isEmpty()) {
            ChorusLag.mc.player.connection.sendPacket((Packet)this.packetss.poll());
        }
        this.hackPacket = false;
        this.delay2 = 0;
        this.ateChorus = false;
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() == ChorusLag.mc.player && event.getResultStack().getItem().equals((Object)Items.CHORUS_FRUIT)) {
            this.posX = ChorusLag.mc.player.posX;
            this.posY = ChorusLag.mc.player.posY;
            this.posZ = ChorusLag.mc.player.posZ;
            this.posTp = false;
            this.ateChorus = true;
        }
    }
}


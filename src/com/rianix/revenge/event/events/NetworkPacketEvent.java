/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package com.rianix.revenge.event.events;

import com.rianix.revenge.event.Event;
import net.minecraft.network.Packet;

public class NetworkPacketEvent
extends Event {
    public Packet m_Packet;

    public NetworkPacketEvent(Packet p_Packet) {
        this.m_Packet = p_Packet;
    }

    public Packet GetPacket() {
        return this.m_Packet;
    }

    public Packet getPacket() {
        return this.m_Packet;
    }
}


/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 */
package com.rianix.revenge.event.events;

import com.rianix.revenge.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class PushEvent
extends Event {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public double x;
    public double y;
    public double z;
    public Entity entity;

    public PushEvent(Entity entity, double x, double y, double z) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}


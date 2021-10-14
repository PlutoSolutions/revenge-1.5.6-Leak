/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.event.events;

import com.rianix.revenge.event.Event;

public class Render3DEvent
extends Event {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }
}


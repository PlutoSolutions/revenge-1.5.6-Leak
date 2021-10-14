/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketVehicleMove
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;

public class EntityVanish
extends Module {
    Entity entity;

    public EntityVanish() {
        super("EntityVanish", "", 0, Module.Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if (EntityVanish.mc.player.getRidingEntity() != null) {
            this.entity = EntityVanish.mc.player.getRidingEntity();
            EntityVanish.mc.player.dismountRidingEntity();
            EntityVanish.mc.world.removeEntity(this.entity);
        }
    }

    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            this.toggle();
            return;
        }
        if (this.entity != null) {
            try {
                this.entity.posX = EntityVanish.mc.player.posX;
                this.entity.posY = EntityVanish.mc.player.posY;
                this.entity.posZ = EntityVanish.mc.player.posZ;
                Objects.requireNonNull(mc.getConnection()).sendPacket((Packet)new CPacketVehicleMove(this.entity));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}


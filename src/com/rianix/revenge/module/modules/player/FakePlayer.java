/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.network.FMLNetworkEvent$ClientDisconnectionFromServerEvent
 */
package com.rianix.revenge.module.modules.player;

import com.mojang.authlib.GameProfile;
import com.rianix.revenge.module.Module;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class FakePlayer
extends Module {
    public FakePlayer() {
        super("FakePlayer", "Spawns a bot", 0, Module.Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if (FakePlayer.mc.player.isDead) {
            this.toggle();
            return;
        }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("4b7ed64e-747e-4ee0-a9d8-0349ae33e7e7"), "FakePlayer"));
        fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        fakePlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        fakePlayer.rotationYaw = FakePlayer.mc.player.rotationYaw;
        fakePlayer.rotationPitch = FakePlayer.mc.player.rotationPitch;
        fakePlayer.setHealth(FakePlayer.mc.player.getHealth() + FakePlayer.mc.player.getAbsorptionAmount());
        fakePlayer.inventory = FakePlayer.mc.player.inventory;
        FakePlayer.mc.world.addEntityToWorld(-101, (Entity)fakePlayer);
        fakePlayer.onLivingUpdate();
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.toggle();
    }

    @Override
    public void onDisable() {
        if (FakePlayer.mc.world != null) {
            FakePlayer.mc.world.removeEntityFromWorld(-101);
        }
    }
}


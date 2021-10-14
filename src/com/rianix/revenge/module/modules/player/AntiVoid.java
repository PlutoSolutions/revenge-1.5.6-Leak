/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class AntiVoid
extends Module {
    public AntiVoid() {
        super("AntiVoid", "", 0, Module.Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (AntiVoid.mc.player == null || AntiVoid.mc.world == null) {
            return;
        }
        if (!AntiVoid.mc.player.noClip && AntiVoid.mc.player.posY <= 0.0) {
            RayTraceResult trace = AntiVoid.mc.world.rayTraceBlocks(AntiVoid.mc.player.getPositionVector(), new Vec3d(AntiVoid.mc.player.posX, 0.0, AntiVoid.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            AntiVoid.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (AntiVoid.mc.player.getRidingEntity() != null) {
                AntiVoid.mc.player.getRidingEntity().setVelocity(0.0, 0.0, 0.0);
            }
        }
    }
}


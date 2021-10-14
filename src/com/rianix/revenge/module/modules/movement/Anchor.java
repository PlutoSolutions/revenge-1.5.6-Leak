/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Anchor
extends Module {
    public SettingBoolean pull = this.register("Pull", true);
    public SettingBoolean disable = this.register("DisableInHole", false);
    public SettingInteger pitch = this.register("Pitch", 60, 0, 90);
    int holeBlocks;

    public Anchor() {
        super("Anchor", "", 0, Module.Category.MOVEMENT);
    }

    public boolean isBlockHole(BlockPos blockpos) {
        this.holeBlocks = 0;
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 0, 0)).getBlock() == Blocks.AIR) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeBlocks;
        }
        if (Anchor.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeBlocks;
        }
        return this.holeBlocks >= 9;
    }

    public Vec3d GetCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }

    @Override
    public void onUpdate() {
        if (Anchor.mc.player.posY < 0.0) {
            return;
        }
        if (Anchor.mc.player.rotationPitch >= (float)this.pitch.getValue() && (this.isBlockHole(this.getPlayerPos().down(1)) || this.isBlockHole(this.getPlayerPos().down(2)) || this.isBlockHole(this.getPlayerPos().down(3)) || this.isBlockHole(this.getPlayerPos().down(4)))) {
            if (!this.pull.getValue()) {
                Anchor.mc.player.motionX = 0.0;
                Anchor.mc.player.motionZ = 0.0;
            } else {
                Vec3d center = this.GetCenter(Anchor.mc.player.posX, Anchor.mc.player.posY, Anchor.mc.player.posZ);
                double XDiff = Math.abs(center.x - Anchor.mc.player.posX);
                double ZDiff = Math.abs(center.z - Anchor.mc.player.posZ);
                if (XDiff <= 0.1 && ZDiff <= 0.1) {
                    center = Vec3d.ZERO;
                } else {
                    double MotionX = center.x - Anchor.mc.player.posX;
                    double MotionZ = center.z - Anchor.mc.player.posZ;
                    Anchor.mc.player.motionX = MotionX / 2.0;
                    Anchor.mc.player.motionZ = MotionZ / 2.0;
                }
            }
            if (this.disable.getValue() && this.isBlockHole(this.getPlayerPos().down(1))) {
                this.toggle();
            }
        }
    }

    @Override
    public void onDisable() {
        this.holeBlocks = 0;
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Anchor.mc.player.posX), Math.floor(Anchor.mc.player.posY), Math.floor(Anchor.mc.player.posZ));
    }
}


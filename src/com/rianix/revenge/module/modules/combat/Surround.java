/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.BlockUtil;
import com.rianix.revenge.util.EntityUtil;
import com.rianix.revenge.util.InventoryUtil;
import com.rianix.revenge.util.PlayerUtil;
import com.rianix.revenge.util.Timer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Surround
extends Module {
    SettingInteger delay = this.register("Delay", 0, 0, 250);
    SettingInteger blocksPerTick = this.register("BPT", 20, 1, 20);
    SettingBoolean center = this.register("Center", true);
    SettingBoolean rotate = this.register("Rotate", false);
    SettingBoolean helpingBlocks = this.register("HelpingBlocks", true);
    SettingBoolean alwaysHelp = this.register("AlwaysHelp", false);
    Timer timer = new Timer();
    Timer retryTimer = new Timer();
    Set<Vec3d> extendingBlocks = new HashSet<Vec3d>();
    Map<BlockPos, Integer> retries = new HashMap<BlockPos, Integer>();
    BlockPos startPos;
    boolean didPlace = false;
    int lastHotbarSlot;
    boolean isSneaking;
    int placements = 0;
    int extenders = 1;
    boolean offHand = false;
    boolean isPlacing = false;

    public Surround() {
        super("Surround", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.lastHotbarSlot = Surround.mc.player.inventory.currentItem;
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
        if (this.center.getValue()) {
            if (Surround.mc.world.getBlockState(new BlockPos(Surround.mc.player.getPositionVector())).getBlock() == Blocks.WEB) {
                Revenge.positionManager.setPositionPacket(Surround.mc.player.posX, this.startPos.getY(), Surround.mc.player.posZ, true, true, true);
            } else {
                Revenge.positionManager.setPositionPacket((double)this.startPos.getX() + 0.5, this.startPos.getY(), (double)this.startPos.getZ() + 0.5, true, true, true);
            }
        }
        this.retries.clear();
        this.retryTimer.reset();
    }

    @Override
    public void onUpdate() {
        boolean onEChest;
        if (this.check()) {
            return;
        }
        boolean bl = onEChest = Surround.mc.world.getBlockState(new BlockPos(Surround.mc.player.getPositionVector())).getBlock() == Blocks.ENDER_CHEST;
        if (Surround.mc.player.posY - (double)((int)Surround.mc.player.posY) < 0.7) {
            onEChest = false;
        }
        if (!BlockUtil.isSafe((Entity)Surround.mc.player, onEChest ? 1 : 0, true)) {
            this.placeBlocks(Surround.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)Surround.mc.player, onEChest ? 1 : 0, true), this.helpingBlocks.getValue(), false);
        } else if (!BlockUtil.isSafe((Entity)Surround.mc.player, onEChest ? 0 : -1, false) && this.alwaysHelp.getValue()) {
            this.placeBlocks(Surround.mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray((Entity)Surround.mc.player, onEChest ? 0 : -1, false), false, false);
        }
        this.processExtendingBlocks();
        if (this.didPlace) {
            this.timer.reset();
        }
    }

    @Override
    public void onDisable() {
        this.isPlacing = false;
        this.isSneaking = PlayerUtil.stopSneaking(this.isSneaking);
    }

    private void processExtendingBlocks() {
        if (this.extendingBlocks.size() == 2 && this.extenders < 1) {
            Vec3d[] array = new Vec3d[2];
            int i = 0;
            Iterator<Vec3d> iterator = this.extendingBlocks.iterator();
            while (iterator.hasNext()) {
                Vec3d extendingBlock;
                Vec3d vec3d;
                array[i] = vec3d = (extendingBlock = iterator.next());
                ++i;
            }
            int placementsBefore = this.placements;
            if (this.areClose(array) != null) {
                this.placeBlocks(this.areClose(array), EntityUtil.getUnsafeBlockArrayFromVec3d(this.areClose(array), 0, true), true, false);
            }
            if (placementsBefore < this.placements) {
                this.extendingBlocks.clear();
            }
        } else if (this.extendingBlocks.size() > 2 || this.extenders >= 1) {
            this.extendingBlocks.clear();
        }
    }

    private Vec3d areClose(Vec3d[] vec3ds) {
        int matches = 0;
        for (Vec3d vec3d : vec3ds) {
            for (Vec3d pos : EntityUtil.getUnsafeBlockArray((Entity)Surround.mc.player, 0, true)) {
                if (!vec3d.equals((Object)pos)) continue;
                ++matches;
            }
        }
        if (matches == 2) {
            return Surround.mc.player.getPositionVector().add(vec3ds[0].add(vec3ds[1]));
        }
        return null;
    }

    private boolean placeBlocks(Vec3d pos, Vec3d[] vec3ds, boolean hasHelpingBlocks, boolean isHelping) {
        boolean gotHelp = true;
        block5: for (Vec3d vec3d : vec3ds) {
            gotHelp = true;
            BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            switch (BlockUtil.isPositionPlaceable(position, false)) {
                case 1: {
                    if (this.retries.get((Object)position) == null || this.retries.get((Object)position) < 4) {
                        this.placeBlock(position);
                        this.retries.put(position, this.retries.get((Object)position) == null ? 1 : this.retries.get((Object)position) + 1);
                        this.retryTimer.reset();
                        continue block5;
                    }
                }
                case 2: {
                    if (!hasHelpingBlocks) continue block5;
                    gotHelp = this.placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true);
                }
                case 3: {
                    if (gotHelp) {
                        this.placeBlock(position);
                    }
                    if (!isHelping) continue block5;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check() {
        if (Surround.mc.player.posY < Surround.mc.player.posY) {
            this.toggle();
        }
        if (!this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player))) {
            this.toggle();
            return true;
        }
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        this.offHand = InventoryUtil.isBlock(Surround.mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
        this.isPlacing = false;
        this.didPlace = false;
        this.extenders = 1;
        this.placements = 0;
        if (obbySlot == -1 && !this.offHand && echestSlot == -1) {
            this.toggle();
            return true;
        }
        this.isSneaking = PlayerUtil.stopSneaking(this.isSneaking);
        if (Surround.mc.player.inventory.currentItem != this.lastHotbarSlot && Surround.mc.player.inventory.currentItem != obbySlot && Surround.mc.player.inventory.currentItem != echestSlot) {
            this.lastHotbarSlot = Surround.mc.player.inventory.currentItem;
        }
        if (this.retryTimer.passedMs(2500L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        return !this.timer.passedMs(this.delay.getValue());
    }

    private void placeBlock(BlockPos pos) {
        if (this.placements < this.blocksPerTick.getValue()) {
            int originalSlot = Surround.mc.player.inventory.currentItem;
            int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
            this.isPlacing = true;
            Surround.mc.player.inventory.currentItem = obbySlot == -1 ? echestSlot : obbySlot;
            Surround.mc.playerController.updateController();
            this.isSneaking = BlockUtil.placeBlock(pos, this.offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), true, this.isSneaking);
            Surround.mc.player.inventory.currentItem = originalSlot;
            Surround.mc.playerController.updateController();
            this.didPlace = true;
            ++this.placements;
        }
    }
}


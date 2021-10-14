/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 */
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingMode;
import com.rianix.revenge.util.BlockUtil;
import com.rianix.revenge.util.InventoryUtil;
import com.rianix.revenge.util.PlayerUtil;
import com.rianix.revenge.util.RotationUtil;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class DamageBlocker
extends Module {
    ArrayList<String> placeMode = new ArrayList();
    SettingMode mode = this.register("Mode", this.placeMode, "City");
    SettingBoolean rotate = this.register("Rotate", false);

    public DamageBlocker() {
        super("DamageBlocker", "", 0, Module.Category.COMBAT);
        this.placeMode.add("City");
        this.placeMode.add("Around");
    }

    @Override
    public void onUpdate() {
        BlockPos cp = this.addPos(Objects.requireNonNull(RotationUtil.getDirection4DCityPos()));
        BlockPos ap = this.addPos(Objects.requireNonNull(RotationUtil.getDirection4DAroundPos()));
        int originalSlot = DamageBlocker.mc.player.inventory.currentItem;
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            this.toggle();
            return;
        }
        DamageBlocker.mc.player.inventory.currentItem = obbySlot == -1 ? eChestSot : obbySlot;
        DamageBlocker.mc.playerController.updateController();
        if (this.mode.getValue().equals("City")) {
            BlockUtil.placeBlock(cp, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        }
        if (this.mode.getValue().equals("Around")) {
            BlockUtil.placeBlock(ap, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        }
        DamageBlocker.mc.player.inventory.currentItem = originalSlot;
        DamageBlocker.mc.playerController.updateController();
        this.toggle();
    }

    private BlockPos addPos(BlockPos pos) {
        BlockPos pPos = PlayerUtil.getPlayerPos(0.2);
        return new BlockPos(pPos.getX() + pos.getX(), pPos.getY() + pos.getY(), pPos.getZ() + pos.getZ());
    }
}


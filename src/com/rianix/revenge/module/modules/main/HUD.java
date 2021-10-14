/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.rianix.revenge.module.modules.main;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingMode;
import com.rianix.revenge.util.MathUtil;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD
extends Module {
    ArrayList<String> greeterMode = new ArrayList();
    SettingColor color = this.register("Color", Color.GREEN, false);
    SettingBoolean watermark = this.register("Watermark", true);
    SettingMode greeter = this.register("Greeter", this.greeterMode, "None");
    SettingBoolean arrayList = this.register("ArrayList", true);
    SettingBoolean coords = this.register("Coords", true);
    SettingBoolean direction = this.register("Direction", true);
    SettingBoolean ping = this.register("Ping", true);

    public HUD() {
        super("HUD", "", 0, Module.Category.MAIN);
        this.greeterMode.add("None");
        this.greeterMode.add("Client");
        this.greeterMode.add("Time");
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            boolean inHell;
            ScaledResolution sr = new ScaledResolution(mc);
            if (this.arrayList.getValue()) {
                int y = 2;
                ArrayList<String> list = new ArrayList<String>();
                for (Module m : Revenge.moduleManager.getModules()) {
                    if (m.isToggled() && m.getCategory() != Module.Category.MAIN) {
                        list.add(m.getFullDisplayInfo());
                    }
                    list.sort(Comparator.comparingInt(s -> Revenge.fontManager.getStringWidth((String)s)));
                    Collections.reverse(list);
                }
                for (String name : list) {
                    Revenge.fontManager.drawStringWithShadow(name, (float)((double)(sr.getScaledWidth() - Revenge.fontManager.getStringWidth(name)) - 0.5), y, this.color.getAWTValue(true).getRGB());
                    y += 10;
                }
            }
            float nether = !(inHell = HUD.mc.world.getBiome(HUD.mc.player.getPosition()).getBiomeName().equals("Hell")) ? 0.125f : 8.0f;
            String posX = new DecimalFormat("#0.0").format(HUD.mc.player.posX);
            String posY = new DecimalFormat("#0.0").format(HUD.mc.player.posY);
            String posZ = new DecimalFormat("#0.0").format(HUD.mc.player.posZ);
            String netherPosX = new DecimalFormat("#0.0").format(HUD.mc.player.posX * (double)nether);
            String netherPosZ = new DecimalFormat("#0.0").format(HUD.mc.player.posZ * (double)nether);
            String coordinates = "XYZ " + posX + ", " + posY + ", " + posZ + " [" + netherPosX + ", " + netherPosZ + "]";
            String dir = Revenge.rotationManager.getDirection4DString();
            int y = 530;
            if (this.coords.getValue()) {
                Revenge.fontManager.drawStringWithShadow(coordinates, 1.0f, y, this.color.getAWTValue(true).getRGB());
                y -= 10;
            }
            if (this.direction.getValue()) {
                Revenge.fontManager.drawStringWithShadow(dir, 1.0f, y, this.color.getAWTValue(true).getRGB());
            }
            int y2 = 1;
            if (this.watermark.getValue()) {
                Revenge.fontManager.drawStringWithShadow("Revenge v1.5.6", 1.0f, y2, this.color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (this.greeter.getValue().equals("Client")) {
                Revenge.fontManager.drawStringWithShadow("Welcome to Revenge " + (Object)ChatFormatting.GRAY + HUD.mc.player.getName() + (Object)ChatFormatting.RESET + "!", 1.0f, y2, this.color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (this.greeter.getValue().equals("Time")) {
                Revenge.fontManager.drawStringWithShadow(MathUtil.getTimeOfDay() + (Object)ChatFormatting.GRAY + HUD.mc.player.getName() + (Object)ChatFormatting.RESET + "!", 1.0f, y2, this.color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (this.ping.getValue()) {
                Revenge.fontManager.drawStringWithShadow("Ping: " + (Object)ChatFormatting.GRAY + Revenge.serverManager.getPing() + "ms", 1.0f, y2, this.color.getAWTValue(true).getRGB());
                y2 += 10;
            }
        }
    }
}


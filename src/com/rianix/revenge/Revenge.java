/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package com.rianix.revenge;

import com.google.common.base.Stopwatch;
import com.rianix.revenge.event.EventProcessor;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.manager.ConfigManager;
import com.rianix.revenge.manager.FontManager;
import com.rianix.revenge.manager.PositionManager;
import com.rianix.revenge.manager.RotationManager;
import com.rianix.revenge.manager.ServerManager;
import com.rianix.revenge.module.ModuleManager;
import com.rianix.revenge.setting.SettingsManager;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="revenge", name="revenge", version="1.5.6")
public class Revenge {
    public static final String MODID = "revenge";
    public static final String NAME = "revenge";
    public static final String VERSION = "1.5.6";
    public static final String NAME_VERSION = "Revenge v1.5.6";
    public static final EventManager EVENT_BUS = new EventManager();
    @Mod.Instance
    public static Revenge instance = new Revenge();
    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static FontManager fontManager;
    public static ServerManager serverManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public Screen clickGui;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)instance);
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        fontManager = new FontManager();
        serverManager = new ServerManager();
        rotationManager = new RotationManager();
        positionManager = new PositionManager();
        new EventProcessor();
        this.clickGui = new Screen();
        Stopwatch watch = Stopwatch.createStarted();
        ConfigManager.load();
        System.out.printf("revenge load config took %sms", new Object[]{watch.stop()});
    }
}


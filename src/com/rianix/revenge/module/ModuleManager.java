/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package com.rianix.revenge.module;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.module.modules.combat.AutoTotem;
import com.rianix.revenge.module.modules.combat.BowBomb;
import com.rianix.revenge.module.modules.combat.ChorusLag;
import com.rianix.revenge.module.modules.combat.DamageBlocker;
import com.rianix.revenge.module.modules.combat.FastBow;
import com.rianix.revenge.module.modules.combat.KillAura;
import com.rianix.revenge.module.modules.combat.Safety;
import com.rianix.revenge.module.modules.combat.SilentEXP;
import com.rianix.revenge.module.modules.combat.Surround;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.module.modules.main.ConfigSave;
import com.rianix.revenge.module.modules.main.Font;
import com.rianix.revenge.module.modules.main.Globals;
import com.rianix.revenge.module.modules.main.HUD;
import com.rianix.revenge.module.modules.misc.Announcer;
import com.rianix.revenge.module.modules.misc.AutoFish;
import com.rianix.revenge.module.modules.misc.BoatBypass;
import com.rianix.revenge.module.modules.misc.ChestStealer;
import com.rianix.revenge.module.modules.misc.FastPlace;
import com.rianix.revenge.module.modules.misc.NameProtect;
import com.rianix.revenge.module.modules.misc.Replenish;
import com.rianix.revenge.module.modules.misc.SoundRemover;
import com.rianix.revenge.module.modules.movement.Anchor;
import com.rianix.revenge.module.modules.movement.AutoWalk;
import com.rianix.revenge.module.modules.movement.EntitySpeed;
import com.rianix.revenge.module.modules.movement.NoSlow;
import com.rianix.revenge.module.modules.movement.PhaseWalk;
import com.rianix.revenge.module.modules.movement.ReverseStep;
import com.rianix.revenge.module.modules.movement.RotationLock;
import com.rianix.revenge.module.modules.movement.Sprint;
import com.rianix.revenge.module.modules.movement.Step;
import com.rianix.revenge.module.modules.movement.Strafe;
import com.rianix.revenge.module.modules.movement.YPort;
import com.rianix.revenge.module.modules.player.AntiHunger;
import com.rianix.revenge.module.modules.player.AntiVoid;
import com.rianix.revenge.module.modules.player.AutoRespawn;
import com.rianix.revenge.module.modules.player.DeathExplorer;
import com.rianix.revenge.module.modules.player.EffectRemover;
import com.rianix.revenge.module.modules.player.EntityVanish;
import com.rianix.revenge.module.modules.player.FakePlayer;
import com.rianix.revenge.module.modules.player.Gamemode;
import com.rianix.revenge.module.modules.player.MCF;
import com.rianix.revenge.module.modules.player.MCP;
import com.rianix.revenge.module.modules.player.SilentChorus;
import com.rianix.revenge.module.modules.player.Velocity;
import com.rianix.revenge.module.modules.render.CrystalChams;
import com.rianix.revenge.module.modules.render.CrystalScale;
import com.rianix.revenge.module.modules.render.CustomTime;
import com.rianix.revenge.module.modules.render.FullBright;
import com.rianix.revenge.module.modules.render.NoRender;
import com.rianix.revenge.module.modules.render.PenisEsp;
import com.rianix.revenge.module.modules.render.PlayerChams;
import com.rianix.revenge.module.modules.render.PopChams;
import com.rianix.revenge.module.modules.render.SkyColour;
import com.rianix.revenge.module.modules.render.ThirdPerson;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.init();
    }

    public void init() {
        this.modules.add(new AutoTotem());
        this.modules.add(new BowBomb());
        this.modules.add(new ChorusLag());
        this.modules.add(new DamageBlocker());
        this.modules.add(new FastBow());
        this.modules.add(new KillAura());
        this.modules.add(new Safety());
        this.modules.add(new SilentEXP());
        this.modules.add(new Surround());
        this.modules.add(new ClickGuiModule());
        this.modules.add(new ConfigSave());
        this.modules.add(new Globals());
        this.modules.add(new Font());
        this.modules.add(new HUD());
        this.modules.add(new Announcer());
        this.modules.add(new AutoFish());
        this.modules.add(new BoatBypass());
        this.modules.add(new ChestStealer());
        this.modules.add(new FastPlace());
        this.modules.add(new NameProtect());
        this.modules.add(new Replenish());
        this.modules.add(new SoundRemover());
        this.modules.add(new Anchor());
        this.modules.add(new AutoWalk());
        this.modules.add(new EntitySpeed());
        this.modules.add(new NoSlow());
        this.modules.add(new PhaseWalk());
        this.modules.add(new ReverseStep());
        this.modules.add(new RotationLock());
        this.modules.add(new Sprint());
        this.modules.add(new Step());
        this.modules.add(new Strafe());
        this.modules.add(new YPort());
        this.modules.add(new AntiHunger());
        this.modules.add(new AntiVoid());
        this.modules.add(new AutoRespawn());
        this.modules.add(new DeathExplorer());
        this.modules.add(new EffectRemover());
        this.modules.add(new FakePlayer());
        this.modules.add(new Gamemode());
        this.modules.add(new MCF());
        this.modules.add(new MCP());
        this.modules.add(new EntityVanish());
        this.modules.add(new SilentChorus());
        this.modules.add(new Velocity());
        this.modules.add(new CrystalChams());
        this.modules.add(new CrystalScale());
        this.modules.add(new CustomTime());
        this.modules.add(new FullBright());
        this.modules.add(new NoRender());
        this.modules.add(new PenisEsp());
        this.modules.add(new PlayerChams());
        this.modules.add(new PopChams());
        this.modules.add(new SkyColour());
        this.modules.add(new ThirdPerson());
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    public <R extends Module> R getModuleByClass(Class<R> clazz) {
        return (R)((Module)this.modules.stream().filter(m -> m.getClass().equals(clazz)).findFirst().orElse(null));
    }

    public ArrayList<Module> getModsInCategory(Module.Category cat) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : this.modules) {
            if (m.getCategory() != cat) continue;
            mods.add(m);
        }
        return mods;
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module m : this.modules) {
                if (m.getKey() != Keyboard.getEventKey()) continue;
                m.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }
        for (Module m : this.modules) {
            if (!m.isToggled()) continue;
            m.onUpdate();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        for (Module m : this.modules) {
            if (!m.isToggled()) continue;
            m.render();
        }
    }
}


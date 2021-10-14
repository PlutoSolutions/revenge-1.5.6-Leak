/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;

public class ThirdPerson
extends Module {
    public ThirdPerson() {
        super("ThirdPerson", "", 0, Module.Category.RENDER);
    }

    @Override
    public void onEnable() {
        ThirdPerson.mc.gameSettings.thirdPersonView = 1;
    }

    @Override
    public void onDisable() {
        ThirdPerson.mc.gameSettings.thirdPersonView = 0;
    }
}


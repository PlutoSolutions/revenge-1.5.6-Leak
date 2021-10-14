/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.rianix.revenge.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.manager.FriendsManager;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.setting.settings.SettingMode;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;

public class ConfigManager {
    private static final String CONFIG_FOLDER = "revenge/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser PARSER = new JsonParser();

    private ConfigManager() {
    }

    public static void load() {
        if (Files.notExists(Paths.get(CONFIG_FOLDER, new String[0]), new LinkOption[0])) {
            return;
        }
        if (Files.exists(Paths.get("revenge/other/friends.json", new String[0]), new LinkOption[0])) {
            try (InputStream stream = Files.newInputStream(Paths.get("revenge/other/friends.json", new String[0]), new OpenOption[0]);){
                FriendsManager.deserialize(PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonArray());
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        for (Module module : Revenge.moduleManager.getModules()) {
            if (Files.notExists(Paths.get(CONFIG_FOLDER + module.getCategory().name() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) continue;
            try {
                InputStream stream = Files.newInputStream(Paths.get(CONFIG_FOLDER + module.getCategory().name() + "/" + module.getName() + ".json", new String[0]), new OpenOption[0]);
                Throwable throwable = null;
                try {
                    JsonElement el = PARSER.parse((Reader)new InputStreamReader(stream));
                    if (!el.isJsonObject()) continue;
                    JsonObject obj = el.getAsJsonObject();
                    if (obj.has("bind")) {
                        module.setKey(obj.get("bind").getAsInt());
                    }
                    if (obj.has("enabled")) {
                        module.setToggled(obj.get("enabled").getAsBoolean());
                    }
                    if (!obj.has("settings")) continue;
                    JsonObject setObj = obj.getAsJsonObject("settings");
                    Revenge.settingsManager.getSettingsInMod(module).forEach(s -> {
                        if (setObj.has(s.getName())) {
                            switch (s.getType()) {
                                case INTEGER: {
                                    ((SettingInteger)s).setValue(setObj.get(s.getName()).getAsInt());
                                    break;
                                }
                                case DOUBLE: {
                                    ((SettingDouble)s).setValue(setObj.get(s.getName()).getAsDouble());
                                    break;
                                }
                                case BOOLEAN: {
                                    ((SettingBoolean)s).setValue(setObj.get(s.getName()).getAsBoolean());
                                    break;
                                }
                                case MODE: {
                                    ((SettingMode)s).setValue(setObj.get(s.getName()).getAsString());
                                    break;
                                }
                                case COLOR: {
                                    JsonArray array = setObj.get(s.getName()).getAsJsonArray();
                                    if (array.size() < 4) break;
                                    SettingColor cs = (SettingColor)s;
                                    cs.setAWTValue(new Color(array.get(0).getAsInt(), array.get(1).getAsInt(), array.get(2).getAsInt(), array.get(3).getAsInt()));
                                }
                            }
                        }
                    });
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (stream == null) continue;
                    if (throwable != null) {
                        try {
                            stream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    stream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        if (Files.notExists(Paths.get("revenge/other", new String[0]), new LinkOption[0])) {
            try {
                Files.createDirectories(Paths.get("revenge/other", new String[0]), new FileAttribute[0]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!FriendsManager.FRIENDS.isEmpty()) {
            String json = GSON.toJson((JsonElement)FriendsManager.serialize());
            try {
                Files.write(Paths.get("revenge/other/friends.json", new String[0]), json.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        for (Module.Category category : Module.Category.values()) {
            if (Files.notExists(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT), new String[0]), new LinkOption[0])) {
                try {
                    Files.createDirectories(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT), new String[0]), new FileAttribute[0]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Module module : Revenge.moduleManager.getModsInCategory(category)) {
                JsonObject obj = new JsonObject();
                obj.addProperty("bind", (Number)module.getKey());
                obj.addProperty("enabled", Boolean.valueOf(module.isToggled()));
                JsonObject setObj = new JsonObject();
                Revenge.settingsManager.getSettingsInMod(module).forEach(s -> {
                    switch (s.getType()) {
                        case INTEGER: {
                            setObj.addProperty(s.getName(), (Number)((SettingInteger)s).getValue());
                            break;
                        }
                        case DOUBLE: {
                            setObj.addProperty(s.getName(), (Number)((SettingDouble)s).getValue());
                            break;
                        }
                        case BOOLEAN: {
                            setObj.addProperty(s.getName(), Boolean.valueOf(((SettingBoolean)s).getValue()));
                            break;
                        }
                        case MODE: {
                            setObj.addProperty(s.getName(), ((SettingMode)s).getValue());
                            break;
                        }
                        case COLOR: {
                            JsonArray array = new JsonArray();
                            Color value = ((SettingColor)s).getAWTValue(false);
                            array.add((Number)value.getRed());
                            array.add((Number)value.getGreen());
                            array.add((Number)value.getBlue());
                            array.add((Number)value.getAlpha());
                            setObj.add(s.getName(), (JsonElement)array);
                        }
                    }
                });
                obj.add("settings", (JsonElement)setObj);
                String json = GSON.toJson((JsonElement)obj);
                try {
                    Files.write(Paths.get(CONFIG_FOLDER + category.name().toLowerCase(Locale.ROOT) + "/" + module.getName() + ".json", new String[0]), json.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


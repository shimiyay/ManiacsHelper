package com.maniacshelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("maniacshelper.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private int hudX = 10;
    private int hudY = 10;
    private boolean autoPartyChat = false;

    public ConfigManager() {
        load();
    }

    public int getHudX() {
        return hudX;
    }

    public int getHudY() {
        return hudY;
    }

    public void setHudPosition(int x, int y) {
        this.hudX = x;
        this.hudY = y;
        save();
    }

    public boolean isAutoPartyChat() {
        return autoPartyChat;
    }

    public void setAutoPartyChat(boolean autoPartyChat) {
        this.autoPartyChat = autoPartyChat;
        save();
    }

    private void load() {
        if (!Files.exists(CONFIG_FILE)) {
            save();
            return;
        }
        try {
            String json = Files.readString(CONFIG_FILE);
            ConfigData data = GSON.fromJson(json, ConfigData.class);
            if (data != null) {
                hudX = data.hudX;
                hudY = data.hudY;
                autoPartyChat = data.autoPartyChat;
            }
        } catch (IOException e) {
            System.err.println("[ManiacsHelper] Could not load config: " + e.getMessage());
        }
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_DIR);
            String json = GSON.toJson(new ConfigData(hudX, hudY, autoPartyChat));
            Files.writeString(CONFIG_FILE, json);
        } catch (IOException e) {
            System.err.println("[ManiacsHelper] Could not save config: " + e.getMessage());
        }
    }

    private static class ConfigData {
        int hudX;
        int hudY;
        boolean autoPartyChat;

        ConfigData(int hudX, int hudY, boolean autoPartyChat) {
            this.hudX = hudX;
            this.hudY = hudY;
            this.autoPartyChat = autoPartyChat;
        }
    }
}

package com.maniacshelper;

import net.fabricmc.api.ClientModInitializer;

public class ManiacsHelper implements ClientModInitializer {

    private static CarryManager carryManager;
    private static ConfigManager configManager;

    @Override
    public void onInitializeClient() {
        configManager = new ConfigManager();
        carryManager = new CarryManager(configManager);

        new ChatListener(carryManager).register();
        new Commands(carryManager, configManager).register();
        new HudOverlay(carryManager, configManager).register();
    }

    public static CarryManager getCarryManager() {
        return carryManager;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}

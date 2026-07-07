package com.maniacshelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class ManiacsHelper implements ClientModInitializer {

    public static boolean openConfigNextTick = false;

    private static CarryManager carryManager;
    private static ConfigManager configManager;

    @Override
    public void onInitializeClient() {
        configManager = new ConfigManager();
        carryManager = new CarryManager(configManager);

        new ChatListener(carryManager).register();
        new Commands(carryManager, configManager).register();
        new HudOverlay(carryManager, configManager).register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openConfigNextTick) {
                client.setScreen(new ConfigScreen(null, carryManager, configManager));
                openConfigNextTick = false;
            }
        });
    }

    public static CarryManager getCarryManager() {
        return carryManager;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}

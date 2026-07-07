package com.maniacshelper;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class HudOverlay {

    private final CarryManager carryManager;
    private final ConfigManager configManager;

    public HudOverlay(CarryManager carryManager, ConfigManager configManager) {
        this.carryManager = carryManager;
        this.configManager = configManager;
    }

    public void register() {
        HudElementRegistry.addLast(
            Identifier.fromNamespaceAndPath("maniacshelper", "hud"),
            this::render
        );
    }

    private void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (carryManager.getActiveCarries().isEmpty()) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) return;

        int x = configManager.getHudX();
        int y = configManager.getHudY();
        int lineHeight = 10;
        int index = 0;

        for (CarryManager.CarrySession session : carryManager.getActiveCarries().values()) {
            String numberColor = session.currentRuns >= session.maxRuns ? "§a" : "§7";
            String text = "§e" + session.playerName + " " + numberColor + session.currentRuns + "/" + session.maxRuns;
            guiGraphics.text(client.font, text, x, y + index * lineHeight, 0xFFFFFFFF, true);
            index++;
        }
    }
}

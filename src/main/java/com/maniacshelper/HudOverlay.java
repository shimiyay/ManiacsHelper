package com.maniacshelper;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudOverlay {

    private final CarryManager carryManager;
    private final ConfigManager configManager;

    public HudOverlay(CarryManager carryManager, ConfigManager configManager) {
        this.carryManager = carryManager;
        this.configManager = configManager;
    }

    public void register() {
        HudRenderCallback.EVENT.register(this::render);
    }

    private void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (carryManager.getActiveCarries().isEmpty()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        TextRenderer textRenderer = client.textRenderer;
        int x = configManager.getHudX();
        int y = configManager.getHudY();
        int lineHeight = 10;
        int index = 0;

        for (CarryManager.CarrySession session : carryManager.getActiveCarries().values()) {
            String numberColor = session.currentRuns >= session.maxRuns ? "§a" : "§7";
            String text = "§e" + session.playerName + " " + numberColor + session.currentRuns + "/" + session.maxRuns;
            drawContext.drawText(textRenderer, text, x, y + index * lineHeight, 0xFFFFFFFF, true);
            index++;
        }
    }
}

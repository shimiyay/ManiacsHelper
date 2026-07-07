package com.maniacshelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {

    private static final int PADDING = 2;

    private final Screen parent;
    private final CarryManager carryManager;
    private final ConfigManager configManager;

    private boolean isDragging;
    private double exactX;
    private double exactY;

    private int boxWidth;
    private int boxHeight;

    private List<String> displayLines;

    private int configX;
    private int configY;

    protected ConfigScreen(Screen parent, CarryManager carryManager, ConfigManager configManager) {
        super(Component.literal("Maniacs Helper Config"));
        this.parent = parent;
        this.carryManager = carryManager;
        this.configManager = configManager;
        this.configX = configManager.getHudX();
        this.configY = configManager.getHudY();
        buildDisplayLines();
    }

    private void buildDisplayLines() {
        displayLines = new ArrayList<>();

        if (carryManager.hasAnyActive()) {
            for (CarryManager.CarrySession s : carryManager.getActiveCarries().values()) {
                String numColor = s.currentRuns >= s.maxRuns ? "§a" : "§7";
                displayLines.add("§e" + s.playerName + " " + numColor + s.currentRuns + "/" + s.maxRuns);
            }
        } else {
            displayLines.add("§eExamplePlayer1 §71/5");
            displayLines.add("§eExamplePlayer2 §73/10");
            displayLines.add("§eExamplePlayer3 §70/3");
            displayLines.add("§eExamplePlayer4 §72/8");
        }

        boxWidth = 0;
        for (String line : displayLines) {
            int w = font.width(line);
            if (w > boxWidth) boxWidth = w;
        }
        boxHeight = displayLines.size() * 10;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.fill(0, 0, this.width, this.height, 0x33000000);

        guiGraphics.text(font, "Drag the box to reposition the HUD", 10, 10, 0xFFFFFF, true);
        guiGraphics.text(font, "Press ESC to save and exit", 10, 22, 0xAAAAAA, true);

        int bgX1 = configX - PADDING;
        int bgY1 = configY - PADDING;
        int bgX2 = configX + boxWidth + PADDING;
        int bgY2 = configY + boxHeight + PADDING;

        guiGraphics.fill(bgX1, bgY1, bgX2, bgY2, 0x50000000);

        int borderColor = isDragging ? 0xFFFFFFFF : 0xFF888888;
        guiGraphics.fill(bgX1, bgY1, bgX2, bgY1 + 1, borderColor);
        guiGraphics.fill(bgX1, bgY2 - 1, bgX2, bgY2, borderColor);
        guiGraphics.fill(bgX1, bgY1 + 1, bgX1 + 1, bgY2 - 1, borderColor);
        guiGraphics.fill(bgX2 - 1, bgY1 + 1, bgX2, bgY2 - 1, borderColor);

        int index = 0;
        for (String line : displayLines) {
            guiGraphics.text(font, line, configX, configY + index * 10, 0xFFFFFFFF, true);
            index++;
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (isInsideBox(event.x(), event.y())) {
            this.isDragging = true;
            this.exactX = this.configX;
            this.exactY = this.configY;
            return true;
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        if (this.isDragging) {
            this.exactX += deltaX;
            this.exactY += deltaY;

            this.configX = (int) Math.max(0, Math.min(Math.round(this.exactX), this.width - this.boxWidth));
            this.configY = (int) Math.max(0, Math.min(Math.round(this.exactY), this.height - this.boxHeight));
            return true;
        }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (this.isDragging) {
            this.isDragging = false;
            this.configManager.setHudPosition(this.configX, this.configY);
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public void onClose() {
        configManager.setHudPosition(configX, configY);
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    private boolean isInsideBox(double mouseX, double mouseY) {
        return mouseX >= configX && mouseX <= configX + boxWidth
            && mouseY >= configY && mouseY <= configY + boxHeight;
    }
}

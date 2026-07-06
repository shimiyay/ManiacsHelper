package com.maniacshelper;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener {

    private static final Pattern VICTORY_PATTERN = Pattern.compile("^\\s*Team Score: \\d+ \\(.*\\)$");
    private static final Pattern PARTY_MEMBER_PATTERN = Pattern.compile("(?:(?:\\[.*?\\]\\s+)?(\\w+)\\s*●)");

    private final CarryManager carryManager;

    public ChatListener(CarryManager carryManager) {
        this.carryManager = carryManager;
    }

    public void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (overlay) return;
            String raw = message.getString();
            handleMessage(raw);
        });
    }

    private void handleMessage(String raw) {
        if (VICTORY_PATTERN.matcher(raw).matches()) {
            if (carryManager.hasAnyActive()) {
                carryManager.incrementAllActive();
            }
            return;
        }

        if (raw.startsWith("Party Members (")) {
            carryManager.clearParty();
            return;
        }

        if (raw.startsWith("Party Leader:") || raw.startsWith("Party Moderators:") || raw.startsWith("Party Members:")) {
            Matcher matcher = PARTY_MEMBER_PATTERN.matcher(raw);
            while (matcher.find()) {
                carryManager.addPartyMember(matcher.group(1));
            }
        }
    }
}

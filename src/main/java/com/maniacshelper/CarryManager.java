package com.maniacshelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarryManager {

    private final HashMap<String, CarrySession> activeCarries = new HashMap<>();
    private final HashSet<String> currentPartyMembers = new HashSet<>();
    private final ConfigManager configManager;

    public CarryManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static class CarrySession {
        public String playerName;
        public int currentRuns;
        public int maxRuns;

        public CarrySession(String playerName, int currentRuns, int maxRuns) {
            this.playerName = playerName;
            this.currentRuns = currentRuns;
            this.maxRuns = maxRuns;
        }
    }

    public void addCarry(String player, int maxRuns) {
        CarrySession existing = activeCarries.get(player);
        if (existing != null) {
            existing.maxRuns += maxRuns;
        } else {
            activeCarries.put(player, new CarrySession(player, 0, maxRuns));
        }
    }

    public void removeCarry(String player) {
        activeCarries.remove(player);
    }

    public void removeRuns(String player, int amount) {
        CarrySession session = activeCarries.get(player);
        if (session != null) {
            session.currentRuns = Math.max(0, session.currentRuns - amount);
        }
    }

    public CarrySession getCarry(String player) {
        return activeCarries.get(player);
    }

    public Map<String, CarrySession> getActiveCarries() {
        return activeCarries;
    }

    public boolean hasActiveCarry(String player) {
        return activeCarries.containsKey(player);
    }

    public boolean hasAnyActive() {
        return !activeCarries.isEmpty();
    }

    public void incrementAllActive() {
        for (CarrySession session : activeCarries.values()) {
            session.currentRuns++;
            if (session.currentRuns >= session.maxRuns) {
                var client = Minecraft.getInstance();
                if (client.player != null) {
                    client.player.sendSystemMessage(
                        Component.literal("§a[§2ManiacsHelper§a] §f" + session.playerName + " §ahas completed their carry (" + session.currentRuns + "/" + session.maxRuns + ")")
                    );
                }
            }
        }

        if (configManager.isAutoPartyChat() && hasAnyActive()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ManiacsHelper] ");
            boolean first = true;
            for (CarrySession s : activeCarries.values()) {
                if (!first) sb.append(", ");
                sb.append(s.playerName).append(" ").append(s.currentRuns).append("/").append(s.maxRuns);
                first = false;
            }
            var client = Minecraft.getInstance();
            if (client.getConnection() != null) {
                client.getConnection().sendCommand("pc " + sb.toString());
            }
        }
    }

    public Set<String> getCurrentPartyMembers() {
        return currentPartyMembers;
    }

    public void setPartyMembers(Set<String> members) {
        currentPartyMembers.clear();
        currentPartyMembers.addAll(members);
    }

    public void addPartyMember(String name) {
        currentPartyMembers.add(name);
    }

    public void clearParty() {
        currentPartyMembers.clear();
    }
}

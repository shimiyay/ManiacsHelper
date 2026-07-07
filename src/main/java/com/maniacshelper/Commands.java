package com.maniacshelper;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class Commands {

    private final CarryManager carryManager;
    private final ConfigManager configManager;

    public Commands(CarryManager carryManager, ConfigManager configManager) {
        this.carryManager = carryManager;
        this.configManager = configManager;
    }

    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerCommands(dispatcher);
        });
    }

    private void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("mh")
            .then(literal("add")
                .then(argument("player", StringArgumentType.word())
                    .suggests(partyMemberSuggestions())
                    .then(argument("max_runs", IntegerArgumentType.integer(1))
                        .executes(ctx -> {
                            String inputPlayer = StringArgumentType.getString(ctx, "player");
                            int maxRuns = IntegerArgumentType.getInteger(ctx, "max_runs");

                            boolean inParty = false;
                            String resolvedPlayerName = inputPlayer;

                            for (String member : carryManager.getCurrentPartyMembers()) {
                                if (member.equalsIgnoreCase(inputPlayer)) {
                                    inParty = true;
                                    resolvedPlayerName = member;
                                    break;
                                }
                            }

                            if (!inParty) {
                                ctx.getSource().sendFeedback(Component.literal(
                                    "§c[§4ManiacsHelper§c] §f" + inputPlayer + " §cis not in the party! Run /p l to update the list."));
                                return 0;
                            }

                            carryManager.addCarry(resolvedPlayerName, maxRuns);
                            ctx.getSource().sendFeedback(Component.literal(
                                "§a[§2ManiacsHelper§a] §f" + resolvedPlayerName + " §aadded with a max of §e" + maxRuns + " §aruns."));
                            return 1;
                        })
                    )
                )
            )
            .then(literal("remove")
                .then(argument("player", StringArgumentType.word())
                    .suggests(activeCarrySuggestions())
                    .then(argument("amount", IntegerArgumentType.integer(1))
                        .executes(ctx -> {
                            String player = StringArgumentType.getString(ctx, "player");
                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                            if (!carryManager.hasActiveCarry(player)) {
                                ctx.getSource().sendFeedback(Component.literal(
                                    "§c[§4ManiacsHelper§c] §f" + player + " §cis not being tracked."));
                                return 0;
                            }
                            carryManager.removeRuns(player, amount);
                            var session = carryManager.getCarry(player);
                            ctx.getSource().sendFeedback(Component.literal(
                                "§a[§2ManiacsHelper§a] §f" + player + " §ais now at §e" + session.currentRuns + "/" + session.maxRuns + "."));
                            return 1;
                        })
                    )
                )
            )
            .then(literal("cancel")
                .then(argument("player", StringArgumentType.word())
                    .suggests(activeCarrySuggestions())
                    .executes(ctx -> {
                        String player = StringArgumentType.getString(ctx, "player");
                        if (!carryManager.hasActiveCarry(player)) {
                            ctx.getSource().sendFeedback(Component.literal(
                                "§c[§4ManiacsHelper§c] §f" + player + " §cis not being tracked."));
                            return 0;
                        }
                        carryManager.removeCarry(player);
                        ctx.getSource().sendFeedback(Component.literal(
                            "§a[§2ManiacsHelper§a] §f" + player + " §aremoved from tracker."));
                        return 1;
                    })
                )
            )
            .then(literal("config")
                .executes(ctx -> {
                    ManiacsHelper.openConfigNextTick = true;
                    return 1;
                })
                .then(literal("chat_message")
                    .then(literal("on")
                        .executes(ctx -> {
                            configManager.setAutoPartyChat(true);
                            ctx.getSource().sendFeedback(Component.literal(
                                "§a[§2ManiacsHelper§a] §aAuto party chat enabled."));
                            return 1;
                        })
                    )
                    .then(literal("off")
                        .executes(ctx -> {
                            configManager.setAutoPartyChat(false);
                            ctx.getSource().sendFeedback(Component.literal(
                                "§a[§2ManiacsHelper§a] §aAuto party chat disabled."));
                            return 1;
                        })
                    )
                )
            )
        );
    }

    private SuggestionProvider<FabricClientCommandSource> partyMemberSuggestions() {
        return (ctx, builder) -> {
            for (String member : carryManager.getCurrentPartyMembers()) {
                builder.suggest(member);
            }
            return builder.buildFuture();
        };
    }

    private SuggestionProvider<FabricClientCommandSource> activeCarrySuggestions() {
        return (ctx, builder) -> {
            for (String player : carryManager.getActiveCarries().keySet()) {
                builder.suggest(player);
            }
            return builder.buildFuture();
        };
    }
}

package com.github.tjeukayim.commandpermissionsfabric;

import com.mojang.brigadier.tree.CommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PermissionsMod implements ModInitializer {
    /**
     * Permission string prefix compatible with other modding frameworks.
     */
    public static final String PREFIX = "minecraft.command.";
    private static final Logger LOGGER = LogManager.getLogger();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            if ("true".equals(System.getenv("minecraft-command-permissions.test"))) {
                var allCommands = dispatcher.getRoot().getChildren()
                        .stream()
                        .map(c -> "\"" + c.getName() + "\",")
                        .sorted()
                        .collect(Collectors.joining("\n"));
                LOGGER.info("All commands:\n{}", allCommands);
            }
            for (CommandNode<ServerCommandSource> node : dispatcher.getRoot().getChildren()) {
                alterCommand(node);
            }
            LOGGER.info("Loaded Minecraft Command Permissions");
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            LuckPerms luckPerms = LuckPermsProvider.get();
            luckPerms.getEventBus().subscribe(NodeMutateEvent.class, e -> {
                if (e.isUser()) {
                    var user = (User) e.getTarget();
                    onPermissionsChange(server, user);
                } else if (e.isGroup()) {
                    var group = (Group) e.getTarget();
                    // Delay because user is not yet updated otherwise
                    Runnable updateGroup = () ->
                            server.send(new ServerTask(1, () -> luckPerms.getUserManager().getLoadedUsers()
                                    .stream()
                                    .filter(u -> u.getInheritedGroups(u.getQueryOptions()).contains(group))
                                    .forEach(u -> onPermissionsChange(server, u))));
                    executorService.schedule(updateGroup, 1, TimeUnit.SECONDS);
                }
            });
            LOGGER.info("Set-up autocompletion refresh for LuckPerms");
        });
    }

    private void alterCommand(CommandNode<ServerCommandSource> node) {
        var name = node.getName();
        Predicate<ServerCommandSource> original = node.getRequirement();
        if (!Objects.requireNonNull(commandPackageName(node)).startsWith("net.minecraft.")) {
            LOGGER.debug("Skip command {}", name);
            return;
        }
        LOGGER.debug("Alter command {}", name);
        try {
            var field = CommandNode.class.getDeclaredField("requirement");
            field.setAccessible(true);
            field.set(node, (Predicate<ServerCommandSource>) (source) ->
                    Permissions.check(source, PREFIX + name, original == null || original.test(source))
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.warn("Failed to alter field CommandNode.requirement" + name, e);
        }
    }

    private String commandPackageName(CommandNode<ServerCommandSource> node) {
        var command = node.getCommand();
        if (command != null) {
            return command.getClass().getPackageName();
        }
        var redirect = node.getRedirect();
        if (redirect != null) {
            return commandPackageName(redirect);
        }
        for (var child : node.getChildren()) {
            var childResult = commandPackageName(child);
            if (childResult != null) {
                return childResult;
            }
        }
        return null;
    }

    private void onPermissionsChange(MinecraftServer server, User user) {
        // TODO: Sometimes this event fires multiple times at once
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(user.getUniqueId());
        if (player != null) {
            server.getPlayerManager().sendCommandTree(player);
            LOGGER.debug(() -> "Updated command tree for " + player.getName());
        }
    }
}

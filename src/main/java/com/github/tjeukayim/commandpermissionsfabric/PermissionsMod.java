package com.github.tjeukayim.commandpermissionsfabric;

import com.mojang.brigadier.tree.CommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PermissionsMod implements ModInitializer {
    /**
     * Permission string prefix compatible with other modding frameworks.
     */
    public static final String PREFIX = "minecraft.command.";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        // LuckPerms only calls AbstractLuckPermsPlugin::enable on the SERVER_STARTING event
        // before that LuckPermsPlugin::getVerboseHandler() returns null. Initializing
        // earlier caused issued when loading datapacks with functions.
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            var dispatcher = server.getCommandManager().getDispatcher();
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
    }

    private void alterCommand(CommandNode<ServerCommandSource> child) {
        var name = child.getName();
        LOGGER.debug("Alter command {}", name);
        var packageName = commandPackageName(child);
        if (packageName == null || !packageName.startsWith("net.minecraft")) {
            LOGGER.debug("minecraft-command-permissions skipping command {} from {}", name, packageName);
            return;
        }
        try {
            var field = CommandNode.class.getDeclaredField("requirement");
            field.setAccessible(true);
            Predicate<ServerCommandSource> original = child.getRequirement();
            field.set(child, (Predicate<ServerCommandSource>) (source) ->
                    Permissions.check(source, PREFIX + name, original == null || original.test(source))
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.warn("Failed to alter field CommandNode.requirement " + name, e);
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
}

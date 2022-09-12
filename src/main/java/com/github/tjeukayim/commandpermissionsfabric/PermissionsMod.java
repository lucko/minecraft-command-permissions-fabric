package com.github.tjeukayim.commandpermissionsfabric;

import com.github.tjeukayim.commandpermissionsfabric.mixin.CommandNodeAccessor;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.build;

public class PermissionsMod implements DedicatedServerModInitializer {
    /**
     * Permission string prefix compatible with other modding frameworks.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeServer() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            if ("true".equals(System.getenv("minecraft-command-permissions.test"))) {
                var allCommands = dispatcher.getRoot().getChildren()
                        .stream()
                        .map(c -> "\"" + c.getName() + "\",")
                        .sorted()
                        .collect(Collectors.joining("\n"));
                LOGGER.info("All commands:\n{}", allCommands);
            }
            for (CommandNode<ServerCommandSource> node : dispatcher.getRoot().getChildren()) {
                alterCommandNode(dispatcher, node, true);
            }
            LOGGER.info("Loaded Minecraft Command Permissions");
        });
    }

    @SuppressWarnings("unchecked")
    private void alterCommandNode(CommandDispatcher<ServerCommandSource> dispatcher, CommandNode<ServerCommandSource> child, boolean root) {
        var name = build(dispatcher.getPath(child).toArray(new String[]{}));
        if (root) {
            var packageName = commandPackageName(child);
            if (packageName == null || !packageName.startsWith("net.minecraft")) {
                LOGGER.debug("minecraft-command-permissions skipping command {} from {}", name, packageName);
                return;
            }
        }
        LOGGER.debug("Alter command node {}", name);
        Predicate<ServerCommandSource> predicate = source -> Permissions.check(source, Constants.COMMAND.formatted(name));
        ((CommandNodeAccessor<ServerCommandSource>) child).setRequirement(root ? child.getRequirement().or(predicate) : predicate.or(src -> src.hasPermissionLevel(2)));
        for (CommandNode<ServerCommandSource> childChild : child.getChildren()) {
            alterCommandNode(dispatcher, childChild, false);
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

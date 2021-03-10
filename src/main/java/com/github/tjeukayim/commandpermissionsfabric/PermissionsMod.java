package com.github.tjeukayim.commandpermissionsfabric;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class PermissionsMod implements ModInitializer {
    /**
     * Permission string prefix compatible with other modding frameworks.
     */
    public static final String PREFIX = "minecraft.command.";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            for (String name : VANILLA_COMMANDS) {
                alterCommand(dispatcher, name);
            }
        });
    }

    private void alterCommand(CommandDispatcher<ServerCommandSource> dispatcher, String name) {
        CommandNode<ServerCommandSource> child = dispatcher.getRoot().getChild(name);
        try {
            Field field = CommandNode.class.getDeclaredField("requirement");
            field.setAccessible(true);
            Predicate<ServerCommandSource> original = child.getRequirement();
            field.set(child, (Predicate<ServerCommandSource>) (source) -> {
                LOGGER.info("Check permissions for {}", name);
                return Permissions.check(source, PREFIX + name, original.test(source));
            });
            LOGGER.info("Altered command " + name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.warn("Failed to alter field CommandNode.requirement" + name, e);
        }
    }

    /**
     * List of commands built-in to vanilla Minecraft that will get permission checks, in alphabetical order.
     * Aliases are treated the same as normal commands and have separate permissions, for example
     * "minecraft.command.tp" is a separate permission from "minecraft.command.teleport".
     */
    private static final String[] VANILLA_COMMANDS = {
            "advancement",
            "attribute",
            "ban",
            "ban-ip",
            "banlist",
            "bossbar",
            "clear",
            "clone",
            "data",
            "datapack",
            "debug",
            "defaultgamemode",
            "deop",
            "difficulty",
            "effect",
            "enchant",
            "execute",
            "experience", // <- xp
            "fill",
            "forceload",
            "function",
            "gamemode",
            "gamerule",
            "give",
            "help",
            "kick",
            "kill",
            "list",
            "locate",
            "locatebiome",
            "loot",
            "me",
            "msg", // <- tell, w
            "op",
            "pardon",
            "pardon-ip",
            "particle",
            "playsound",
            "recipe",
            "reload",
            "replaceitem",
            "save-all",
            "save-off",
            "save-on",
            "say",
            "schedule",
            "scoreboard",
            "seed",
            "setblock",
            "setidletimeout",
            "setworldspawn",
            "spawnpoint",
            "spectate",
            "spreadplayers",
            "stop",
            "stopsound",
            "summon",
            "tag",
            "team",
            "teammsg", // <- tm
            "teleport", // <- tp
            "tell", // -> msg
            "tellraw",
            "time",
            "title",
            "tm", // -> teammsg
            "tp", // -> teleport (Sponge uses tp, while Bukkit uses teleport)
            "trigger",
            "w", // w -> msg
            "weather",
            "whitelist",
            "worldborder",
            "xp", // -> experience
    };
}

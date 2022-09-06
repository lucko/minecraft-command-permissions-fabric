package com.github.tjeukayim.commandpermissionsfabric;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Constants {

    public static final CharSequence DELIMITER = ".";
    private static final String PREFIX = Identifier.DEFAULT_NAMESPACE;

    public static final String BYPASS_SPAWN_PROTECTION = permission("bypass", "spawn-protection");
    public static final String BYPASS_FORCE_GAMEMODE = permission("bypass", "force-gamemode");
    public static final String BYPASS_MOVE_SPEED_PLAYER = permission("bypass", "move-speed", "player");
    public static final String BYPASS_MOVE_SPEED_VEHICLE = permission("bypass", "move-speed", "vehicle", "%s", "%s");
    public static final String BYPASS_CHAT_SPEED = permission("bypass", "chat-speed");
    public static final String COMMAND = permission("command", "%s");
    public static final String DEBUG_STICK_USE = permission("%s", "use", "%s", "%s");
    public static final String NBT_QUERY_ENTITY = permission("nbt", "query", "entity");
    public static final String NBT_QUERY_BLOCK = permission("nbt", "query", "block");
    public static final String NBT_LOAD_ENTITY = permission("nbt", "load", "entity");
    public static final String NBT_LOAD_BLOCK = permission("nbt", "load", "block");
    public static final String OPERATOR_BLOCK_PLACE = permission("%s", "place");
    public static final String OPERATOR_BLOCK_VIEW = permission("%s", "view");
    public static final String OPERATOR_BLOCK_EDIT = permission("%s", "edit");
    public static final String OPERATOR_BLOCK_BREAK = permission("%s", "break");
    public static final String SELECTOR = permission("selector");

    // Not yet implemented
    public static final String BYPASS_WHITELIST = permission("bypass", "whitelist");
    public static final String BYPASS_PLAYER_LIMIT = permission("bypass", "player-limit");

    protected static String permission(String... parts) {
        return build(PREFIX, build(parts));
    }

    public static String build(String... parts) {
        return String.join(DELIMITER, parts);
    }

    public static String item(Item item) {
        return Registry.ITEM.getId(item).getPath();
    }

    public static String block(Block block) {
        return Registry.BLOCK.getId(block).getPath();
    }
}

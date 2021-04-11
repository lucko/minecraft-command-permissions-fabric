# Minecraft Command Permissions Fabric

Registers vanilla Minecraft commands in Fabric Permission API with structure `minecraft.command.<command>`,
like `minecraft.command.ban`.

Depends on [fabric-permissions-api-v0.1.jar](https://github.com/lucko/fabric-permissions-api/releases).

This mod is designed to be used with [LuckPerms](https://luckperms.net).
Then you can do things like:
`lp group admin permission set minecraft.command.ban true`.

Inspired by other modding frameworks:

- https://bukkit.fandom.com/wiki/CraftBukkit_Commands
- https://docs.spongepowered.org/stable/en/server/spongineer/commands.html

## Permissions

All these commands are supported: 

```
minecraft.command.advancement, minecraft.command.attribute, minecraft.command.ban, minecraft.command.ban-ip,
minecraft.command.banlist, minecraft.command.bossbar, minecraft.command.clear, minecraft.command.clone,
minecraft.command.data, minecraft.command.datapack, minecraft.command.debug, minecraft.command.defaultgamemode,
minecraft.command.deop, minecraft.command.difficulty, minecraft.command.effect, minecraft.command.enchant,
minecraft.command.execute, minecraft.command.experience, minecraft.command.fill, minecraft.command.forceload,
minecraft.command.function, minecraft.command.gamemode, minecraft.command.gamerule, minecraft.command.give,
minecraft.command.help, minecraft.command.kick, minecraft.command.kill, minecraft.command.list,
minecraft.command.locate, minecraft.command.locatebiome, minecraft.command.loot, minecraft.command.me,
minecraft.command.msg, minecraft.command.op, minecraft.command.pardon, minecraft.command.pardon-ip,
minecraft.command.particle, minecraft.command.playsound, minecraft.command.recipe, minecraft.command.reload,
minecraft.command.replaceitem, minecraft.command.save-all, minecraft.command.save-off, minecraft.command.save-on,
minecraft.command.say, minecraft.command.schedule, minecraft.command.scoreboard, minecraft.command.seed,
minecraft.command.setblock, minecraft.command.setidletimeout, minecraft.command.setworldspawn, minecraft.command.spawnpoint,
minecraft.command.spectate, minecraft.command.spreadplayers, minecraft.command.stop, minecraft.command.stopsound,
minecraft.command.summon, minecraft.command.tag, minecraft.command.team, minecraft.command.teammsg,
minecraft.command.teleport, minecraft.command.tell, minecraft.command.tellraw, minecraft.command.time,
minecraft.command.title, minecraft.command.tm, minecraft.command.tp, minecraft.command.trigger, minecraft.command.w,
minecraft.command.weather, minecraft.command.whitelist, minecraft.command.worldborder, minecraft.command.xp
```

## Limitations

1. Aliases have separate permissions, for example `minecraft.command.tp` and `minecraft.command.teleport`
2. These permissions are CraftBukkit specific, <ins>**not implemented**</ins>:
   - `minecraft.nbt.copy`
   - `minecraft.nbt.place`
   - `minecraft.autocraft`
   - `minecraft.debugstick`
   - `minecraft.debugstick.always`
4. These permissions are Sponge specific,  <ins>**not implemented**</ins>:
   - `minecraft.selector`
   - `minecraft.spawn-protection.override`
   - `minecraft.force-gamemode.override`
   - `minecraft.commandblock.edit.block.<name>`
   - `minecraft.commandblock.edit.minecart.<name>`
   - `minecraft.login.bypass-whitelist`
   - `minecraft.login.bypass-player-limit`

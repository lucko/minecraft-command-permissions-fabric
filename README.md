# Minecraft Command Permissions Fabric

Announcement: I'm looking for a new maintainer that wants to continue developing this mod, as I will archive this Git repository soon.

Registers vanilla Minecraft commands in Fabric Permission API with structure `minecraft.command.<command>`,
like `minecraft.command.ban`.

Depends on [fabric-permissions-api-v0.1.jar](https://github.com/lucko/fabric-permissions-api/releases)
(this is already bundled in the LuckPerms JAR).

This mod is designed to be used with [LuckPerms](https://luckperms.net).
Then you can do things like:
`lp group admin permission set minecraft.command.ban true`.
However, it should also work with other permission mods that respond to fabric-permissions-api events.

Inspired by other modding frameworks:

- https://bukkit.fandom.com/wiki/CraftBukkit_Commands
- https://docs.spongepowered.org/stable/en/server/spongineer/commands.html

## Supported permissions

These are all the permissions for Minecraft 1.16:

```txt
minecraft.command.advancement     minecraft.command.kick           minecraft.command.setworldspawn
minecraft.command.attribute       minecraft.command.kill           minecraft.command.spawnpoint
minecraft.command.ban             minecraft.command.list           minecraft.command.spectate
minecraft.command.ban-ip          minecraft.command.locate         minecraft.command.spreadplayers
minecraft.command.banlist         minecraft.command.locatebiome    minecraft.command.stop
minecraft.command.bossbar         minecraft.command.loot           minecraft.command.stopsound
minecraft.command.clear           minecraft.command.me             minecraft.command.summon
minecraft.command.clone           minecraft.command.msg            minecraft.command.tag
minecraft.command.data            minecraft.command.op             minecraft.command.team
minecraft.command.datapack        minecraft.command.pardon         minecraft.command.teammsg
minecraft.command.debug           minecraft.command.pardon-ip      minecraft.command.teleport
minecraft.command.defaultgamemode minecraft.command.particle       minecraft.command.tell
minecraft.command.deop            minecraft.command.playsound      minecraft.command.tellraw
minecraft.command.difficulty      minecraft.command.recipe         minecraft.command.time
minecraft.command.effect          minecraft.command.reload         minecraft.command.title
minecraft.command.enchant         minecraft.command.replaceitem    minecraft.command.tm
minecraft.command.execute         minecraft.command.save-all       minecraft.command.tp
minecraft.command.experience      minecraft.command.save-off       minecraft.command.trigger
minecraft.command.fill            minecraft.command.save-on        minecraft.command.w
minecraft.command.forceload       minecraft.command.say            minecraft.command.weather
minecraft.command.function        minecraft.command.schedule       minecraft.command.whitelist
minecraft.command.gamemode        minecraft.command.scoreboard     minecraft.command.worldborder
minecraft.command.gamerule        minecraft.command.seed           minecraft.command.xp
minecraft.command.give            minecraft.command.setblock
minecraft.command.help            minecraft.command.setidletimeout
```

And since Minecraft 1.17 also this one:

```txt
minecraft.command.item
```

With Minecraft 1.18 you'll get this one too:
```
minecraft.command.jfr
```

## Limitations

1. Aliases have separate permissions, for example `minecraft.command.tp` and `minecraft.command.teleport` can have different settings
2. These permissions are CraftBukkit specific, <ins>**not implemented**</ins>:
   - `minecraft.nbt.copy`
   - `minecraft.nbt.place`
   - `minecraft.autocraft`
   - `minecraft.debugstick`
   - `minecraft.debugstick.always`
3. These permissions are Sponge specific,  <ins>**not implemented**</ins>:
   - `minecraft.selector`
   - `minecraft.spawn-protection.override`
   - `minecraft.force-gamemode.override`
   - `minecraft.commandblock.edit.block.<name>`
   - `minecraft.commandblock.edit.minecart.<name>`
   - `minecraft.login.bypass-whitelist`
   - `minecraft.login.bypass-player-limit`

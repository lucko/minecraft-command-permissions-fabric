> [!IMPORTANT]  
> This mod is no longer being maintained. I recommmend that you instead use the [VanillaPermissions](https://github.com/DrexHD/VanillaPermissions) mod, which has equivalent functionality and is being updated for new Minecraft versions.
> * https://modrinth.com/mod/vanilla-permissions
> * https://github.com/DrexHD/VanillaPermissions
>
> Many thanks to the original author of minecraft-command-permissions-fabric, @TjeuKayim. Sorry I didn't do a very good job of keeping it up to date!


# Minecraft Command Permissions Fabric

A mod to add permission checks for built-in Minecraft commands.

* Download from the [releases](https://github.com/lucko/minecraft-command-permissions-fabric/releases) tab.
* You also need to have a permissions mod installed. (e.g. [LuckPerms](https://luckperms.net))

## Details

Permissions for built-in Minecraft commands are added in the format `minecraft.command.<command>`.   
(e.g. `minecraft.command.ban` for the /ban command)

The permission checks are performed using [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api), which acts as an interface between permission *checking* mods (like this one) and permission *provider* mods.

If you're using LuckPerms, you can grant these permissions using the permission set command.   
e.g. `/lp group admin permission set minecraft.command.ban true`

Any permission provider mod that supports fabric-permissions-api is also supported.

Originally created by Tjeu Kayim ([@TjeuKayim](https://github.com/TjeuKayim)), and inspired by other modding frameworks:

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

Minecraft 1.17 adds the following permissions:
```txt
minecraft.command.item
```

Minecraft 1.18 adds the following permissions:
```txt
minecraft.command.jfr
```

Minecraft 1.19 adds the following permissions:
```txt
minecraft.command.place
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

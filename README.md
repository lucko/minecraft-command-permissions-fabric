# Vanilla Permissions Fabric

Registers vanilla Minecraft commands in Fabric Permission API with structure `minecraft.command.&lt;command>`,
like `minecraft.command.ban`.

Depends on [fabric-permissions-api-v0.1.jar](https://github.com/lucko/fabric-permissions-api/releases).

This mod is designed to be used with [LuckPerms](https://luckperms.net).
Then you can do things like:
`lp group admin permission set minecraft.command.ban true`.

Inspired by other modding frameworks:

- https://bukkit.fandom.com/wiki/CraftBukkit_Commands
- https://docs.spongepowered.org/stable/en/server/spongineer/commands.html

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

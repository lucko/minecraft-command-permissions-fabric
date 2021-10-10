package com.github.tjeukayim.commandpermissionsfabric.mixin;

import com.github.tjeukayim.commandpermissionsfabric.PermissionsMod;
import com.mojang.authlib.GameProfile;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Inspired by https://github.com/SpongePowered/Sponge/blob/2a1b67eb6b3df09f582a50fddf5b6f7bcda5ad6c/src/mixins/java/org/spongepowered/common/mixin/core/server/dedicated/DedicatedPlayerListMixin.java
 */
@Mixin(DedicatedPlayerManager.class)
public abstract class DedicatedPlayerManagerMixin {
    @Shadow
    public abstract MinecraftDedicatedServer getServer();

    @Inject(method = "canBypassPlayerLimit", at = @At("HEAD"), cancellable = true)
    private void minecraftCommandPermissions$bypassPlayerLimitPermission(final GameProfile profile, final CallbackInfoReturnable<Boolean> ci) {
        // At this point in the login phase, the ServerPlayerEntity is not yet created
        // So, constructing a new ServerCommandSource is necessary
        var level = getServer().getPermissionLevel(profile);
        var source = new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ZERO, Vec2f.ZERO, null, level, "", LiteralText.EMPTY, getServer(), null);
        if (Permissions.check(source, PermissionsMod.BYPASS_PLAYER_LIMIT, false)) {
            ci.setReturnValue(true);
        }
    }
}

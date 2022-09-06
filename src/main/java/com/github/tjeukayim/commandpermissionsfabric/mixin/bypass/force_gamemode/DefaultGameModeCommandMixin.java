package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.force_gamemode;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.DefaultGameModeCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultGameModeCommand.class)
public abstract class DefaultGameModeCommandMixin {

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;changeGameMode(Lnet/minecraft/world/GameMode;)Z"
            )
    )
    private static boolean addDefaultGameModeOverridePermission(ServerPlayerEntity playerEntity, GameMode gameMode) {
        if (Permissions.check(playerEntity, Constants.BYPASS_FORCE_GAMEMODE)) {
            return false;
        }
        return playerEntity.changeGameMode(gameMode);
    }

}

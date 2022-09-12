package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.force_gamemode;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.DefaultGameModeCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DefaultGameModeCommand.class)
public abstract class DefaultGameModeCommandMixin {

    @WrapOperation(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;changeGameMode(Lnet/minecraft/world/GameMode;)Z"
            )
    )
    private static boolean mcpf_addDefaultGameModeOverridePermission(ServerPlayerEntity player, GameMode gameMode, Operation<Boolean> original) {
        if (Permissions.check(player, Constants.BYPASS_FORCE_GAMEMODE)) {
            return false;
        }
        return original.call(player, gameMode);
    }

}

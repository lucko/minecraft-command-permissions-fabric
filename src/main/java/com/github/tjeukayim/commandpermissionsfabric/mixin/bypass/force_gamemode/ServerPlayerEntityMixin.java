package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.force_gamemode;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @WrapOperation(
            method = "getServerGameMode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getForcedGameMode()Lnet/minecraft/world/GameMode;"
            )
    )
    public GameMode mcpf_addDefaultGameModeOverridePermission(MinecraftServer minecraftServer, Operation<GameMode> original) {
        if (Permissions.check((ServerPlayerEntity) (Object) this, Constants.BYPASS_FORCE_GAMEMODE)) {
            return null;
        } else {
            return original.call(minecraftServer);
        }
    }

}

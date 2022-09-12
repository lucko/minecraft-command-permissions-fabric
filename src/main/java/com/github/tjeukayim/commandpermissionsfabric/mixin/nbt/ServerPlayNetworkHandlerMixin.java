package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @ModifyExpressionValue(
            method = "onQueryEntityNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasPermissionLevel(I)Z"
            )
    )
    public boolean mcpf_addNbtQueryEntityPermission(boolean original) {
        return Permissions.check(this.player, Constants.NBT_QUERY_ENTITY) || original;
    }

    @ModifyExpressionValue(
            method = "onQueryBlockNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasPermissionLevel(I)Z"
            )
    )
    public boolean mcpf_addNbtQueryBlockPermission(boolean original) {
        return Permissions.check(this.player, Constants.NBT_QUERY_BLOCK) || original;
    }

}

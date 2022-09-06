package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Redirect(
            method = "onQueryEntityNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasPermissionLevel(I)Z"
            )
    )
    public boolean addNbtQueryEntityPermission(ServerPlayerEntity playerEntity, int level) {
        return Permissions.check(playerEntity, Constants.NBT_QUERY_ENTITY, level);
    }

    @Redirect(
            method = "onQueryBlockNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasPermissionLevel(I)Z"
            )
    )
    public boolean addNbtQueryBlockPermission(ServerPlayerEntity playerEntity, int level) {
        return Permissions.check(playerEntity, Constants.NBT_QUERY_BLOCK, level);
    }

}

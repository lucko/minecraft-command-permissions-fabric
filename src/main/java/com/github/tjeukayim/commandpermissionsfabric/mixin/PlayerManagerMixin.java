package com.github.tjeukayim.commandpermissionsfabric.mixin;

import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @ModifyArg(
            method = "sendCommandTree(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;sendCommandTree(Lnet/minecraft/server/network/ServerPlayerEntity;I)V"
            ),
            index = 1
    )
    public int sendOpLevelTwoOrHigher(int permissionLevel) {
        return Math.max(2, permissionLevel);
    }

}

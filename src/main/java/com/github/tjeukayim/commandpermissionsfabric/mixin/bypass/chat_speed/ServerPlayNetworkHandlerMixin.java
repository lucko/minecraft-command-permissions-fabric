package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.chat_speed;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.mojang.authlib.GameProfile;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Redirect(
            method = "checkForSpam",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    public boolean addBypassChatSpeedPermission(PlayerManager playerManager, GameProfile profile) {
        return Permissions.check(this.player, Constants.BYPASS_CHAT_SPEED) || playerManager.isOperator(profile);
    }
}

package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.spawn_protection;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.mojang.authlib.GameProfile;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MinecraftDedicatedServerMixin {

    @Redirect(
            method = "isSpawnProtected",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/dedicated/DedicatedPlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    public boolean addSpawnProtectionPermission(DedicatedPlayerManager playerManager, GameProfile gameProfile, ServerWorld world, BlockPos pos, PlayerEntity player) {
        return Permissions.check(player, Constants.BYPASS_SPAWN_PROTECTION) || playerManager.isOperator(gameProfile);
    }

}

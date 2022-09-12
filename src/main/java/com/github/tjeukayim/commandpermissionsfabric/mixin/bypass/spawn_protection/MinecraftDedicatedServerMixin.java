package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.spawn_protection;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MinecraftDedicatedServerMixin {

    @ModifyExpressionValue(
            method = "isSpawnProtected",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/dedicated/DedicatedPlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    public boolean mcpf_addSpawnProtectionPermission(boolean original, ServerWorld world, BlockPos pos, PlayerEntity player) {
        return Permissions.check(player, Constants.BYPASS_SPAWN_PROTECTION) || original;
    }

}

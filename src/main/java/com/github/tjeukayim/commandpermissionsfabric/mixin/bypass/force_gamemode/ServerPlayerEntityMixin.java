package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.force_gamemode;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.mojang.authlib.GameProfile;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Redirect(
            method = "getServerGameMode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getForcedGameMode()Lnet/minecraft/world/GameMode;"
            )
    )
    public GameMode addDefaultGameModeOverridePermission(MinecraftServer minecraftServer) {
        if (Permissions.check(this, Constants.BYPASS_FORCE_GAMEMODE)) {
            return null;
        } else {
            return minecraftServer.getForcedGameMode();
        }
    }

}

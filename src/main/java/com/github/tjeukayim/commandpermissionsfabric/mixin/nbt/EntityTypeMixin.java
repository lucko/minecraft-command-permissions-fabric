package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.mojang.authlib.GameProfile;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {

    @Redirect(
            method = "loadFromEntityNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    private static boolean addNbtLoadEntityPermission(PlayerManager playerManager, GameProfile profile, World world, PlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.NBT_LOAD_ENTITY) || playerManager.isOperator(profile);
    }
}

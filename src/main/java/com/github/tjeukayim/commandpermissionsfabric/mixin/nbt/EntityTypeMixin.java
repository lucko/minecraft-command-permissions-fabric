package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {

    @ModifyExpressionValue(
            method = "loadFromEntityNbt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    private static boolean mcpf_addNbtLoadEntityPermission(boolean original, World world, PlayerEntity player) {
        return Permissions.check(player, Constants.NBT_LOAD_ENTITY) || original;
    }
}

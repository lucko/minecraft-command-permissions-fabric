package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @ModifyExpressionValue(
            method = "writeNbtToBlockEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    private static boolean mcpf_addNbtLoadBlockPermission(boolean original, World world, PlayerEntity player) {
        return Permissions.check(player, Constants.NBT_LOAD_BLOCK) || original;
    }

}

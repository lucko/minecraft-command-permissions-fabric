package com.github.tjeukayim.commandpermissionsfabric.mixin.nbt;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Redirect(
            method = "writeNbtToBlockEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    private static boolean addNbtLoadBlockPermission(PlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.NBT_LOAD_BLOCK) || playerEntity.isCreativeLevelTwoOp();
    }

}

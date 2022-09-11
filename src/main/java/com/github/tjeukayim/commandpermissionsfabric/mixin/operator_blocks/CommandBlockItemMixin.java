package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CommandBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(CommandBlockItem.class)
public abstract class CommandBlockItemMixin extends BlockItem {

    public CommandBlockItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @Redirect(
            method = "getPlacementState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addCommandBlockPlacePermission(PlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_PLACE.formatted(block(getBlock()))) || playerEntity.isCreativeLevelTwoOp();
    }

}

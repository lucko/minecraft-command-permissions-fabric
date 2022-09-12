package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CommandBlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(CommandBlockItem.class)
public abstract class CommandBlockItemMixin extends BlockItem {

    public CommandBlockItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @ModifyExpressionValue(
            method = "getPlacementState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addCommandBlockPlacePermission(boolean original, ItemPlacementContext context) {
        assert context.getPlayer() != null;
        return Permissions.check(context.getPlayer(), Constants.OPERATOR_BLOCK_PLACE.formatted(block(getBlock()))) || original;
    }

}

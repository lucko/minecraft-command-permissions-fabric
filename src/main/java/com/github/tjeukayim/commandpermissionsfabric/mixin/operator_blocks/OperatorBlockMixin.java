package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(value = {CommandBlock.class, JigsawBlock.class})
public abstract class OperatorBlockMixin extends Block {

    public OperatorBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addOperatorBlockEditPermission(boolean original, BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return Permissions.check(player, Constants.OPERATOR_BLOCK_VIEW.formatted(block(this))) || original;
    }

}

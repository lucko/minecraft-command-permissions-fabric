package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Block;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(value = {CommandBlock.class, JigsawBlock.class})
public abstract class OperatorBlockMixin extends Block {

    public OperatorBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addOperatorBlockEditPermission(PlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_VIEW.formatted(block(this))) || playerEntity.isCreativeLevelTwoOp();
    }

}

package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(StructureBlockBlockEntity.class)
public abstract class StructureBlockBlockEntityMixin {

    @ModifyExpressionValue(
            method = "openScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addCommandBlockEditPermission(boolean original, PlayerEntity player) {
        return Permissions.check(player, Constants.OPERATOR_BLOCK_VIEW.formatted(block(Blocks.STRUCTURE_BLOCK))) || original;
    }

}

package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(StructureBlockBlockEntity.class)
public abstract class StructureBlockBlockEntityMixin {

    @Redirect(
            method = "openScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addCommandBlockEditPermission(PlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_VIEW.formatted(block(Blocks.STRUCTURE_BLOCK))) || playerEntity.isCreativeLevelTwoOp();
    }

}

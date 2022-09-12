package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.CommandBlockExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.item;

@Mixin(CommandBlockExecutor.class)
public abstract class CommandBlockExecutorMixin {

    @ModifyExpressionValue(
            method = "interact",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addCommandBlockMinecartOpenPermission(boolean original, PlayerEntity player) {
        return Permissions.check(player, Constants.OPERATOR_BLOCK_VIEW.formatted(item(Items.COMMAND_BLOCK_MINECART))) || original;
    }

}

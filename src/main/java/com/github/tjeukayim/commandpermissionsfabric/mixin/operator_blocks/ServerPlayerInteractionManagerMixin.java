package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerWorld world;

    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @ModifyExpressionValue(
            method = "tryBreakBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addOperatorBlockBreakPermission(boolean original, BlockPos pos) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_BREAK.formatted(block(this.world.getBlockState(pos).getBlock()))) || original;
    }
}

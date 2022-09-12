package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;
import static com.github.tjeukayim.commandpermissionsfabric.Constants.item;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @ModifyExpressionValue(
            method = "onUpdateCommandBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addCommandBlockEditPermission(boolean original) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.COMMAND_BLOCK))) || original;
    }

    @ModifyExpressionValue(
            method = "onUpdateJigsaw",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addJigsawBlockEditPermission(boolean original) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.JIGSAW))) || original;
    }

    @ModifyExpressionValue(
            method = "onJigsawGenerating",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addJigsawBlockEditPermission2(boolean original) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.JIGSAW))) || original;
    }

    @ModifyExpressionValue(
            method = "onUpdateStructureBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addStructureBlockEditPermission(boolean original) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.STRUCTURE_BLOCK))) || original;
    }

    @ModifyExpressionValue(
            method = "onUpdateCommandBlockMinecart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addCommandBlockMinecartEditPermission(boolean original) {
        return Permissions.check(this.player, Constants.OPERATOR_BLOCK_EDIT.formatted(item(Items.COMMAND_BLOCK_MINECART))) || original;
    }

}

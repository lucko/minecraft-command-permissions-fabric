package com.github.tjeukayim.commandpermissionsfabric.mixin.operator_blocks;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.block;
import static com.github.tjeukayim.commandpermissionsfabric.Constants.item;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Redirect(
            method = "onUpdateCommandBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addCommandBlockEditPermission(ServerPlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.COMMAND_BLOCK))) || playerEntity.isCreativeLevelTwoOp();
    }

    @Redirect(
            method = "onUpdateJigsaw",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addJigsawBlockEditPermission(ServerPlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.JIGSAW))) || playerEntity.isCreativeLevelTwoOp();
    }

    @Redirect(
            method = "onJigsawGenerating",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addJigsawBlockEditPermission2(ServerPlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.JIGSAW))) || playerEntity.isCreativeLevelTwoOp();
    }

    @Redirect(
            method = "onUpdateStructureBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addStructureBlockEditPermission(ServerPlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_EDIT.formatted(block(Blocks.STRUCTURE_BLOCK))) || playerEntity.isCreativeLevelTwoOp();
    }

    @Redirect(
            method = "onUpdateCommandBlockMinecart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean addCommandBlockMinecartEditPermission(ServerPlayerEntity playerEntity) {
        return Permissions.check(playerEntity, Constants.OPERATOR_BLOCK_EDIT.formatted(item(Items.COMMAND_BLOCK_MINECART))) || playerEntity.isCreativeLevelTwoOp();
    }

}

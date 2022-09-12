package com.github.tjeukayim.commandpermissionsfabric.mixin.debugstick;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DebugStickItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.tjeukayim.commandpermissionsfabric.Constants.item;

@Mixin(DebugStickItem.class)
public abstract class DebugStickItemMixin {

    @ModifyExpressionValue(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isCreativeLevelTwoOp()Z"
            )
    )
    public boolean mcpf_addDebugStickUsePermission(boolean original, PlayerEntity player, BlockState state) {
        Identifier identifier = Registry.BLOCK.getId(state.getBlock());
        return Permissions.check(player, Constants.DEBUG_STICK_USE.formatted(item(Items.DEBUG_STICK), identifier.getNamespace(), identifier.getPath())) || original;
    }

}

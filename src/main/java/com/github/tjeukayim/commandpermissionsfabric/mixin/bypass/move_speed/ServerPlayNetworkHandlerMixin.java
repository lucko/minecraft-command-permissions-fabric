package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.move_speed;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @ModifyExpressionValue(
            method = "onPlayerMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    public boolean mcpf_addBypassMoveSpeedPlayerPermission(boolean original) {
        return Permissions.check(this.player, Constants.BYPASS_MOVE_SPEED_PLAYER) || original;
    }

    @ModifyExpressionValue(
            method = "onVehicleMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    public boolean mcpf_addBypassMoveSpeedVehiclePermission(boolean original) {
        Identifier identifier = Registry.ENTITY_TYPE.getId(this.player.getRootVehicle().getType());
        return Permissions.check(this.player, Constants.BYPASS_MOVE_SPEED_VEHICLE.formatted(identifier.getNamespace(), identifier.getPath())) || original;
    }
}

package com.github.tjeukayim.commandpermissionsfabric.mixin.bypass.move_speed;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    protected abstract boolean isHost();

    @Redirect(
            method = "onPlayerMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    public boolean addBypassMoveSpeedPlayerPermission(ServerPlayNetworkHandler networkHandler) {
        return Permissions.check(networkHandler.player, Constants.BYPASS_MOVE_SPEED_PLAYER) || this.isHost();
    }

    @Redirect(
            method = "onVehicleMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;isHost()Z"
            )
    )
    public boolean addBypassMoveSpeedVehiclePermission(ServerPlayNetworkHandler networkHandler) {
        Identifier identifier = Registry.ENTITY_TYPE.getId(networkHandler.player.getRootVehicle().getType());
        return Permissions.check(networkHandler.player, Constants.BYPASS_MOVE_SPEED_VEHICLE.formatted(identifier.getNamespace(), identifier.getPath())) || this.isHost();
    }
}

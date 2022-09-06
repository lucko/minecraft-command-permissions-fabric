package com.github.tjeukayim.commandpermissionsfabric.mixin.selector;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntitySelector.class)
public abstract class EntitySelectorMixin {

    @Redirect(
            method = "checkSourcePermission",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/command/ServerCommandSource;hasPermissionLevel(I)Z"
            )
    )
    public boolean addSelectorPermission(ServerCommandSource source, int level) {
        return Permissions.check(source, Constants.SELECTOR, level);
    }

}

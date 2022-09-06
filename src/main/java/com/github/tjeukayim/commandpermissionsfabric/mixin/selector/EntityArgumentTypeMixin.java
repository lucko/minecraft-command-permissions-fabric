package com.github.tjeukayim.commandpermissionsfabric.mixin.selector;

import com.github.tjeukayim.commandpermissionsfabric.Constants;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArgumentType.class)
public abstract class EntityArgumentTypeMixin {

    @Redirect(
            method = "listSuggestions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/command/CommandSource;hasPermissionLevel(I)Z"
            )
    )
    public boolean addSelectorPermission(CommandSource source, int level) {
        return Permissions.check(source, Constants.SELECTOR, level);
    }

}

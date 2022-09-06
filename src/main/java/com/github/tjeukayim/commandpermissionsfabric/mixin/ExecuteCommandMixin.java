package com.github.tjeukayim.commandpermissionsfabric.mixin;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ExecuteCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExecuteCommand.class)
public abstract class ExecuteCommandMixin {

    @Shadow
    private static ArgumentBuilder<ServerCommandSource, ?> addConditionLogic(CommandNode<ServerCommandSource> root, ArgumentBuilder<ServerCommandSource, ?> builder, boolean positive, ExecuteCommand.Condition condition) {
        return null;
    }

    @Inject(method = "addConditionArguments", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;then(Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;", ordinal = 0))
    private static void addPermissionConditionArgument(CommandNode<ServerCommandSource> root, LiteralArgumentBuilder<ServerCommandSource> argumentBuilder, boolean positive, CommandRegistryAccess commandRegistryAccess, CallbackInfoReturnable<ArgumentBuilder<ServerCommandSource, ?>> cir) {
        argumentBuilder.then(
                CommandManager.literal("permission")
                        .then(
                                CommandManager.argument("entity", EntityArgumentType.entity())
                                        .then(
                                                addConditionLogic(
                                                        root,
                                                        CommandManager.argument("permission", StringArgumentType.word()),
                                                        positive,
                                                        context -> Permissions.check(EntityArgumentType.getEntity(context, "entity"), StringArgumentType.getString(context, "permission"))
                                                )
                                        )
                        )
        );
    }

}

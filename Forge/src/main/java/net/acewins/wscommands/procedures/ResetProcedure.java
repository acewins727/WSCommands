package net.acewins.wscommands.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class ResetProcedure {
    public static void execute(Entity entity) {
        if (entity == null || entity.getServer() == null) return;

        executeCommand(entity, "advancement revoke @s only wscommands:new_world");

        if (entity instanceof Player player && !player.level().isClientSide()) {
            player.displayClientMessage(Component.translatable("message.wscommands.reset"), false);
        }
    }

    private static void executeCommand(Entity entity, String command) {
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(
                    new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(),
                            entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 4,
                            entity.getName().getString(), entity.getDisplayName(), entity.level().getServer(), entity), command);
        }
    }
}

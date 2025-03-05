
package net.acewins.wscommands.command;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.acewins.wscommands.procedures.ResetProcedure;
import net.acewins.wscommands.procedures.ForceRunProcedure;

@Mod.EventBusSubscriber
public class WSCCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("wsc").requires(s -> s.hasPermission(4))
                        .then(Commands.argument("name", EntityArgument.players())
                                .then(Commands.literal("reset").executes(arguments -> executeReset(arguments.getSource())))
                                .then(Commands.literal("forcerun").executes(arguments -> executeForceRun(arguments.getSource()))))
        );
    }

    private static int executeReset(CommandSourceStack source) {
        Entity entity = getCommandEntity(source);
        if (entity != null) ResetProcedure.execute(entity);
        return 0;
    }

    private static int executeForceRun(CommandSourceStack source) {
        Entity entity = getCommandEntity(source);
        if (entity != null) ForceRunProcedure.execute(entity);
        return 0;
    }

    private static Entity getCommandEntity(CommandSourceStack source) {
        Entity entity = source.getEntity();
        Level world = source.getLevel();
        if (entity == null && world instanceof ServerLevel serverLevel) {
            entity = FakePlayerFactory.getMinecraft(serverLevel);
        }
        return entity;
    }
}
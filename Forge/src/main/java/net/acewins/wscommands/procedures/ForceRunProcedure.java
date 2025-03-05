package net.acewins.wscommands.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ForceRunProcedure {
    public static void execute(Entity entity) {
        if (entity == null || entity.getServer() == null) return;

        Path configPath = FMLPaths.GAMEDIR.get().resolve("config/WSCommands.json");
        JsonObject commands = new JsonObject();

        try {
            String jsonContent = Files.readString(configPath);
            commands = JsonParser.parseString(jsonContent).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        double commandIndex = 1;
        int totalCommands = commands.size() - 2;

        for (int i = 0; i < totalCommands; i++) {
            String commandKey = String.format("%.2f", commandIndex);
            if (commands.has(commandKey)) {
                executeCommand(entity, commands.get(commandKey).getAsString());
            }
            commandIndex++;
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

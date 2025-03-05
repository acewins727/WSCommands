package net.acewins.wscommands.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModLoadProcedure {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FMLPaths.GAMEDIR.get().resolve("config/WSCommands.json");

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        execute();
    }

    public static void execute() {
        if (Files.notExists(CONFIG_PATH)) {
            try {
                Files.createDirectories(CONFIG_PATH.getParent());
                JsonObject commands = new JsonObject();
                commands.addProperty("Repeat", false);
                commands.addProperty("DedicatedMode", false);
                commands.addProperty("1", "say WorldStartCommands is not configured yet");
                commands.addProperty("2", "say go to your config folder and find WSCommands.json to configure it");
                commands.addProperty("3", "wsc @s reset");

                Files.writeString(CONFIG_PATH, GSON.toJson(commands));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

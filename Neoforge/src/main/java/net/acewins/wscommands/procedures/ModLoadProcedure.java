package net.acewins.wscommands.procedures;

import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModLoadProcedure {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		execute();
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		File Config = new File("");
		com.google.gson.JsonObject commands = new com.google.gson.JsonObject();
		Config = new File((FMLPaths.GAMEDIR.get().toString() + "" + File.separator + "config"), File.separator + "WSCommands.json");
		if (!Config.exists()) {
			try {
				Config.getParentFile().mkdirs();
				Config.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			commands.addProperty("Repeat", false);
			commands.addProperty("DedicatedMode", false);
			commands.addProperty("1", "say WorldStartCommands is not configured yet");
			commands.addProperty("2", "say go to your config folder and find WSCommands.json to configure it");
			commands.addProperty("3", "wsc @s reset");
			{
				com.google.gson.Gson mainGSONBuilderVariable = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter fileWriter = new FileWriter(Config);
					fileWriter.write(mainGSONBuilderVariable.toJson(commands));
					fileWriter.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}

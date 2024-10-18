package net.acewins.wscommands.procedures;

import net.neoforged.fml.loading.FMLPaths;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

public class ForceRunProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		File Config = new File("");
		com.google.gson.JsonObject commands = new com.google.gson.JsonObject();
		double X = 0;
		Config = new File((FMLPaths.GAMEDIR.get().toString() + "\\config"), File.separator + "WSCommands.json");
		X = 1;
		{
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(Config));
				StringBuilder jsonstringbuilder = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					jsonstringbuilder.append(line);
				}
				bufferedReader.close();
				commands = new com.google.gson.Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
				for (int index0 = 0; index0 < (int) commands.size(); index0++) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), commands.get((new java.text.DecimalFormat("##.##").format(X))).getAsString());
						}
					}
					X = X + 1;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

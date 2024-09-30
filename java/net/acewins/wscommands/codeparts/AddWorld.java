package net.acewins.wscommands.codeparts;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

@Mod.EventBusSubscriber
public class AddWorld {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
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
				if (commands.get("DedicatedMode").getAsBoolean()) {
					if (!(entity instanceof ServerPlayer _plr3 && _plr3.level() instanceof ServerLevel
							&& _plr3.getAdvancements().getOrStartProgress(_plr3.server.getAdvancements().getAdvancement(new ResourceLocation("wscommands:new_world"))).isDone())) {
						if (entity instanceof ServerPlayer _player) {
							Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("wscommands:new_world"));
							AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
							if (!_ap.isDone()) {
								for (String criteria : _ap.getRemainingCriteria())
									_player.getAdvancements().award(_adv, criteria);
							}
						}
						for (int index0 = 0; index0 < (int) (commands.size() - 1); index0++) {
							{
								Entity _ent = entity;
								if (!_ent.level().isClientSide() && _ent.getServer() != null) {
									_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null,
											4, _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), commands.get((new java.text.DecimalFormat("##.##").format(X))).getAsString());
								}
							}
							X = X + 1;
						}
					}
				} else {
					if (world.players().size() == 1) {
						if (!(entity instanceof ServerPlayer _plr9 && _plr9.level() instanceof ServerLevel
								&& _plr9.getAdvancements().getOrStartProgress(_plr9.server.getAdvancements().getAdvancement(new ResourceLocation("wscommands:new_world"))).isDone())) {
							if (entity instanceof ServerPlayer _player) {
								Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("wscommands:new_world"));
								AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
								if (!_ap.isDone()) {
									for (String criteria : _ap.getRemainingCriteria())
										_player.getAdvancements().award(_adv, criteria);
								}
							}
							for (int index1 = 0; index1 < (int) (commands.size() - 1); index1++) {
								{
									Entity _ent = entity;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(),
												_ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
												commands.get((new java.text.DecimalFormat("##.##").format(X))).getAsString());
									}
								}
								X = X + 1;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

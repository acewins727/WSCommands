package net.acewins.wscommands.procedures;

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
import java.lang.reflect.Method;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class AddWorldProcedure {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        execute(event, event.getEntity().level(), event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null) return;

        File config = new File(FMLPaths.GAMEDIR.get().toFile(), "config/WSCommands.json");
        JsonObject commands = new JsonObject();
        double x = 1;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(config))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);
            }
            commands = new Gson().fromJson(jsonString.toString(), JsonObject.class);

            boolean dedicatedMode = commands.has("DedicatedMode") && commands.get("DedicatedMode").getAsBoolean();
            boolean repeat = commands.has("Repeat") && commands.get("Repeat").getAsBoolean();

            if (dedicatedMode || world.players().size() == 1) {
                if (!hasAdvancement(entity, "wscommands:new_world")) {
                    if (!repeat) {
                        grantAdvancement(entity, "wscommands:new_world");
                    }
                    for (int i = 0; i < (commands.size() - 2); i++) {
                        executeCommand(entity, commands.get(String.valueOf((int) x)).getAsString());
                        x++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasAdvancement(Entity entity, String advancement) {
        if (entity instanceof ServerPlayer player) {
            try {
                Method getAdvancement = player.server.getAdvancements().getClass().getMethod("getAdvancement", ResourceLocation.class);
                Object adv = getAdvancement.invoke(player.server.getAdvancements(), new ResourceLocation(advancement));
                if (adv != null) {
                    AdvancementProgress progress = player.getAdvancements().getOrStartProgress((Advancement) adv);
                    return progress.isDone();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static void grantAdvancement(Entity entity, String advancement) {
        if (entity instanceof ServerPlayer player) {
            try {
                Method getAdvancement = player.server.getAdvancements().getClass().getMethod("getAdvancement", ResourceLocation.class);
                Object adv = getAdvancement.invoke(player.server.getAdvancements(), new ResourceLocation(advancement));
                if (adv != null) {
                    AdvancementProgress progress = player.getAdvancements().getOrStartProgress((Advancement) adv);
                    for (String criteria : progress.getRemainingCriteria()) {
                        player.getAdvancements().award((Advancement) adv, criteria);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeCommand(Entity entity, String command) {
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(
                    new CommandSourceStack(
                            CommandSource.NULL,
                            entity.position(),
                            entity.getRotationVector(),
                            entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null,
                            4,
                            entity.getName().getString(),
                            entity.getDisplayName(),
                            entity.level().getServer(),
                            entity
                    ),
                    command
            );
        }
    }
}

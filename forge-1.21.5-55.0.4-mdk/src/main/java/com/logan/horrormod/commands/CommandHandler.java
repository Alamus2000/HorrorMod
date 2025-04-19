package com.logan.horrormod.commands;

import com.logan.horrormod.gui.SanityGUI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "horrormod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommandHandler {

    // Register the /opensanitygui command
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // Register a command to open the Sanity GUI
        event.getServer().getCommands().getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("opensanitygui")
                        .executes(context -> {
                            // Get the player who executed the command
                            Player player = context.getSource().getPlayerOrException();

                            // Open the Sanity GUI for the player
                            Minecraft.getInstance().setScreen(new SanityGUI(player));
                            return 1;
                        })
        );
    }
}

package com.logan.horrormod.commands;

import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.OpenSanityGuiPacket;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "horrormod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandHandler {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        event.getServer().getCommands().getDispatcher().register(
                Commands.literal("opensanitygui")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.sendSystemMessage(Component.literal("Opening Sanity GUI..."));

                            // Send packet to client to open the Sanity GUI
                            ModMessages.sendToPlayer(new OpenSanityGuiPacket(), player);

                            return 1;
                        })
        );
    }
}

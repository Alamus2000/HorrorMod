package com.logan.horrormod;

import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.OpenSanityGuiPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

@Mod(HorrorMod.MOD_ID)
public class HorrorMod {
    public static final String MOD_ID = "horrormod";

    public HorrorMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register the mod's event bus to let Forge discover our methods
        modEventBus.addListener(this::addCreative);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();  // Ensure messages are registered here
    }


    // Register command and handle it when executed
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        event.getServer().getCommands().getDispatcher().register(
                Commands.literal("sanity")
                        .requires(source -> source.hasPermission(0)) // Permission level 0 = all players
                        .executes(context -> {
                            // Server-side: send a packet to the client to open the GUI
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Opening Sanity GUI..."));

                            // Send packet to client (you'll define this next)
                            ModMessages.sendToPlayer(
                                    new OpenSanityGuiPacket(), // The packet to be sent
                                    player // The player to send the packet to
                            );

                            return 1;
                        })
        );
    }

    // Add to creative tab if necessary
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // This can be left empty if not needed
    }

    // Register the client setup method for registering clients-side features (optional)
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Client setup code goes here
        }
    }
}

package com.logan.horrormod;

import com.logan.horrormod.client.ClientSanityEffects;
import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.OpenSanityGuiPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mod(HorrorMod.MOD_ID)
public class HorrorMod {
    public static final String MOD_ID = "horrormod";

    public HorrorMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }


    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup completed.");
    }

}

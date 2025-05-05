package com.logan.horrormod;

import com.logan.horrormod.item.ModItems;
import com.logan.horrormod.block.ModBlocks;
import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.SoundEventsRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HorrorMod.MOD_ID)
public class HorrorMod {
    public static final String MOD_ID = "horrormod";
    public HorrorMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        SoundEventsRegistry.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.registerCommon(); // server-safe only
    }



    private void clientSetup(final FMLClientSetupEvent event) {

    }


}

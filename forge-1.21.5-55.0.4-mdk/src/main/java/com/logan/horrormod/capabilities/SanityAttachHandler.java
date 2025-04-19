package com.logan.horrormod.capabilities;

import com.logan.horrormod.HorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID)
public class SanityAttachHandler {

    private static final ResourceLocation ID = ResourceLocation.tryParse(HorrorMod.MOD_ID + ":sanity");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Player> event) {
        // Check if sanity is already attached
        if (!event.getObject().getCapability(SanityCapability.SANITY).isPresent()) {
            // Add the capability
            event.addCapability(ID, new SanityProvider());
            // Log the event for debugging
            System.out.println("Sanity capability attached to player: " + event.getObject().getName().getString());
        }
    }
}

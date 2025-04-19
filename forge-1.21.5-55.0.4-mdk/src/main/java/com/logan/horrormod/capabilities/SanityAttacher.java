package com.logan.horrormod.capabilities;

import com.logan.horrormod.HorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.player.Player;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SanityAttacher {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Player> event) {
        // Check if the sanity capability is already attached
        if (!event.getObject().getCapability(SanityCapability.SANITY).isPresent()) {
            // Attach the capability
            event.addCapability(
                    ResourceLocation.tryParse(HorrorMod.MOD_ID + ":sanity"),
                    new SanityProvider()
            );
        }
    }
}

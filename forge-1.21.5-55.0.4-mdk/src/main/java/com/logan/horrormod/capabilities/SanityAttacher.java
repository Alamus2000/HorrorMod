package com.logan.horrormod.capabilities;

import com.logan.horrormod.HorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SanityAttacher {

    public static final ResourceLocation SANITY_ID = ResourceLocation.fromNamespaceAndPath(HorrorMod.MOD_ID, "sanity");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            // Only attach if not already present
            if (!player.getCapability(SanityCapability.SANITY).isPresent()) {
                event.addCapability(SANITY_ID, new SanityProvider());
            }
        }
    }
}

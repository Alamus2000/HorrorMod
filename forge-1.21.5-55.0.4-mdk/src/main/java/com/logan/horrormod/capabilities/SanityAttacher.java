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
        event.addCapability(
                ResourceLocation.tryParse(HorrorMod.MOD_ID + ":sanity"),
                new SanityProvider()
        );

    }
}

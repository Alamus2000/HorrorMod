package com.logan.horrormod.events;

import com.logan.horrormod.HorrorMod;
import com.logan.horrormod.client.ClientSanityData;
import com.logan.horrormod.network.SoundEventsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SanityClientHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        int sanity = ClientSanityData.get();

        // Every 15 seconds (assuming 20 ticks per second)
        if (player.tickCount % 300 == 0) {
            if (sanity <= 40 && sanity > 25 && RANDOM.nextFloat() < 0.03f) {
                player.playSound(SoundEventsRegistry.WHISPERING1.get(), 1.0f, 0.8f + RANDOM.nextFloat() * 0.4f);
            } else if (sanity <= 25 && RANDOM.nextFloat() < 0.3f) {
                player.playSound(SoundEventsRegistry.WHISPERING1.get(), 1.0f, 0.8f + RANDOM.nextFloat() * 0.4f);
            }
        }

        // You could also add screen effects, hallucinations, particles, etc. here.
    }
}

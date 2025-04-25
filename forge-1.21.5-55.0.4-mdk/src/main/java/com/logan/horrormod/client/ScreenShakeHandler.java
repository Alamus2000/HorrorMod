package com.logan.horrormod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "horrormod", value = Dist.CLIENT)
public class ScreenShakeHandler {

    @SubscribeEvent
    public static void onComputeCameraAngles(ComputeCameraAngles event) {
        float sanity = ClientSanityData.get();
        if (sanity >= 30f) return;

        // Drive smooth oscillation by tick count + partial ticks
        double t = Minecraft.getInstance().player.tickCount + event.getPartialTick();
        float intensity = (30f - sanity) / 30f * 0.1f;

        // Apply sine-based wobble to rotation
        event.setYaw(event.getYaw() + (float)Math.sin(t * 0.5) * intensity);
        event.setPitch(event.getPitch() + (float)Math.cos(t * 0.4) * intensity * 0.5f);
        event.setRoll(event.getRoll() + (float)Math.sin(t * 0.7) * intensity * 0.3f);
    }
}

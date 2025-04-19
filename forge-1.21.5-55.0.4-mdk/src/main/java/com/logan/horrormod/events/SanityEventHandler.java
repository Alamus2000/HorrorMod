package com.logan.horrormod.events;

import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

@Mod.EventBusSubscriber
public class SanityEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.player;

        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            int currentSanity = sanity.getSanity();

            // Example effects based on sanity levels
            if (currentSanity < 75 && currentSanity > 50) {
                // Maybe minor screen flicker, background sounds, or nothing
            } else if (currentSanity <= 50 && currentSanity > 25) {
                // Spawn particles, play heartbeat, or minor debuffs
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 1));
                }

            } else if (currentSanity <= 25 && currentSanity > 0) {
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 150, 2));
                }
                if (!player.hasEffect(MobEffects.BLINDNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 75, 1));
                }

            } else if (currentSanity <= 0) {
                // Major consequence: damage, madness, hostile mobs, etc.
                // Example: deal damage
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 3));
                }
                if (!player.hasEffect(MobEffects.BLINDNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 750, 5)); // 5 seconds
                }
                if (player.tickCount % 100 == 0) { // Only damage every 5 seconds
                    player.hurt(player.damageSources().magic(), 1.0F);
                }

            }
        });
    }
}
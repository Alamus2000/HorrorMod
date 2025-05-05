package com.logan.horrormod.events;

import com.logan.horrormod.HorrorMod;
import com.logan.horrormod.capabilities.SanityCapability;
import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.SyncSanityPacket;
import com.logan.horrormod.network.SoundEventsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SanityServerHandler {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Only run on server at end of tick
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.player;
        Level level = player.level();

        // Light-based sanity change every 3 seconds (60 ticks)
        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            if (player.tickCount % 60 == 0) {
                BlockPos pos = player.blockPosition();
                int light = level.getMaxLocalRawBrightness(pos);

                int current = sanity.getSanity();
                int newSanity = current;

                if (light < 6) {
                    newSanity = Math.min(0, current - 1);
                } else if (light > 12) {
                    newSanity = Math.max(100, current + 1);
                }

                if (newSanity != current) {
                    sanity.setSanity(newSanity);
                    ModMessages.sendToPlayer(new SyncSanityPacket(newSanity), player);
                }
            }
        });

        // Sound cues and effects based on sanity every 15 seconds (300 ticks)
        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            int currentSanity = sanity.getSanity();

            if (player.tickCount % 300 == 0) {
                if (currentSanity <= 40 && currentSanity > 25 && RANDOM.nextFloat() < 0.03f) {
                    player.playSound(SoundEventsRegistry.WHISPERING1.get(), 1.0f, 0.8f + RANDOM.nextFloat() * 0.4f);
                } else if (currentSanity <= 25 && RANDOM.nextFloat() < 0.3f) {
                    player.playSound(SoundEventsRegistry.WHISPERING1.get(), 1.0f, 0.8f + RANDOM.nextFloat() * 0.4f);
                }
            }

            // Apply potion effects based on sanity thresholds
            if (currentSanity <= 75 && currentSanity > 50) {
                // Minor debuffs or subtle effects can go here
            } else if (currentSanity <= 50 && currentSanity > 35) {
                // Intermediate effects
            } else if (currentSanity <= 35 && currentSanity > 25) {
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 75, 1));
                }
            } else if (currentSanity <= 25 && currentSanity > 0) {
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 150, 2));
                }
                if (!player.hasEffect(MobEffects.BLINDNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 1));
                }
            } else if (currentSanity <= 0) {
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 3));
                }
                if (!player.hasEffect(MobEffects.BLINDNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 750, 1));
                }
                if (player.tickCount % 100 == 0) {
                    player.hurt(player.damageSources().magic(), 1.0F);
                }
            }
        });
    }
}


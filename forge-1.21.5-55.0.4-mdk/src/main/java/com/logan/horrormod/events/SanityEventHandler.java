package com.logan.horrormod.events;
import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.SyncSanityPacket;
import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
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
        Minecraft mc = Minecraft.getInstance();
        Level level = player.level();

        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            if (player.tickCount % 60 == 0) { // every 2 seconds

                BlockPos pos = player.blockPosition();
                int light = level.getMaxLocalRawBrightness(pos);

                int current = sanity.getSanity();
                int newSanity = current;

                if (light < 6) {
                    newSanity = Math.max(0, current - 1); // too dark, lose sanity
                } else if (light > 12) {
                    newSanity = Math.min(100, current + 1); // bright area, regain sanity
                }

                if (newSanity != current) {
                    sanity.setSanity(newSanity);
                    ModMessages.sendToPlayer(new SyncSanityPacket(newSanity), player); // ✅
                }
            }
        });

        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            int currentSanity = sanity.getSanity();

            // Example effects based on sanity levels
            if (currentSanity < 75 && currentSanity > 50) {
                // Maybe minor screen flicker, background sounds, or nothing
            } else if (currentSanity <= 50 && currentSanity > 35) {

            } else if (currentSanity <= 35 && currentSanity > 25) {
                // Spawn particles, play heartbeat, or minor debuffs
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
                // Major consequence: damage, madness, hostile mobs, etc.
                // Example: deal damage
                if (!player.hasEffect(MobEffects.WEAKNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 3));
                }
                if (!player.hasEffect(MobEffects.BLINDNESS)) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 750, 1)); // 5 seconds
                }
                if (player.tickCount % 100 == 0) { // Only damage every 5 seconds
                    player.hurt(player.damageSources().magic(), 1.0F);
                }

            }
        });
    }
}
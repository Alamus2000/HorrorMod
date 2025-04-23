package com.logan.horrormod.client;

import com.logan.horrormod.capabilities.SanityCapability;
import com.logan.horrormod.client.entity.HallucinationZombie;
import com.logan.horrormod.network.SoundEventsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.logan.horrormod.client.entity.HallucinationMimic;
import com.mojang.authlib.GameProfile;



@Mod.EventBusSubscriber(modid = "horrormod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientSanityEffects {

    private static final List<Zombie> hallucinations = new ArrayList<>();
    private static final Random RANDOM = new Random();
    private static final Random RANDOM1 = new Random();
    private static final Random RANDOM2 = new Random();

    private static final List<HallucinationMimic> mimicHallucinations = new ArrayList<>();
    private static final List<HallucinationMimic> activeMimics = new ArrayList<>();






    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase != ClientTickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;

        if (player == null || level == null) return;

        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            int currentSanity = ClientSanityData.get();
            if (currentSanity <= 15) {
                if (player.tickCount % 100 == 0 && RANDOM.nextFloat() <= 0.25f) {
                    System.out.println("Spawning fake zombie hallucination");
                    spawnFakeZombie(level, player);
                }
            }
            if (currentSanity <= 10) {
                if (player.tickCount % 100 == 0 && RANDOM1.nextFloat() <= 0.25f) {
                    System.out.println("Spawning fake player hallucination");
                    spawnFakeMimic(level, player);
                }
            }
            if (currentSanity <= 5 && player.tickCount % 200 == 0 && activeMimics.isEmpty() && RANDOM2.nextFloat() <= 0.20f) {
                System.out.println("Spawning fake players hallucination");
                spawnMimics(level, player, 6); // Spawn 6 around the player
            }
            activeMimics.removeIf(mimic -> {
                if (mimic.isRemoved()) return true;

                double dx = player.getX() - mimic.getX();
                double dz = player.getZ() - mimic.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist < 0.5 || mimic.tickCount > 300) {
                    mimic.discard();
                    return true;
                }
                return false;
            });

            mimicHallucinations.removeIf(mimic -> {
                if (mimic.isRemoved()) return true;

                double dx = player.getX() - mimic.getX();
                double dz = player.getZ() - mimic.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist < 2.0 || mimic.tickCount > 750) {
                    mimic.discard(); // too close or too old
                    return true;
                }

                return false;
            });



            hallucinations.removeIf(z -> {
                if (z.isRemoved()) {
                    System.out.println("Zombie was removed by game");
                    return true;
                }

                // Check distance between zombie and player
                double dx = player.getX() - z.getX();
                double dz = player.getZ() - z.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);

                if (dist < 2 || z.tickCount > 750) {
                    System.out.println("Removing hallucination zombie: too close or expired");
                    z.discard(); // Remove the zombie if it's too close or expired
                    return true;
                }

                return false;
            });


        });
    }


    private static void spawnFakeZombie(ClientLevel level, LocalPlayer player) {
        double angle = RANDOM.nextDouble() * 360;
        double distance = 6 + RANDOM.nextDouble() * 4;
        double rad = Math.toRadians(angle);

        double dx = player.getX() + Math.cos(rad) * distance;
        double dz = player.getZ() + Math.sin(rad) * distance;
        double dy = player.getY();

        HallucinationZombie fakeZombie = new HallucinationZombie(level);
        fakeZombie.setPos(dx, dy, dz);
        level.addEntity(fakeZombie);
        hallucinations.add(fakeZombie);
        player.playSound(SoundEvents.ZOMBIE_AMBIENT, 1.0f, 1.0f);

    }

    private static void spawnFakeMimic(ClientLevel level, LocalPlayer player) {
        double angle = RANDOM1.nextDouble() * 360;
        double distance = 6 + RANDOM1.nextDouble() * 4;
        double rad = Math.toRadians(angle);

        double dx = player.getX() + Math.cos(rad) * distance;
        double dz = player.getZ() + Math.sin(rad) * distance;
        double dy = player.getY();

        GameProfile fakeProfile = new GameProfile(UUID.randomUUID(), "???");
        HallucinationMimic mimic = new HallucinationMimic(level, fakeProfile);
        mimic.setPos(dx, dy, dz);
        level.addEntity(mimic);
        mimicHallucinations.add(mimic);
    }
    private static void spawnMimics(ClientLevel level, LocalPlayer player, int count) {
        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI / count) * i;
            double radius = 3 + RANDOM.nextDouble() * 1.5; // 2.0–3.5 blocks away
            double dx = player.getX() + radius * Math.cos(angle);
            double dz = player.getZ() + radius * Math.sin(angle);
            double dy = player.getY();

            GameProfile fakeProfile = new GameProfile(UUID.randomUUID(), "???");
            HallucinationMimic mimic = new HallucinationMimic(level, fakeProfile);
            mimic.setPos(dx, dy, dz);

            // Face the player
            double lookX = player.getX() - dx;
            double lookZ = player.getZ() - dz;
            float yaw = (float) Math.toDegrees(Math.atan2(-lookX, -lookZ));
            mimic.setYRot(yaw);
            mimic.yBodyRot = yaw;
            mimic.yHeadRot = yaw;

            level.addEntity(mimic);
            activeMimics.add(mimic);
        }
    }


}

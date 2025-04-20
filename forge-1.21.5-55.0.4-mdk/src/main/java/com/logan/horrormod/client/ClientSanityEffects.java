package com.logan.horrormod.client;

import com.logan.horrormod.capabilities.SanityCapability;
import com.logan.horrormod.client.entity.HallucinationZombie;
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



@Mod.EventBusSubscriber(modid = "horrormod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientSanityEffects {

    private static final List<Zombie> hallucinations = new ArrayList<>();
    private static final Random RANDOM = new Random();



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

}

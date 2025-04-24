package com.logan.horrormod.client;

import com.logan.horrormod.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = "horrormod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientBlockHallucinationHandler {
    private static final Random RANDOM = new Random();

    // Active hallucinations: position → (original state, ticks left)
    private static final Map<BlockPos, Pair<BlockState, Integer>> active = new HashMap<>();

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        if (event.phase != ClientTickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) return;

        // Only trigger when sanity is low
        if (ClientSanityData.get() > 25) {
            // If sanity recovered, immediately revert all
            active.forEach((pos, data) -> level.setBlock(pos, data.first(), 19));
            active.clear();
            return;
        }

        // 1) Revert any expired hallucinations
        Iterator<Map.Entry<BlockPos, Pair<BlockState, Integer>>> it = active.entrySet().iterator();
        while (it.hasNext()) {
            var entry = it.next();
            BlockPos pos = entry.getKey();
            Pair<BlockState, Integer> data = entry.getValue();
            int ticksLeft = data.second() - 1;
            if (ticksLeft <= 0) {
                level.setBlock(pos, data.first(), 19);
                it.remove();
            } else {
                entry.setValue(new Pair<>(data.first(), ticksLeft));
            }
        }

        // 2) Occasionally spawn a new hallucination
        //    – here: once every 20 ticks, 10% chance
        if (player.tickCount % 20 == 0 && RANDOM.nextFloat() < 0.1f) {
            BlockPos center = player.blockPosition();
            BlockPos target = getRandomNearby(center, 5);
            if (!active.containsKey(target)) {
                BlockState original = level.getBlockState(target);
                // Choose your hallucination block here:
                BlockState halluc = ModBlocks.MISSING_TEXTURE_BLOCK.get().defaultBlockState();

                // Apply visual-only change:
                level.setBlock(target, halluc, 19);

                // Remember original and lifespan (40 ticks = 2 seconds)
                active.put(target, new Pair<>(original, 40));
            }
        }
    }

    // Returns a random position within radius horizontally, ±1 vertically
    private static BlockPos getRandomNearby(BlockPos center, int radius) {
        int dx = RANDOM.nextInt(radius * 2 + 1) - radius;
        int dy = RANDOM.nextInt(3) - 1;
        int dz = RANDOM.nextInt(radius * 2 + 1) - radius;
        return center.offset(dx, dy, dz);
    }

    // Simple Pair helper
    private record Pair<F, S>(F first, S second) {}
}

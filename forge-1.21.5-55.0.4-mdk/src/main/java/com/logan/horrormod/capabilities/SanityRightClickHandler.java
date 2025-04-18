package com.logan.horrormod.capabilities;

import com.logan.horrormod.HorrorMod;
import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SanityRightClickHandler {

    // Subscribe to the event when a player right-clicks a block
    @SubscribeEvent
    public static void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        // Get the player who triggered the event
        Player player = (Player) event.getEntity();  // Cast the entity to Player

        // Check if the sanity capability is attached
        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            // Capability is present and accessible
            System.out.println("Sanity capability is attached!");

            // Example action: Reduce sanity by 1 when player right-clicks a block
            sanity.reduceSanity(1);
        });
    }

    // Subscribe to the event when a player right-clicks an item
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        // Get the player who triggered the event
        Player player = (Player) event.getEntity();  // Cast the entity to Player

        // Check if the sanity capability is attached
        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
            // Capability is present and accessible
            System.out.println("Sanity capability is attached!");

            // Example action: Reduce sanity by 1 when player right-clicks an item
            sanity.reduceSanity(1);
        });
    }
}

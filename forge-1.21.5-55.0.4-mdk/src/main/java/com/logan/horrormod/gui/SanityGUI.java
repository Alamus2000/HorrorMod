package com.logan.horrormod.gui;

import com.logan.horrormod.capabilities.SanityCapability;
import com.logan.horrormod.network.SanityChangePacket; // Correct import
import com.logan.horrormod.network.ModMessages; // Correct import
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class SanityGUI extends Screen {

    private final Player player;

    public SanityGUI(Player player) {
        super(Component.literal("Sanity GUI"));
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();

        // Add buttons to increase and decrease sanity
        this.addRenderableWidget(Button.builder(Component.literal("Increase Sanity"), (button) -> {
            // Send packet to increase sanity by 5
            ModMessages.sendToServer(new SanityChangePacket(5));  // Correct usage here
        }).bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Decrease Sanity"), (button) -> {
            // Send packet to decrease sanity by 5
            ModMessages.sendToServer(new SanityChangePacket(-5));  // Correct usage here
        }).bounds(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        // Get current sanity level from the player
        String sanityText = "Sanity Level: " + player.getCapability(SanityCapability.SANITY)
                .map(sanity -> sanity.getSanity())  // Use map to get the sanity value
                .orElse(0);  // Default to 0 if no capability

        // Center the text horizontally
        int x = this.width / 2 - this.font.width(sanityText) / 2;

        // Draw the sanity level text
        graphics.drawString(this.font, sanityText, x, this.height / 2 - 40, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Don't pause the game when the GUI is open
    }
}

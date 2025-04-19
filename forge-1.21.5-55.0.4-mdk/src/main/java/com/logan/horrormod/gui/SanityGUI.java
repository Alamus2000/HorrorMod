package com.logan.horrormod.gui;

import com.logan.horrormod.capabilities.SanityCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.gui.GuiGraphics;

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
            // Increase sanity by 5
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.addSanity(5);
            });
        }).bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Decrease Sanity"), (button) -> {
            // Decrease sanity by 5
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.reduceSanity(5);
            });
        }).bounds(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        // Get current sanity level
        String sanityText = "Sanity Level: " + player.getCapability(SanityCapability.SANITY)
                .map(sanity -> sanity.getSanity())
                .orElse(0);

        // Center the text horizontally
        int x = this.width / 2 - this.font.width(sanityText) / 2;

        // Draw the text (display sanity level)
        graphics.drawString(this.font, sanityText, x, this.height / 2 - 40, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Don't pause the game when the GUI is open
    }
}

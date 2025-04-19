package com.logan.horrormod.gui;

import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.mojang.blaze3d.vertex.PoseStack;

public class SanityGUI extends Screen {

    private final Player player;

    public SanityGUI(Player player) {
        super(Component.literal("Sanity GUI")); // Using Component.literal instead of TextComponent
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();

        // Create buttons for increasing and decreasing sanity using Button.builder()
        this.addRenderableWidget(Button.builder(Component.literal("Increase"), (button) -> {
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.addSanity(5); // Increase sanity by 5
            });
        }).bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Decrease"), (button) -> {
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.reduceSanity(5); // Decrease sanity by 5
            });
        }).bounds(this.width / 2 - 50, this.height / 2 + 20, 100, 20).build());
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack); // Render the background using PoseStack
        super.render(poseStack, mouseX, mouseY, partialTick);

        // Render the player's sanity level
        String sanityText = "Sanity Level: " + player.getCapability(SanityCapability.SANITY).map(sanity -> sanity.getSanity()).orElse(0);

        // Draw the text at a specific position using the font renderer
        Minecraft.getInstance().font.draw(poseStack, sanityText, this.width / 2, this.height / 2 - 40);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Don't pause the game when the screen is opened
    }
}

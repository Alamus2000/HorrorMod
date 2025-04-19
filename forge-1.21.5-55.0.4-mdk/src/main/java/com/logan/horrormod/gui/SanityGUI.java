package com.logan.horrormod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;

public class SanityGUI extends Screen {

    private final Player player;

    public SanityGUI(Player player) {
        super(Component.literal("Sanity GUI")); // Using Component.literal instead of TextComponent
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();

        // Create buttons for increasing and decreasing sanity
        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 - 10, 100, 20, Component.literal("Increase"), button -> {
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.addSanity(5); // Increase sanity by 5
            });
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + 20, 100, 20, Component.literal("Decrease"), button -> {
            player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                sanity.reduceSanity(5); // Decrease sanity by 5
            });
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {
        this.renderBackground();

        super.render(mouseX, mouseY, partialTick);
        // Render the player's sanity level
        drawString(Minecraft.getInstance().font, "Sanity Level: " + player.getCapability(SanityCapability.SANITY).map(sanity -> sanity.getSanity()).orElse(0), this.width / 2 - 50, this.height / 2 - 40);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Don't pause the game when the screen is opened
    }
}

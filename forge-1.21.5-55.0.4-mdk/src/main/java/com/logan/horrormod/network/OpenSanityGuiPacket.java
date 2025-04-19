package com.logan.horrormod.network;

import com.logan.horrormod.gui.SanityGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class OpenSanityGuiPacket {

    public OpenSanityGuiPacket() {
    }

    public static OpenSanityGuiPacket newPacket(FriendlyByteBuf buf) {
        return new OpenSanityGuiPacket();
    }

    public void toBytes(FriendlyByteBuf buf) {
        // No data to write
    }

    public static void handle(OpenSanityGuiPacket message, CustomPayloadEvent.Context context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.setScreen(new SanityGUI(mc.player)); // Open the Sanity GUI
        }
    }
}


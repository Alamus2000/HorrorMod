package com.logan.horrormod.network;

import com.logan.horrormod.gui.SanityGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkContext;

public class OpenSanityGuiPacket {
    public OpenSanityGuiPacket() {}

    public static void encode(OpenSanityGuiPacket msg, FriendlyByteBuf buf) {
        // In this case, there's no data to write, but you could write something if needed
        // For now, just leave it empty
    }

    public static OpenSanityGuiPacket decode(FriendlyByteBuf buf) {
        return new OpenSanityGuiPacket();
    }

    public static void handle(OpenSanityGuiPacket msg, NetworkContext ctx) {
        // Handle the packet (opening the GUI)
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.setScreen(new SanityGUI(mc.player));
        }
    }
}

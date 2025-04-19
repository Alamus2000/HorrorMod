package com.logan.horrormod.network;

import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.minecraft.resources.ResourceLocation;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1.0";

    // Create the SimpleChannel to handle network messages
    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("horrormod", "main_channel"))
            .networkProtocolVersion(Integer.parseInt(PROTOCOL_VERSION))
            .clientAcceptedVersions(((ver, dir) -> ver.equals(PROTOCOL_VERSION)))
            .serverAcceptedVersions(((ver, dir) -> ver.equals(PROTOCOL_VERSION)))
            .simpleChannel();

    private static int packetId = 0;

    // Register the messages
    public static void register() {
        INSTANCE.messageBuilder(OpenSanityGuiPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(OpenSanityGuiPacket::toBytes)
                .decoder(OpenSanityGuiPacket::newPacket)
                .consumerMainThread(OpenSanityGuiPacket::handle)
                .add();
    }

    // Send a packet to a specific player (client)
    public static void sendToPlayer(OpenSanityGuiPacket message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with((player)));
    }
    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }
}
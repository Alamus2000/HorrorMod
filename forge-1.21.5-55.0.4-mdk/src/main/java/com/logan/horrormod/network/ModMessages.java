package com.logan.horrormod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModMessages {
    private static final int PROTOCOL_VERSION = 1;

    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("horrormod", "main_channel"))
            .networkProtocolVersion(PROTOCOL_VERSION)                      // ← int here
            .acceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))        // ← same VersionTest on both sides
            .simpleChannel();

    private static int packetId = 0;

    public static void register() {
        INSTANCE.messageBuilder(OpenSanityGuiPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(OpenSanityGuiPacket::toBytes)
                .decoder(OpenSanityGuiPacket::newPacket)
                .consumerMainThread(OpenSanityGuiPacket::handle)
                .add();

        INSTANCE.messageBuilder(SanityChangePacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SanityChangePacket::encode)
                .decoder(SanityChangePacket::decode)
                .consumerMainThread(SanityChangePacket::handle)
                .add();

        INSTANCE.messageBuilder(SyncSanityPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncSanityPacket::encode)
                .decoder(SyncSanityPacket::decode)
                .consumerMainThread(SyncSanityPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }

}


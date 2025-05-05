package com.logan.horrormod.network;

import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.*;

public class ModMessages {
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("horrormod","main_channel"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .acceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();

    public static int packetId = 0;

    /** Call this from your FMLCommonSetupEvent */
    public static void registerCommon() {
        //client → server: SanityChangePacket
        INSTANCE.messageBuilder(SanityChangePacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SanityChangePacket::encode)
                .decoder(SanityChangePacket::decode)
                .consumerMainThread((msg, ctx) -> {
                    ctx.enqueueWork(() -> {
                        ServerPlayer player = ctx.getSender();
                        if (player == null) return;
                        player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                            if (msg.delta > 0) sanity.addSanity(msg.delta);
                            else sanity.reduceSanity(Math.abs(msg.delta));
                            // Sync back
                            int newVal = sanity.getSanity();
                            INSTANCE.send(
                                    new SyncSanityPacket(newVal),
                                    PacketDistributor.PLAYER.with(player)
                            );
                        });
                    });
                    ctx.setPacketHandled(true);
                })
                .add();

        //server → client: SyncSanityPacket
        INSTANCE.messageBuilder(SyncSanityPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncSanityPacket::encode)
                .decoder(SyncSanityPacket::decode)
                .consumerMainThread((msg, ctx) -> {
                    com.logan.horrormod.client.ClientSanityData.set(msg.sanity);
                })
                .add();

    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }

}

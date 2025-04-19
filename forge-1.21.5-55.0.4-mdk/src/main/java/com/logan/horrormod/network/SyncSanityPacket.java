package com.logan.horrormod.network;

import com.logan.horrormod.client.ClientSanityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkContext;


import java.util.function.Supplier;

public class SyncSanityPacket {
    private final int sanity;
    public SyncSanityPacket(int sanity) { this.sanity = sanity; }

    public static void encode(SyncSanityPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.sanity);
    }

    public static SyncSanityPacket decode(FriendlyByteBuf buf) {
        return new SyncSanityPacket(buf.readInt());
    }

    public static void handle(SyncSanityPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ClientSanityData.set(msg.sanity);
        });
        ctx.setPacketHandled(true);
    }
}


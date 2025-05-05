package com.logan.horrormod.network;

import net.minecraft.network.FriendlyByteBuf;

public class SyncSanityPacket {
    public final int sanity;

    public SyncSanityPacket(int sanity) {
        this.sanity = sanity;
    }

    public static void encode(SyncSanityPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.sanity);
    }

    public static SyncSanityPacket decode(FriendlyByteBuf buf) {
        return new SyncSanityPacket(buf.readInt());
    }
}


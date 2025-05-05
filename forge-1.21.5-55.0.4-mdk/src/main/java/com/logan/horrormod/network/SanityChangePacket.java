package com.logan.horrormod.network;

import net.minecraft.network.FriendlyByteBuf;

public class SanityChangePacket {
    public final int delta;

    public SanityChangePacket(int delta) {
        this.delta = delta;
    }

    public static void encode(SanityChangePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.delta);
    }

    public static SanityChangePacket decode(FriendlyByteBuf buf) {
        return new SanityChangePacket(buf.readInt());
    }

}

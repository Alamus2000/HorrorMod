package com.logan.horrormod.network;


import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkContext;

public class SanityChangePacket {
    private final int delta;

    public SanityChangePacket(int delta) {
        this.delta = delta;
    }

    public static void encode(SanityChangePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.delta);
    }

    public static SanityChangePacket decode(FriendlyByteBuf buf) {
        return new SanityChangePacket(buf.readInt());
    }

    public static void handle(SanityChangePacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                player.getCapability(SanityCapability.SANITY).ifPresent(sanity -> {
                    if (msg.delta > 0) {
                        sanity.addSanity(msg.delta);
                    } else {
                        sanity.reduceSanity(-msg.delta);
                    }
                });
            }
        });
    }
}

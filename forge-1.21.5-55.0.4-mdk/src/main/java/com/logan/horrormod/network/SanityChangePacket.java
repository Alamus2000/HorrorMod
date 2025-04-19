package com.logan.horrormod.network;

import com.logan.horrormod.capabilities.SanityCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;


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
                    // 1) Update the server-side value
                    if (msg.delta > 0) {
                        sanity.addSanity(msg.delta);
                    } else {
                        sanity.reduceSanity(Math.abs(msg.delta));
                    }

                    // 2) Immediately sync the new value back to that client
                    int newValue = sanity.getSanity();
                    ModMessages.sendToPlayer(new SyncSanityPacket(newValue), player);

                    // 3) (Optional) Debug
                    System.out.println("[Sanity] Synced new value " + newValue + " to " + player.getName().getString());
                });
            }
        });
        ctx.setPacketHandled(true);
    }



}

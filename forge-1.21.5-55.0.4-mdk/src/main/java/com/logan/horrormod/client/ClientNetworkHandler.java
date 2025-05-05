package com.logan.horrormod.client;


import com.logan.horrormod.HorrorMod;
import com.logan.horrormod.gui.SanityGUI;
import com.logan.horrormod.network.ModMessages;
import com.logan.horrormod.network.OpenSanityGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = HorrorMod.MOD_ID,
        value = Dist.CLIENT,
        bus   = Mod.EventBusSubscriber.Bus.MOD)
public class ClientNetworkHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent evt) {
        // only here, on the client:
        ModMessages.INSTANCE.messageBuilder(
                        OpenSanityGuiPacket.class,
                        ModMessages.packetId++,
                        NetworkDirection.PLAY_TO_CLIENT
                )
                .encoder(OpenSanityGuiPacket::encode)
                .decoder(OpenSanityGuiPacket::decode)
                .consumerMainThread((msg, ctx) -> {
                    Minecraft.getInstance().setScreen(
                            new SanityGUI(Minecraft.getInstance().player)
                    );
                })
                .add();
    }
}



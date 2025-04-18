package com.logan.horrormod.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SanityCapability {
    public static final Capability<ISanity> SANITY = CapabilityManager.get(new CapabilityToken<>() {});
}


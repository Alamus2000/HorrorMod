package com.logan.horrormod.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ISanity {
    int getSanity();
    void setSanity(int amount);
    void addSanity(int amount);
    void reduceSanity(int amount);
}


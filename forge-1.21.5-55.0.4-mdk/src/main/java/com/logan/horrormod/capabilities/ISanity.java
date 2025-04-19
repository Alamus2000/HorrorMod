package com.logan.horrormod.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ISanity {
    int getSanity();
    void setSanity(int amount);

    default void addSanity(int amount) {
        setSanity(getSanity() + amount);
    }

    default void reduceSanity(int amount) {
        setSanity(getSanity() - amount);
    }
}


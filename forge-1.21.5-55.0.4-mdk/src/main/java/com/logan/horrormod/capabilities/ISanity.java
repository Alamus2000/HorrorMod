package com.logan.horrormod.capabilities;

public interface ISanity {
    int getSanity();  // Must return an int
    void setSanity(int amount);  // Accepts an int
    void addSanity(int amount);
    void reduceSanity(int amount);
}



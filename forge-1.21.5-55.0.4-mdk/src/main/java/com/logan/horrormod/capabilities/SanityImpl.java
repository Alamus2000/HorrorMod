package com.logan.horrormod.capabilities;

public class SanityImpl implements ISanity {
    private int sanity = 100;

    @Override
    public int getSanity() {
        return sanity;
    }

    @Override
    public void setSanity(int sanity) {
        this.sanity = Math.max(0, Math.min(100, sanity)); // Clamp between 0 and 100
    }

    @Override
    public void addSanity(int amount) {
        setSanity(sanity + amount);
    }

    @Override
    public void reduceSanity(int amount) {
        setSanity(sanity - amount);
    }
}


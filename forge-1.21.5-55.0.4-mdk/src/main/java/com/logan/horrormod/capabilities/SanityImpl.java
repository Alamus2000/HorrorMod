package com.logan.horrormod.capabilities;

public class SanityImpl implements ISanity {
    private int sanity = 100;

    @Override
    public int getSanity() {
        return sanity;
    }

    @Override
    public void setSanity(int sanity) {
        // Clamping the sanity value directly here
        this.sanity = Math.max(0, Math.min(100, sanity));
    }

    @Override
    public void addSanity(int amount) {
        // Simply modify sanity and clamp in setSanity
        setSanity(this.sanity + amount);
    }

    @Override
    public void reduceSanity(int amount) {
        // Simply modify sanity and clamp in setSanity
        setSanity(this.sanity - amount);
    }
}

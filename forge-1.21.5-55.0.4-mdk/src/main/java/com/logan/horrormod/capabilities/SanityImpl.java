package com.logan.horrormod.capabilities;


public class SanityImpl implements ISanity {
    private int sanity = 100;

    @Override
    public int getSanity() {
        return sanity;  // Should return an int, not Optional
    }

    @Override
    public void setSanity(int sanity) {
        this.sanity = Math.max(0, Math.min(100, sanity)); // Clamps sanity between 0 and 100
    }

    @Override
    public void addSanity(int amount) {
        setSanity(this.sanity + amount);
    }

    @Override
    public void reduceSanity(int amount) {
        setSanity(this.sanity - amount);
    }
}


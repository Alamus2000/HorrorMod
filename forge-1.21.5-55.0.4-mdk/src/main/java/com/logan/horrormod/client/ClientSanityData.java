package com.logan.horrormod.client;

public class ClientSanityData {
    private static int sanity = 100;

    public static void set(int newSanity) {
        sanity = newSanity;
    }

    public static int get() {
        return sanity;
    }
}

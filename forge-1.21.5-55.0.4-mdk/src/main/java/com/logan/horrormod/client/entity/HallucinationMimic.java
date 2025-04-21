package com.logan.horrormod.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;

public class HallucinationMimic extends RemotePlayer {
    public HallucinationMimic(ClientLevel level, GameProfile profile) {
        super(level, profile);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setCustomNameVisible(false);
    }

    @Override
    public void tick() {
        super.tick();

        // only on client-side
        if (!(this.level() instanceof ClientLevel)) return;
        if (this.isRemoved()) return;

        // grab the real player
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        // compute deltas
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double dy = player.getEyeY() - this.getEyeY();
        double horizontalDist = Math.sqrt(dx*dx + dz*dz);
        if (horizontalDist < 0.0001) return;

        // --- HORIZONTAL (yaw) ---
        // atan2(dz, dx) gives angle relative to +X axis; subtract 90 to align with 0° = +Z
        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        this.setYRot(yaw);
        this.yBodyRot = yaw;
        this.yBodyRotO = yaw;
        this.yHeadRot = yaw;
        this.yHeadRotO = yaw;

        // --- VERTICAL (pitch) ---
        float pitch = (float) Math.toDegrees(-Math.atan2(dy, horizontalDist));
        this.setXRot(pitch);
        this.xRotO = pitch;
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}

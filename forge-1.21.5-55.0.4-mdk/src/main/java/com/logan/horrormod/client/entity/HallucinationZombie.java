
package com.logan.horrormod.client.entity;

import net.minecraft.client.multiplayer.ClientLevel;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.EntityType;

public class HallucinationZombie extends Zombie {
    public HallucinationZombie(ClientLevel level) {
        super(EntityType.ZOMBIE, level);
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setCustomNameVisible(false);
    }

    @Override
    public boolean isInvisible() {
        return false; // Keep visible
    }

    @Override
    public boolean isPickable() {
        return false; // Can't be hit
    }
}

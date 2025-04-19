package com.logan.horrormod.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SanityProvider implements ICapabilitySerializable<CompoundTag> {
    private final ISanity instance = new SanityImpl();
    private final LazyOptional<ISanity> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == SanityCapability.SANITY ? optional.cast() : LazyOptional.empty();
    }

    // <-- Remove this block entirely:
    // @Override
    // public CompoundTag serializeNBT() { ... }
    //
    // @Override
    // public void deserializeNBT(CompoundTag nbt) { ... }
    // -->

    // The ONLY NBT methods you need in 1.21+
    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registryAccess) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Sanity", instance.getSanity());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registryAccess, CompoundTag nbt) {
        // Pass the sanity value from the NBT data to setSanity
        nbt.getInt("Sanity");
    }


}

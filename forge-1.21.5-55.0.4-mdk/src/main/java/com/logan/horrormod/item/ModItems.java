package com.logan.horrormod.item;

import com.logan.horrormod.HorrorMod;
import com.logan.horrormod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HorrorMod.MOD_ID);

    public static final RegistryObject<Item> MISSING_TEXTURE_BLOCK_ITEM = ITEMS.register("missing_texture_block",
            () -> new BlockItem(ModBlocks.MISSING_TEXTURE_BLOCK.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(HorrorMod.MOD_ID, "missing_texture_block")))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
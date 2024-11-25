/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.*;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.containers.CraftingStationMenu;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ImprovedStations implements ModInitializer {
    public static final ResourceLocation CRAFTING_STATION_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "crafting_station");
    public static final ResourceLocation CRAFTING_STATION_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "crafting_station_slab");
    public static final ResourceLocation FURNACE_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "furnace_slab");
    public static final ResourceLocation SMOKER_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "smoker_slab");
    public static final ResourceLocation BLAST_FURNACE_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "blast_furnace_slab");
    public static final ResourceLocation CRAFTING_TABLE_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "crafting_table_slab");
    public static final ResourceLocation JUKEBOX_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "jukebox_slab");
    public static final ResourceLocation LOOM_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "loom_slab");
    public static final ResourceLocation CARTOGRAPHY_TABLE_SLAB_ID = ResourceLocation.fromNamespaceAndPath("improved-stations", "cartography_table_slab");

    public static final ResourceKey<Block> CRAFTING_STATION_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, CRAFTING_STATION_ID);
    public static final ResourceKey<Block> CRAFTING_STATION_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, CRAFTING_STATION_SLAB_ID);
    public static final ResourceKey<Block> FURNACE_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, FURNACE_SLAB_ID);
    public static final ResourceKey<Block> SMOKER_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, SMOKER_SLAB_ID);
    public static final ResourceKey<Block> BLAST_FURNACE_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, BLAST_FURNACE_SLAB_ID);
    public static final ResourceKey<Block> CRAFTING_TABLE_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, CRAFTING_TABLE_SLAB_ID);
    public static final ResourceKey<Block> JUKEBOX_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, JUKEBOX_SLAB_ID);
    public static final ResourceKey<Block> LOOM_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, LOOM_SLAB_ID);
    public static final ResourceKey<Block> CARTOGRAPHY_TABLE_SLAB_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, CARTOGRAPHY_TABLE_SLAB_ID);

    public static final ResourceKey<Item> CRAFTING_STATION_ITEM_KEY = ResourceKey.create(Registries.ITEM, CRAFTING_STATION_ID);
    public static final ResourceKey<Item> CRAFTING_STATION_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, CRAFTING_STATION_SLAB_ID);
    public static final ResourceKey<Item> FURNACE_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, FURNACE_SLAB_ID);
    public static final ResourceKey<Item> SMOKER_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, SMOKER_SLAB_ID);
    public static final ResourceKey<Item> BLAST_FURNACE_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, BLAST_FURNACE_SLAB_ID);
    public static final ResourceKey<Item> CRAFTING_TABLE_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, CRAFTING_TABLE_SLAB_ID);
    public static final ResourceKey<Item> JUKEBOX_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, JUKEBOX_SLAB_ID);
    public static final ResourceKey<Item> LOOM_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, LOOM_SLAB_ID);
    public static final ResourceKey<Item> CARTOGRAPHY_TABLE_SLAB_ITEM_KEY = ResourceKey.create(Registries.ITEM, CARTOGRAPHY_TABLE_SLAB_ID);

    public static final Block CRAFTING_STATION = new CraftingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).setId(CRAFTING_STATION_BLOCK_KEY).noOcclusion());
    public static final Block CRAFTING_STATION_SLAB = new CraftingStationSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).setId(CRAFTING_TABLE_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block FURNACE_SLAB = new FurnaceSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).setId(FURNACE_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block SMOKER_SLAB = new SmokerSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOKER).setId(SMOKER_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block BLAST_FURNACE_SLAB = new BlastFurnaceSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLAST_FURNACE).setId(BLAST_FURNACE_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block CRAFTING_TABLE_SLAB = new CraftingTableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).setId(CRAFTING_TABLE_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block JUKEBOX_SLAB = new JukeboxSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUKEBOX).setId(JUKEBOX_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block LOOM_SLAB = new LoomSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LOOM).setId(LOOM_SLAB_BLOCK_KEY).noOcclusion());
    public static final Block CARTOGRAPHY_TABLE_SLAB = new CartographyTableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARTOGRAPHY_TABLE).setId(CARTOGRAPHY_TABLE_SLAB_BLOCK_KEY).noOcclusion());
    public static final BlockEntityType<CraftingStationBlockEntity> CRAFTING_STATION_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(CraftingStationBlockEntity::new, CRAFTING_STATION, CRAFTING_STATION_SLAB).build(null);
    public static final MenuType<CraftingStationMenu> CRAFTING_STATION_TYPE = new ExtendedScreenHandlerType<>((syncId, playerInventory, pos) -> new CraftingStationMenu(syncId, playerInventory, (CraftingStationBlockEntity) playerInventory.player.level().getBlockEntity(pos), ContainerLevelAccess.create(playerInventory.player.level(), pos)), BlockPos.STREAM_CODEC);
    
    @Override
    public void onInitialize() {
        registerBlock(CRAFTING_STATION_BLOCK_KEY, CRAFTING_STATION);
        registerItem(CRAFTING_STATION_ITEM_KEY, new BlockItem(CRAFTING_STATION, new Item.Properties().setId(CRAFTING_STATION_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(CRAFTING_STATION_SLAB_BLOCK_KEY, CRAFTING_STATION_SLAB);
        registerItem(CRAFTING_STATION_SLAB_ITEM_KEY, new BlockItem(CRAFTING_STATION_SLAB, new Item.Properties().setId(CRAFTING_STATION_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(FURNACE_SLAB_BLOCK_KEY, FURNACE_SLAB);
        registerItem(FURNACE_SLAB_ITEM_KEY, new BlockItem(FURNACE_SLAB, new Item.Properties().setId(FURNACE_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(SMOKER_SLAB_BLOCK_KEY, SMOKER_SLAB);
        registerItem(SMOKER_SLAB_ITEM_KEY, new BlockItem(SMOKER_SLAB, new Item.Properties().setId(SMOKER_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(BLAST_FURNACE_SLAB_BLOCK_KEY, BLAST_FURNACE_SLAB);
        registerItem(BLAST_FURNACE_SLAB_ITEM_KEY, new BlockItem(BLAST_FURNACE_SLAB, new Item.Properties().setId(BLAST_FURNACE_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(CRAFTING_TABLE_SLAB_BLOCK_KEY, CRAFTING_TABLE_SLAB);
        registerItem(CRAFTING_TABLE_SLAB_ITEM_KEY, new BlockItem(CRAFTING_TABLE_SLAB, new Item.Properties().setId(CRAFTING_TABLE_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(JUKEBOX_SLAB_BLOCK_KEY, JUKEBOX_SLAB);
        registerItem(JUKEBOX_SLAB_ITEM_KEY, new BlockItem(JUKEBOX_SLAB, new Item.Properties().setId(JUKEBOX_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(LOOM_SLAB_BLOCK_KEY, LOOM_SLAB);
        registerItem(LOOM_SLAB_ITEM_KEY, new BlockItem(LOOM_SLAB, new Item.Properties().setId(LOOM_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        registerBlock(CARTOGRAPHY_TABLE_SLAB_BLOCK_KEY, CARTOGRAPHY_TABLE_SLAB);
        registerItem(CARTOGRAPHY_TABLE_SLAB_ITEM_KEY, new BlockItem(CARTOGRAPHY_TABLE_SLAB, new Item.Properties().setId(CARTOGRAPHY_TABLE_SLAB_ITEM_KEY).useBlockDescriptionPrefix()));

        Registry.registerForHolder(BuiltInRegistries.BLOCK_ENTITY_TYPE, CRAFTING_STATION_ID, CRAFTING_STATION_BLOCK_ENTITY);

        Registry.registerForHolder(BuiltInRegistries.MENU, CRAFTING_STATION_ID, CRAFTING_STATION_TYPE);
    }

    public static void registerItem(ResourceKey<Item> id, Item item) {
        Registry.registerForHolder(BuiltInRegistries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.accept(item));
    }

    public static void registerBlock(ResourceKey<Block> id, Block block) {
        Registry.registerForHolder(BuiltInRegistries.BLOCK, id, block);
    }
}

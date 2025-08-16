/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.entities.renderer.CraftingStationBlockEntityRenderer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public class ImprovedStationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ImprovedStations.CRAFTING_STATION_TYPE, CraftingStationScreen::new);
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, ImprovedStations.CRAFTING_STATION, ImprovedStations.CRAFTING_STATION_SLAB);
        BlockEntityRenderers.register(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, CraftingStationBlockEntityRenderer::new);
    }
}

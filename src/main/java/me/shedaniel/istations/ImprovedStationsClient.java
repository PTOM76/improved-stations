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
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

@Environment(EnvType.CLIENT)
public class ImprovedStationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ImprovedStations.CRAFTING_STATION_TYPE, CraftingStationScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ImprovedStations.CRAFTING_STATION, ImprovedStations.CRAFTING_STATION_SLAB);
        BlockEntityRenderers.register(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, CraftingStationBlockEntityRenderer::new);
    }
}

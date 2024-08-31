/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ImprovedStationsREIPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(BuiltinPlugin.CRAFTING, of(ImprovedStations.CRAFTING_STATION_SLAB), of(ImprovedStations.CRAFTING_TABLE_SLAB));
        registry.addWorkstations(BuiltinPlugin.SMELTING, of(ImprovedStations.FURNACE_SLAB));
        registry.addWorkstations(BuiltinPlugin.SMOKING, of(ImprovedStations.SMOKER_SLAB));
        registry.addWorkstations(BuiltinPlugin.BLASTING, of(ImprovedStations.BLAST_FURNACE_SLAB));
    }

    public static EntryStack<ItemStack> of(ItemLike item) {
        return EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(item));
    }
}

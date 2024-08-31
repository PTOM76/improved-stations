/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingMenu.class)
public abstract class MixinCraftingTableContainer extends RecipeBookMenu<CraftingInput, CraftingRecipe> {
    @Shadow @Final private ContainerLevelAccess access;
    
    public MixinCraftingTableContainer(MenuType<?> containerType, int i) {
        super(containerType, i);
    }
    
    @Inject(method = "stillValid", at = @At("RETURN"), cancellable = true)
    private void canUse(Player player, CallbackInfoReturnable<Boolean> callback) {
        if (!callback.getReturnValue()) callback.setReturnValue(stillValid(this.access, player, ImprovedStations.CRAFTING_TABLE_SLAB));
    }
}

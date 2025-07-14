/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ConditionalItemModel.class)
public interface MixinConditionalItemModel {
    @Accessor("onTrue")
    ItemModel getOnTrue();

    @Accessor("onFalse")
    ItemModel getOnFalse();
}

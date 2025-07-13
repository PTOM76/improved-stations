/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockModelWrapper.class)
public interface MixinBlockModelWrapper {
    @Accessor("model")
    BakedModel getModel();
}

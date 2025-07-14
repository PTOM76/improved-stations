/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockModelWrapper.class)
public interface MixinBlockModelWrapper {
    @Accessor("properties")
    ModelRenderProperties getProperties();
}

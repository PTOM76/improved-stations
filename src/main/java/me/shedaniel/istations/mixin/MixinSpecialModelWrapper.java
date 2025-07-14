/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpecialModelWrapper.class)
public interface MixinSpecialModelWrapper {
    @Accessor("properties")
    ModelRenderProperties getProperties();
}

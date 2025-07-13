package me.shedaniel.istations.mixin;

import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpecialModelWrapper.class)
public interface MixinSpecialModelWrapper {
    @Accessor("baseModel")
    BakedModel getBaseModel();
}

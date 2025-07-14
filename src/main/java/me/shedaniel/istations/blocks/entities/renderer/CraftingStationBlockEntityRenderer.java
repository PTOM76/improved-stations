/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.shedaniel.istations.blocks.CraftingStationBlock;
import me.shedaniel.istations.blocks.CraftingStationSlabBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.mixin.MixinBlockModelWrapper;
import me.shedaniel.istations.mixin.MixinCompositeModel;
import me.shedaniel.istations.mixin.MixinConditionalItemModel;
import me.shedaniel.istations.mixin.MixinSpecialModelWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class CraftingStationBlockEntityRenderer implements BlockEntityRenderer<CraftingStationBlockEntity> {
    public CraftingStationBlockEntityRenderer(BlockEntityRendererProvider.Context dispatcher) {
    }

    @Override
    public void render(CraftingStationBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 vec3) {
        try {
            int lightAbove = LevelRenderer.getLightColor(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos().above());
            BlockState state = blockEntity.getBlockState();
            if (!(state.getBlock() instanceof CraftingStationBlock)) return;
            Direction o = state.getValue(HorizontalDirectionalBlock.FACING);
            SlabType slabType = (state.getBlock() instanceof CraftingStationSlabBlock && state.hasProperty(SlabBlock.TYPE)) ? state.getValue(SlabBlock.TYPE) : SlabType.DOUBLE;
            for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++) {
                    int slotId = x + y * 3;
                    int newX = x - 1;
                    int newY = y - 1;
                    
                    if (o == Direction.NORTH) {
                        newX *= -1;
                        newY *= -1;
                    } else if (o == Direction.EAST) {
                        int tmp = newY;
                        newY = newX;
                        newX = tmp;
                        newY *= -1;
                    } else if (o == Direction.WEST) {
                        int tmp = newY;
                        newY = newX;
                        newX = tmp;
                        newX *= -1;
                    }
                    
                    ItemStack stack = blockEntity.getItem(slotId);
                    if (stack.isEmpty())
                        continue;

                    ResourceLocation resourceLocation = stack.get(DataComponents.ITEM_MODEL);
                    ItemModel itemModel = Minecraft.getInstance().getModelManager().getItemModel(resourceLocation);

                    matrices.pushPose();
                    if (slabType == SlabType.BOTTOM) {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, .5d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    } else {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, 1d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    }

                    //System.out.println("Rendering item " + "," + ((MixinBlockModelWrapper) itemModel).getQuads().size());

                    if (!is3dGui(itemModel)) {
                        matrices.translate(0, .55 / 16d, -.5d / 16d);
                        matrices.mulPose(Axis.XP.rotationDegrees(90));
                        matrices.scale(.3f, .3f, .3f);
                    } else {
                        matrices.scale(.5f, .5f, .5f);
                    }
                    Minecraft.getInstance().getItemRenderer().renderStatic(
                            stack,
                            ItemDisplayContext.GROUND,
                            lightAbove,
                            OverlayTexture.NO_OVERLAY,
                            matrices,
                            vertexConsumers,
                            blockEntity.getLevel(),
                            slotId
                    );
                    matrices.popPose();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean is3dGui(ItemModel model) {
        if (model instanceof BlockModelWrapper) return ((MixinBlockModelWrapper) model).getProperties().usesBlockLight();
        if (model instanceof SpecialModelWrapper) return ((MixinSpecialModelWrapper) model).getProperties().usesBlockLight();
        if (model instanceof CompositeModel) {
            for (ItemModel subModel : ((MixinCompositeModel) model).getModels()) {
                if (is3dGui(subModel)) return true;
            }
        }
        if (model instanceof ConditionalItemModel) return is3dGui(((MixinConditionalItemModel) model).getOnFalse());

        return false;
    }
}

/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities.renderer;

import me.shedaniel.istations.blocks.CraftingStationSlabBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import java.util.Objects;

public class CraftingStationBlockEntityRenderer implements BlockEntityRenderer<CraftingStationBlockEntity> {
    public CraftingStationBlockEntityRenderer(BlockEntityRendererFactory.Context dispatcher) {
    }
    
    @Override
    public void render(CraftingStationBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        try {
            int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(blockEntity.getWorld()), blockEntity.getPos().up());
            BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
            Direction o = state.get(HorizontalFacingBlock.FACING);
            SlabType slabType = (state.getBlock() instanceof CraftingStationSlabBlock && state.contains(SlabBlock.TYPE)) ? state.get(SlabBlock.TYPE) : SlabType.DOUBLE;
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
                    
                    ItemStack stack = blockEntity.getInvStack(slotId);
                    if (stack.isEmpty())
                        continue;
                    BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(stack, null, null, 0);
                    matrices.push();
                    if (slabType == SlabType.BOTTOM) {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, .5d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    } else {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, 1d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    }
                    if (!bakedModel.hasDepth()) {
                        matrices.translate(0, .55 / 16d, -.5d / 16d);
                        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
                        matrices.scale(.3f, .3f, .3f);
                    } else {
                        matrices.scale(.5f, .5f, .5f);
                    }
                    MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, lightAbove, OverlayTexture.DEFAULT_UV, bakedModel);
                    matrices.pop();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

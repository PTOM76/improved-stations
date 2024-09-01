/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.JukeboxSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecordItem.class)
public class MixinRecordItem {
    @Inject(method = "useOn", at = @At("RETURN"), cancellable = true)
    public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> callback) {
        if (callback.getReturnValue().equals(InteractionResult.PASS)) {
            Level world = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() == ImprovedStations.JUKEBOX_SLAB && !blockState.getValue(JukeboxSlabBlock.HAS_RECORD)) {
                ItemStack itemStack = context.getItemInHand();
                if (!world.isClientSide) {
                    Player playerEntity = context.getPlayer();
                    BlockEntity blockEntity = world.getBlockEntity(blockPos);
                    if (blockEntity instanceof JukeboxBlockEntity) {
                        JukeboxBlockEntity jukeboxBlockEntity = (JukeboxBlockEntity) blockEntity;
                        jukeboxBlockEntity.setFirstItem(itemStack.copy());
                        world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState));
                    }

                    itemStack.shrink(1);
                    if (playerEntity != null) {
                        playerEntity.awardStat(Stats.PLAY_RECORD);
                    }
                }
                callback.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}

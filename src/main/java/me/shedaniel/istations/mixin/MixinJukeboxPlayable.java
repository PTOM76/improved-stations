package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxPlayable.class)
public class MixinJukeboxPlayable {

    @Inject(method = "tryInsertIntoJukebox", at = @At("RETURN"), cancellable = true)
    private static void tryInsertIntoJukebox(Level level, BlockPos blockPos, ItemStack itemStack, Player player, CallbackInfoReturnable<ItemInteractionResult> cir) {
        JukeboxPlayable jukeboxPlayable = itemStack.get(DataComponents.JUKEBOX_PLAYABLE);
        if (jukeboxPlayable != null) {
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(ImprovedStations.JUKEBOX_SLAB) && !(Boolean) blockState.getValue(JukeboxBlock.HAS_RECORD)) {
                if (!level.isClientSide) {
                    ItemStack itemStack2 = itemStack.consumeAndReturn(1, player);
                    BlockEntity var8 = level.getBlockEntity(blockPos);
                    if (var8 instanceof JukeboxBlockEntity) {
                        JukeboxBlockEntity jukeboxBlockEntity = (JukeboxBlockEntity) var8;
                        jukeboxBlockEntity.setTheItem(itemStack2);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState));
                    }

                    player.awardStat(Stats.PLAY_RECORD);
                }

                cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}

/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public record ExtendedScreenHandlerFactoryWrapped(
        MenuProvider factory,
        Function<ServerPlayer, Object> dataWriter
) implements ExtendedScreenHandlerFactory {
    @Override
    public Object getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return dataWriter.apply(serverPlayerEntity);
    }
    
    @Override
    public Component getDisplayName() {
        return factory.getDisplayName();
    }
    
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return factory.createMenu(syncId, inv, player);
    }
}

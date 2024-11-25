/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.CraftingStationBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CraftingStationMenu extends RecipeBookMenu {
    private final ResultContainer resultInv;
    private final ContainerLevelAccess access;
    private final CraftingStationBlockEntity entity;
    private final CraftingContainer craftingInventory;
    private final Player player;
    private boolean placingRecipe;
    
    public CraftingStationMenu(int syncId, Inventory playerInventory, CraftingStationBlockEntity entity, ContainerLevelAccess access) {
        super(ImprovedStations.CRAFTING_STATION_TYPE, syncId);
        this.resultInv = new ResultContainer();
        this.player = playerInventory.player;
        this.craftingInventory = new TransientCraftingContainer(this, 3, 3) {
            @Override
            public int getContainerSize() {
                return entity.getContainerSize();
            }
            
            @Override
            public boolean isEmpty() {
                return entity.isEmpty();
            }
            
            @Override
            public ItemStack getItem(int slot) {
                return entity.getItem(slot);
            }
            
            @Override
            public ItemStack removeItemNoUpdate(int slot) {
                return entity.removeItemNoUpdate(slot);
            }
            
            @Override
            public ItemStack removeItem(int slot, int amount) {
                ItemStack stack = entity.removeItem(slot, amount);
                if (!stack.isEmpty()) {
                    slotsChanged(craftingInventory);
                }
                return stack;
            }

            @Override
            public List<ItemStack> getItems() {
                return entity.getItems();
            }

            @Override
            public void setItem(int slot, ItemStack stack) {
                entity.setItem(slot, stack);
                slotsChanged(craftingInventory);
            }
            
            @Override
            public void setChanged() {
                entity.markDirty();
            }
            
            @Override
            public boolean stillValid(Player player) {
                return entity.stillValid(player);
            }
            
            @Override
            public void clearContent() {
                entity.clearContent();
            }

            @Override
            public void fillStackedContents(StackedItemContents recipeFinder) {
                entity.fillStackedContents(recipeFinder);
            }
        };
        this.access = access;
        this.entity = entity;
        this.addSlot(new ResultSlot(playerInventory.player, craftingInventory, this.resultInv, 0, 124, 35));
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(craftingInventory, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }
        
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public void beginPlacingRecipe() {
        this.placingRecipe = true;
    }

    public void finishPlacingRecipe(ServerLevel level, RecipeHolder<CraftingRecipe> recipeHolder) {
        this.placingRecipe = false;
        updateResult(this, level, this.player, this.craftingInventory, this.resultInv, recipeHolder);
    }

    /*
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.craftingInventory));
    }
    */

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
        this.craftingInventory.fillStackedContents(stackedContents);
    }

    @Override
    protected void clearContainer(Player player, Container container) {
        super.clearContainer(player, container);
    }

    @Override
    public PostPlaceAction handlePlacement(boolean bl, boolean bl2, RecipeHolder<?> recipeHolder, ServerLevel serverLevel, Inventory inventory) {
        RecipeHolder<CraftingRecipe> recipeHolder2 = (RecipeHolder<CraftingRecipe>) recipeHolder;
        this.beginPlacingRecipe();

        RecipeBookMenu.PostPlaceAction var8;
        try {
            List<Slot> list = this.getInputGridSlots();
            var8 = ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
                public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
                    CraftingStationMenu.this.fillCraftSlotsStackedContents(stackedItemContents);
                }

                public void clearCraftingContent() {
                    CraftingStationMenu.this.craftingInventory.clearContent();
                    CraftingStationMenu.this.resultInv.clearContent();
                }

                public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipeHolder) {
                    return (recipeHolder.value()).matches(CraftingStationMenu.this.craftingInventory.asCraftInput(), CraftingStationMenu.this.player.level());
                }
            }, 3, 3, list, list, inventory, recipeHolder2, bl, bl2);
        } finally {
            this.finishPlacingRecipe(serverLevel, recipeHolder2);
        }

        return var8;
    }

    @Override
    public void broadcastChanges() {
        if (player.level() instanceof ServerLevel) {
            if (!this.placingRecipe) {
                updateResult(this, (ServerLevel) player.level(), player, craftingInventory, resultInv, null);
            }
        }
        super.broadcastChanges();
    }

    protected static void updateResult(AbstractContainerMenu menu, ServerLevel level, Player player, CraftingContainer craftingInventory, ResultContainer resultInventory, @Nullable RecipeHolder<CraftingRecipe> recipeHolder) {
        CraftingInput craftingInput = craftingInventory.asCraftInput();
        ServerPlayer serverPlayer = (ServerPlayer)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInput, level, recipeHolder);
        if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeHolder2 = optional.get();
            CraftingRecipe craftingRecipe = recipeHolder2.value();
            if (resultInventory.setRecipeUsed(serverPlayer, recipeHolder2)) {
                ItemStack itemStack2 = craftingRecipe.assemble(craftingInput, level.registryAccess());
                if (itemStack2.isItemEnabled(level.enabledFeatures())) {
                    itemStack = itemStack2;
                }
            }
        }
        resultInventory.setItem(0, itemStack);
        menu.setRemoteSlot(0, itemStack);
        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
    }
    
    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if (!this.placingRecipe) {
            this.access.execute((level, blockPos) -> {
                if (level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel)level;
                    updateResult(this, serverLevel, this.player, this.craftingInventory, this.resultInv, null);
                }
            });
        }

        if (!player.level().isClientSide) {
            entity.markDirty();
        }
    }
    
    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate((world, blockPos) -> world.getBlockState(blockPos).getBlock() instanceof CraftingStationBlock && player.canInteractWithBlock(blockPos, 4.0), true);
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == 0) {
                this.access.execute((world, blockPos) -> {
                    itemStack2.getItem().onCraftedBy(itemStack2, world, player);
                });
                if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (invSlot >= 10 && invSlot < 46) {
                if (!this.moveItemStackTo(itemStack2, 1, 10, false)) {
                    if (invSlot < 37) {
                        if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            slot.onTake(player, itemStack2);
            if (invSlot == 0) {
                player.drop(itemStack2, false);
            }
        }
        
        return itemStack;
    }
    
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultInv && super.canTakeItemForPickAll(stack, slot);
    }

    public Slot getResultSlot() {
        return this.slots.get(0);
    }

    public List<Slot> getInputGridSlots() {
        return this.slots.subList(1, 10);
    }

    public int getGridWidth() {
        return 3;
    }

    public int getGridHeight() {
        return 3;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }
}

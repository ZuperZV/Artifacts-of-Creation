package net.zuperzv.artifacts_of_creation.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.block.custom.NotDirtBlock;
import net.zuperzv.artifacts_of_creation.block.entity.ItemHandler.CustomItemHandler;
import net.zuperzv.artifacts_of_creation.block.entity.ModBlockEntities;
import net.zuperzv.artifacts_of_creation.recipes.ModRecipes;
import net.zuperzv.artifacts_of_creation.recipes.RightClickRecipe;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class NotDirtBlockEntity extends BlockEntity {
    private long lastClickTime = 0;
    private static final long COOLDOWN_TIME = 800;

    private final ItemStackHandler inputItems = createItemHandler(1);
    private final ItemStackHandler outputItems = createItemHandler(1);

    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> new CustomItemHandler(inputItems));
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> new CustomItemHandler(outputItems));

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{4};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0, 1, 2, 3};


    public NotDirtBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NOT_DIRT_BLOCK_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NotDirtBlockEntity blockEntity) {
        boolean dirty = false;
        if (level == null) return;

        if (level.random.nextFloat() < 0.03f) {
            blockEntity.getInputItems().setStackInSlot(0, new ItemStack(ModBlocks.NOT_DIRT));
        dirty = true;
        }

        if (dirty) {
            blockEntity.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        }
    }

    public void onRightClick(Player player, Level level, BlockPos pos) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime >= COOLDOWN_TIME) {
            lastClickTime = currentTime;

            if (level.getBlockState(pos).getBlock() instanceof NotDirtBlock) {
                if (hasRecipe()) {
                    craftItem(this);
                    player.hurt(player.damageSources().generic(), 1);

                    if (level.random.nextFloat() < 0.4f) {
                        level.setBlock(pos, ModBlocks.NOT_FARMLAND.get().defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private boolean hasRecipe() {
        Level level = this.level;
        if (level == null) return false;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<RightClickRecipe>> alcheRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.RIGHT_CLICK_RECIPE_TYPE.get(), getRecipeInput(inventory), level);


        return (alcheRecipe.isPresent() && canInsertAmountIntoOutputSlot(inventory)) ;
    }

    private RecipeInput getRecipeInput(SimpleContainer inventory) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return inventory.getItem(index).copy();
            }

            @Override
            public int size() {
                return inventory.getContainerSize();
            }
        };
    }

    private boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        ItemStack outputStack1 = outputItems.getStackInSlot(0);

        boolean canInsertIntoFirstSlot = outputStack1.isEmpty() || (outputStack1.getCount() < outputStack1.getMaxStackSize());

        return canInsertIntoFirstSlot;
    }

    private void craftItem(NotDirtBlockEntity serverLevel) {
        Level level = this.level;
        if (level == null) return;

        SimpleContainer inventory = new SimpleContainer(inputItems.getSlots());
        for (int i = 0; i < inputItems.getSlots(); i++) {
            inventory.setItem(i, inputItems.getStackInSlot(i));
        }

        Optional<RecipeHolder<RightClickRecipe>> alcheRecipeOptional = level.getRecipeManager()
                .getRecipeFor(ModRecipes.RIGHT_CLICK_RECIPE_TYPE.get(), getRecipeInput(inventory), level);

        if (alcheRecipeOptional.isPresent()) {
            RightClickRecipe recipe = alcheRecipeOptional.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());

            ItemStack outputStack = outputItems.getStackInSlot(0);
            if (outputStack.isEmpty()) {
                outputItems.setStackInSlot(0, result.copy());
            } else if (outputStack.getItem() == result.getItem()) {
                outputStack.grow(result.getCount());
            }

            if (level.random.nextFloat() < 0.5f) {
                dropRecipeItem();
            }
        }
    }

    private boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(null) > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inputItems", inputItems.serializeNBT(registries));
        tag.put("outputItems", outputItems.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inputItems.deserializeNBT(registries, tag.getCompound("inputItems"));
        outputItems.deserializeNBT(registries, tag.getCompound("outputItems"));
    }

    private ItemStackHandler createItemHandler(int slots) {
        return new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void dropRecipeItem() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() == ModBlocks.NOT_DIRT.get().asItem()) {
                inventory.setItem(i, ItemStack.EMPTY);
            }
        }
        Containers.dropContents(level, worldPosition, inventory);
    }


    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    public ItemStackHandler getInputItems() {
        return inputItems;
    }

    private void markForUpdate() {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}

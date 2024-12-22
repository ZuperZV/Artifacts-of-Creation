package net.zuperzv.artifacts_of_creation.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ArtifactsOfCreation.MOD_ID);

    public static final DeferredItem<Item> TEMPORAL_SEED = ITEMS.register("temporal_seed",
            () -> new ItemNameBlockItem(ModBlocks.ROOTED_CROP.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
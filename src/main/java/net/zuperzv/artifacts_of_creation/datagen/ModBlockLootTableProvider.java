package net.zuperzv.artifacts_of_creation.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.block.custom.RootedCropBlock;
import net.zuperzv.artifacts_of_creation.item.ModItems;

import java.util.Set;
import java.util.function.Function;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.NOT_DIRT.get());
        this.dropSelf(ModBlocks.NOT_FARMLAND.get());

        this.dropSelf(ModBlocks.RIFT.get());

        this.dropSelf(ModBlocks.ROOTED_LOG.get());
        this.dropSelf(ModBlocks.ROOTED_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ROOTED_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ROOTED_WOOD.get());

        this.dropSelf(ModBlocks.ROOTED_PLANKS.get());
        this.dropSelf(ModBlocks.ROOTED_SAPLING.get());

        this.add(ModBlocks.ROOTED_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ROOTED_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));


        LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.ROOTED_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RootedCropBlock.AGE, 3));
        this.add(ModBlocks.ROOTED_CROP.get(), this.createCropDrops(ModBlocks.ROOTED_CROP.get(),
                Item.byBlock(ModBlocks.ROOTED_SAPLING.get()), ModItems.TEMPORAL_SEED.asItem(), lootItemConditionBuilder));

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createMultipleItemLogDrops(Block pBlock, Item log, Item item, float minDrops, float maxDrops) {
        LootPool.Builder logPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(log)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))));

        LootPool.Builder itemPool = LootPool.lootPool()
                .add(LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .when(LootItemRandomChanceCondition.randomChance(0.8f)));

        return LootTable.lootTable()
                .withPool(logPool)
                .withPool(itemPool);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
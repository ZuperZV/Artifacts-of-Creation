package net.zuperzv.artifacts_of_creation.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, ArtifactsOfCreation.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROOTED_LOG.get().asItem())
                .add(ModBlocks.ROOTED_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ROOTED_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ROOTED_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.ROOTED_PLANKS.get().asItem());

        tag(ItemTags.SAPLINGS)
        .add(ModBlocks.ROOTED_SAPLING.get().asItem());
    }
}
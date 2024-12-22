package net.zuperzv.artifacts_of_creation.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArtifactsOfCreation.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROOTED_LOG.get())
                .add(ModBlocks.ROOTED_WOOD.get())
                .add(ModBlocks.STRIPPED_ROOTED_LOG.get())
                .add(ModBlocks.STRIPPED_ROOTED_WOOD.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.ROOTED_LOG.get())
                .add(ModBlocks.ROOTED_WOOD.get())
                .add(ModBlocks.STRIPPED_ROOTED_LOG.get())
                .add(ModBlocks.STRIPPED_ROOTED_WOOD.get());

        this.tag(ModTags.Blocks.PLACE_ON_THIS)
                .add(ModBlocks.NOT.get())
                .add(ModBlocks.NOT_DIRT.get())
                .add(ModBlocks.NOT_FARMLAND.get())
                .add(Blocks.DIRT)
                .add(Blocks.FARMLAND);

        this.tag(BlockTags.MAINTAINS_FARMLAND)
                .add(ModBlocks.ROOTED_CROP.get());
    }
}
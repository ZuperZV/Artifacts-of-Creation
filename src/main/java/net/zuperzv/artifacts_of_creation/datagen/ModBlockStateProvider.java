package net.zuperzv.artifacts_of_creation.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.block.custom.RootedCropBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArtifactsOfCreation.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.NOT);
        blockWithItem(ModBlocks.NOT_DIRT);

        logBlock(((RotatedPillarBlock) ModBlocks.ROOTED_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.ROOTED_WOOD.get()), blockTexture(ModBlocks.ROOTED_LOG.get()), blockTexture(ModBlocks.ROOTED_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ROOTED_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ROOTED_WOOD.get()), blockTexture(ModBlocks.STRIPPED_ROOTED_LOG.get()), blockTexture(ModBlocks.STRIPPED_ROOTED_LOG.get()));

        blockItem(ModBlocks.ROOTED_LOG);
        blockItem(ModBlocks.ROOTED_WOOD);
        blockItem(ModBlocks.STRIPPED_ROOTED_LOG);
        blockItem(ModBlocks.STRIPPED_ROOTED_WOOD);

        blockWithItem(ModBlocks.ROOTED_PLANKS);

        leavesBlock(ModBlocks.ROOTED_LEAVES);
        saplingBlock(ModBlocks.ROOTED_SAPLING);

        makeCrop(((RootedCropBlock) ModBlocks.ROOTED_CROP.get()), "rooted_crop_stage","rooted_crop_stage");
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((RootedCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "block/" + textureName +
                        state.getValue(((RootedCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("artifacts_of_creation:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<Block> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("artifacts_of_creation:block/" + deferredBlock.getId().getPath() + appendix));
    }

    private void leavesBlock(DeferredBlock<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(deferredBlock.get())).renderType("cutout"));
    }

    private void saplingBlock(DeferredBlock<Block> deferredBlock) {
        simpleBlock(deferredBlock.get(), models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout"));
    }
}
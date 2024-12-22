package net.zuperzv.artifacts_of_creation.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {

        woodFromLogs(pWriter, ModBlocks.ROOTED_WOOD.get(), ModBlocks.ROOTED_LOG.get());
        woodFromLogs(pWriter, ModBlocks.STRIPPED_ROOTED_WOOD.get(), ModBlocks.STRIPPED_ROOTED_LOG.get());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ROOTED_PLANKS.get(), 2)
                .requires(ModBlocks.ROOTED_LOG.get())
                .unlockedBy("has_rooted_planks", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.ROOTED_LOG.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "rooted_planks_from_log"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ROOTED_PLANKS.get(), 2)
                .requires(ModBlocks.ROOTED_WOOD.get())
                .unlockedBy("has_rooted_planks", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.ROOTED_WOOD.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "rooted_planks_from_wood"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ROOTED_PLANKS.get(), 3)
                .requires(ModBlocks.STRIPPED_ROOTED_LOG.get())
                .unlockedBy("has_rooted_planks", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.STRIPPED_ROOTED_LOG.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "stripped_rooted_planks_from_log"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ROOTED_PLANKS.get(), 3)
                .requires(ModBlocks.STRIPPED_ROOTED_WOOD.get())
                .unlockedBy("has_rooted_planks", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.STRIPPED_ROOTED_WOOD.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "stripped_rooted_planks_from_wood"));
    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, ArtifactsOfCreation.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
package net.zuperzv.artifacts_of_creation.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.worldgen.biome.RegisterBiomes;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_ROOTED = registerKey("add_tree_rooted");
    public static final ResourceKey<BiomeModifier> ADD_DIRT  = registerKey("add_dirt");



    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        ResourceKey<Biome> woodZoneBiomeKey = ResourceKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "wood-zone"));

        ResourceKey<Biome> timeZoneBiomeKey = ResourceKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "time-zone"));

        context.register(ADD_TREE_ROOTED, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(woodZoneBiomeKey)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ROOTED_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }



    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, name));
    }
}
package net.zuperzv.artifacts_of_creation.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;

public class RegisterBiomes {
    public static final ResourceKey<Biome> TIME_ZONE = register("time-zone");

    private static ResourceKey<Biome> register(String p_48229_) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, p_48229_));
    }
}

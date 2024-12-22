package net.zuperzv.artifacts_of_creation.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower ROOTED = new TreeGrower(ArtifactsOfCreation.MOD_ID + ":rooted",
            Optional.empty(), Optional.of(ModConfiguredFeatures.ROOTED_KEY), Optional.empty());
}
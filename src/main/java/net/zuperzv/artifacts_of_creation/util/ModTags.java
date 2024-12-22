package net.zuperzv.artifacts_of_creation.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;

public class ModTags {

    public static class Items {

        //public static final TagKey<Item> SOUL_SHARD = tag("soul_shard");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, name));
        }

        private static TagKey<Item> moddedTag(String modid, String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> PLACE_ON_THIS = tag("place_on_this");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
    }

    public static class Entities {


        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, name));
        }
    }
}

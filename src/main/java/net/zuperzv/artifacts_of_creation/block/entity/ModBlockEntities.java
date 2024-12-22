package net.zuperzv.artifacts_of_creation.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.block.entity.custom.NotDirtBlockEntity;
import net.zuperzv.artifacts_of_creation.block.entity.custom.RiftBlockEntity;

import java.util.function.Supplier;


public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ArtifactsOfCreation.MOD_ID);

    public static final Supplier<BlockEntityType<RiftBlockEntity>> RIFT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("rift_be", () -> BlockEntityType.Builder.of(
                    RiftBlockEntity::new, ModBlocks.RIFT.get()).build(null));

    public static final Supplier<BlockEntityType<NotDirtBlockEntity>> NOT_DIRT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("not_dirt_be", () -> BlockEntityType.Builder.of(
                    NotDirtBlockEntity::new, ModBlocks.NOT_DIRT.get()).build(null));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
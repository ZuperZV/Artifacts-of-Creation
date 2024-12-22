package net.zuperzv.artifacts_of_creation.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArtifactsOfCreation.MOD_ID);

    public static final Supplier<CreativeModeTab> ARTIFACTS_OF_CREATION_TAB =
            CREATIVE_MODE_TABS.register("artifacts_of_creation_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.artifacts_of_creation.artifacts_of_creation_tab"))
                    .icon(() -> new ItemStack(ModBlocks.NOT.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.NOT.get());
                        pOutput.accept(ModBlocks.NOT_DIRT.get());
                        pOutput.accept(ModBlocks.NOT_FARMLAND.get());

                        pOutput.accept(ModItems.TEMPORAL_SEED.get());
                        pOutput.accept(ModBlocks.ROOTED_SAPLING.get());
                        pOutput.accept(ModBlocks.ROOTED_LOG.get());
                        pOutput.accept(ModBlocks.ROOTED_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ROOTED_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ROOTED_WOOD.get());
                        pOutput.accept(ModBlocks.ROOTED_PLANKS.get());
                        pOutput.accept(ModBlocks.ROOTED_LEAVES.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

package net.zuperzv.artifacts_of_creation.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.ModBlocks;
import net.zuperzv.artifacts_of_creation.block.custom.NotFarmBlock;

import static net.minecraft.world.level.block.Block.pushEntitiesUp;

@EventBusSubscriber(modid = ArtifactsOfCreation.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);

        if (blockState.is(ModBlocks.NOT_FARMLAND)) {

            event.setCanceled(true);

            BlockState newBlockState = ModBlocks.NOT_DIRT.get().defaultBlockState();
            BlockState updatedBlockState = pushEntitiesUp(blockState, newBlockState, level, pos);

            level.setBlockAndUpdate(pos, updatedBlockState);
        }
    }

    @SubscribeEvent
    public static void onNotDirtClickedWithHoe(BlockEvent.BlockToolModificationEvent event) {
        if (event.isCanceled() || ! event.getItemAbility().equals(ItemAbilities.HOE_TILL))
            return;
        BlockState newBlock = ModBlocks.NOT_FARMLAND.get().defaultBlockState();
        BlockPos pos = event.getPos();
        LevelAccessor world = event.getContext().getLevel();
        BlockState dirt = world.getBlockState(event.getContext().getClickedPos());

        if (event.getItemAbility() == ItemAbilities.HOE_TILL && (dirt.is(ModBlocks.NOT_DIRT.get()))) {
            if (newBlock.canSurvive(world, pos)) {
                event.setFinalState(ModBlocks.NOT_FARMLAND.get().defaultBlockState());
            }
        }
    }
}
package net.zuperzv.artifacts_of_creation.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.zuperzv.artifacts_of_creation.block.entity.custom.NotDirtBlockEntity;

import javax.annotation.Nullable;

public class NotDirtBlock extends Block implements EntityBlock {

    public NotDirtBlock(Properties properties) {
        super(Properties.of());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack p_316304_, BlockState p_316362_, Level level, BlockPos pos, Player player, InteractionHand p_316595_, BlockHitResult p_316140_) {
        if (player.getMainHandItem().isEmpty()) {
            if (level.getBlockEntity(pos) instanceof NotDirtBlockEntity blockEntity) {
                blockEntity.onRightClick(player, level, pos);
            }
        }
        return super.useItemOn(p_316304_, p_316362_, level, pos, player, p_316595_, p_316140_);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_60503_, Level level, BlockPos pos, Player player, BlockHitResult p_60508_) {
        if (player.getMainHandItem().isEmpty()) {
            if (level.getBlockEntity(pos) instanceof NotDirtBlockEntity blockEntity) {
                blockEntity.onRightClick(player, level, pos);
            }
        }
        return super.useWithoutItem(p_60503_, level, pos, player, p_60508_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NotDirtBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof NotDirtBlockEntity blockEntityTick) {
                blockEntityTick.tick(level, pos, st, blockEntityTick);
            }
        };
    }
}

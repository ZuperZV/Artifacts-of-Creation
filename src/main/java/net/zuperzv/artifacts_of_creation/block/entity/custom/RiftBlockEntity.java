package net.zuperzv.artifacts_of_creation.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.zuperzv.artifacts_of_creation.block.entity.ModBlockEntities;

public class RiftBlockEntity extends BlockEntity {
    protected RiftBlockEntity(BlockEntityType<?> p_155855_, BlockPos p_155856_, BlockState p_155857_) {
        super(p_155855_, p_155856_, p_155857_);
    }

    public RiftBlockEntity(BlockPos p_155859_, BlockState p_155860_) {
        this(ModBlockEntities.RIFT_BLOCK_ENTITY.get(), p_155859_, p_155860_);
    }

    public boolean shouldRenderFace(Direction p_59980_) {
        return p_59980_.getAxis() == Direction.Axis.Y;
    }
}

package net.zuperzv.artifacts_of_creation.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.EndPlatformFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zuperzv.artifacts_of_creation.ArtifactsOfCreation;
import net.zuperzv.artifacts_of_creation.block.entity.custom.RiftBlockEntity;

public class RiftBlock extends BaseEntityBlock implements Portal {
    public static final MapCodec<RiftBlock> CODEC = simpleCodec(RiftBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);
    public static final ResourceKey<Level> FLAT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "flat"));
    public static final ResourceKey<Level> WOOD = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(ArtifactsOfCreation.MOD_ID, "wood"));

    @Override
    public MapCodec<RiftBlock> codec() {
        return CODEC;
    }

    public RiftBlock(BlockBehaviour.Properties p_53017_) {
        super(p_53017_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_153196_, BlockState p_153197_) {
        return new RiftBlockEntity(p_153196_, p_153197_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_53038_, BlockGetter p_53039_, BlockPos p_53040_, CollisionContext p_53041_) {
        return SHAPE;
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!level.isClientSide) {
                ResourceKey<Level> currentDimension = level.dimension();
                ResourceKey<Level> targetDimension = currentDimension == FLAT ? WOOD : FLAT;

                if (currentDimension == targetDimension) {
                    serverPlayer.sendSystemMessage(Component.literal("You are already in the correct dimension!"));
                    return;
                }
                teleportToDimension(serverPlayer, targetDimension, new BlockPos(0, 100, 0));
                scheduleReturnToDimension((ServerLevel) level, serverPlayer, currentDimension, new BlockPos(0, 100, 0), 50);
            }
        }
    }

    private void teleportToDimension(ServerPlayer player, ResourceKey<Level> dimensionKey, BlockPos targetPos) {
        ServerLevel targetLevel = player.getServer().getLevel(dimensionKey);

        if (targetLevel != null && player.level().dimension() != dimensionKey) {
            player.teleportTo(targetLevel, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, player.getYRot(), player.getXRot());
        }
    }

    private void scheduleReturnToDimension(ServerLevel currentLevel, ServerPlayer player, ResourceKey<Level> originalDimension, BlockPos returnPos, int delayTicks) {
        currentLevel.getServer().execute(() -> {
            currentLevel.getServer().tell(new TickTask(currentLevel.getServer().getTickCount() + delayTicks, () -> {
                ServerLevel targetLevel = currentLevel.getServer().getLevel(originalDimension);

                if (targetLevel != null && player.isAlive() && player.level().dimension() == originalDimension) {
                    teleportToDimension(player, originalDimension, returnPos);
                }
            }));
        });
    }

    @Override
    public DimensionTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalPos) {
        ResourceKey<Level> targetDimension = currentLevel.dimension() == FLAT ? WOOD : FLAT;
        ServerLevel targetLevel = currentLevel.getServer().getLevel(targetDimension);

        if (targetLevel == null) {
            return null;
        }

        boolean isReturningToFlat = targetDimension == FLAT;
        BlockPos targetPos = isReturningToFlat ? ServerLevel.END_SPAWN_POINT : targetLevel.getSharedSpawnPos();
        Vec3 targetVec = new Vec3(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

        float yaw = entity.getYRot();
        if (isReturningToFlat) {
            yaw = Direction.WEST.toYRot();
            if (entity instanceof ServerPlayer) {
                targetVec = targetVec.subtract(0.0, 1.0, 0.0);
            }
        }

        return new DimensionTransition(
                targetLevel,
                targetVec,
                Vec3.ZERO,
                yaw,
                entity.getXRot(),
                DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
        );
    }

    @Override
    public void animateTick(BlockState p_221102_, Level p_221103_, BlockPos p_221104_, RandomSource p_221105_) {
        double d0 = (double)p_221104_.getX() + p_221105_.nextDouble();
        double d1 = (double)p_221104_.getY() + 0.8;
        double d2 = (double)p_221104_.getZ() + p_221105_.nextDouble();
        p_221103_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader p_304508_, BlockPos p_53022_, BlockState p_53023_) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState p_53035_, Fluid p_53036_) {
        return false;
    }
}

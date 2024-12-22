package net.zuperzv.artifacts_of_creation.block.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class CustomTeleporter {
    public static void teleportPlayer(ServerPlayer player, ServerLevel targetLevel, BlockPos targetPos) {
        player.teleportTo(targetLevel,
                targetPos.getX() + 0.5,
                targetPos.getY(),
                targetPos.getZ() + 0.5,
                player.getYRot(),
                player.getXRot());
    }
}

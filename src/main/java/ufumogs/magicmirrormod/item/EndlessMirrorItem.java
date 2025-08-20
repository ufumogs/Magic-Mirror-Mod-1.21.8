package ufumogs.magicmirrormod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class EndlessMirrorItem extends Item {
    public EndlessMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return ActionResult.SUCCESS;

        ServerPlayerEntity player = (ServerPlayerEntity) user;
        ServerWorld sw = (ServerWorld) world;

        // Try bed/anchor first
        TeleportTarget target = player.getRespawnTarget(true, TeleportTarget.NO_OP);
        if (target != null && !target.missingRespawnBlock()) {
            player.teleportTo(target);
        } else {
            BlockPos spawn = sw.getSpawnPos();
            BlockPos safe = sw.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawn);
            Vec3d dest = new Vec3d(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);

            TeleportTarget worldSpawnTarget = new TeleportTarget(
                    sw, dest, Vec3d.ZERO, player.getYaw(), player.getPitch(), TeleportTarget.NO_OP
            );
            player.teleportTo(worldSpawnTarget);
        }

        sw.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,   // the sound
                SoundCategory.PLAYERS,                  // category
                1.0F,                                   // volume
                1.0F                                    // pitch
        );

        // No durability, infinite uses.
        return ActionResult.SUCCESS;
    }
}

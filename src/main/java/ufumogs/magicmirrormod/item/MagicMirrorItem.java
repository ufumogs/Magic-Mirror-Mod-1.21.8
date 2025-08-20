package ufumogs.magicmirrormod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
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

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return ActionResult.SUCCESS;

        user.setCurrentHand(hand); // start channeling
        return ActionResult.CONSUME; // keep using until finished
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 30; // 1 second
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void usageTick(World world, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        if (world.isClient) return;
        if (!(entity instanceof ServerPlayerEntity player)) return;

        if (remainingUseTicks == 1) {
            ServerWorld sw = (ServerWorld) world;

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

            sw.playSound(null, player.getBlockPos(),
                    SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
                    SoundCategory.PLAYERS, 2.0f, 1.0f);

            if (!player.getAbilities().creativeMode) {
                stack.damage(1, player, player.getMainHandStack() == stack ? Hand.MAIN_HAND : Hand.OFF_HAND);
            }

            player.stopUsingItem(); // stop so it won't fire again
        }
    }
}

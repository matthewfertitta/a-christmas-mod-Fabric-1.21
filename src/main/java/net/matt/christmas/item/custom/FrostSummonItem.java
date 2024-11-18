package net.matt.christmas.item.custom;

import net.matt.christmas.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.Map;

public class FrostSummonItem extends Item {

    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.OAK_LOG, ModBlocks.PEPPERMINT_BLOCK,
                    Blocks.GOLD_BLOCK, Blocks.NETHERITE_BLOCK
            );

    public FrostSummonItem(Settings settings) {
        super(settings);
    }

    /*
    // shows how blocks and server stuff do thingies
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // get world & block that we used it on
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock))
        {
            // server side
            if(!world.isClient())
            {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.BLOCKS);
            }
        }

        return ActionResult.SUCCESS;
    }
    */

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient())
        {
            // summon mobs in radius
            summonMobs((ServerWorld) world, user.getBlockPos(), 5, 10);

            user.getStackInHand(hand).damage(1, ((ServerWorld) world), ((ServerPlayerEntity) user),
                    item -> user.sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 3 ,1);
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    private void summonMobs(ServerWorld world, BlockPos center, int minMobs, int maxMobs)
    {
        // random num gen
        int mobCount = minMobs + world.random.nextInt(maxMobs - minMobs + 1);
        for (int i = 0; i < mobCount; i++) {
            // mobs to spawn
            EntityType<?>[] mobTypes = {EntityType.SNOW_GOLEM, EntityType.STRAY};
            EntityType<?> randomMob = mobTypes[world.random.nextInt(mobTypes.length)];
            MobEntity mob = (MobEntity) randomMob.create(world);

            if (mob != null) {
                int attempts = 10; // Number of attempts to find a valid spawn position
                while (attempts-- > 0) {
                    BlockPos spawnPos = center.add(
                            world.random.nextInt(10) - 5,
                            0, // Spawn at ground level relative to the center
                            world.random.nextInt(10) - 5
                    );

                    // snap to nearest block on ground
                    spawnPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPos);

                    mob.refreshPositionAndAngles(
                            spawnPos.getX() + 0.5,
                            spawnPos.getY(),
                            spawnPos.getZ() + 0.5,
                            world.random.nextFloat() * 360, 0
                    );

                    // Check if the mob can spawn at this position
                    if (canSpawnMob(world, spawnPos, mob)) {
                        world.spawnEntity(mob);

                        // Add smoke particles at the spawn position
                        for (int j = 0; j < 20; j++) {
                            double offsetX = world.random.nextGaussian() * 0.2;
                            double offsetY = world.random.nextGaussian() * 0.2;
                            double offsetZ = world.random.nextGaussian() * 0.2;

                            world.spawnParticles(
                                    ParticleTypes.SMOKE,  // Type of particle
                                    spawnPos.getX() + 0.5, // Centered on X
                                    spawnPos.getY() + 0.5, // Slightly above ground
                                    spawnPos.getZ() + 0.5, // Centered on Z
                                    2, // Number of particles per iteration
                                    offsetX, offsetY, offsetZ, // Spread/motion
                                    0.1
                            );
                        }

                        break; // Successfully spawned the mob
                    }
                }
            }
        }
    }

    private boolean canSpawnMob(ServerWorld world, BlockPos pos, MobEntity mob) {
        // check if the block at the position is solid
        if (!world.getBlockState(pos).isAir() && !world.getBlockState(pos).getFluidState().isEmpty()) {
            return false; // not a valid spawn position
        }

        // check for collisions with other entities or blocks
        return world.isSpaceEmpty(mob.getBoundingBox());
    }
}

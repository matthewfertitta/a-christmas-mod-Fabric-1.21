package net.matt.christmas.item.custom;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.matt.christmas.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FrostSummonItem extends Item {

    /*
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.OAK_LOG, ModBlocks.PEPPERMINT_BLOCK,
                    Blocks.GOLD_BLOCK, Blocks.NETHERITE_BLOCK
            );
     */

    // mobs to spawn
    private List<EntityType<?>> mobTypes = new ArrayList<>(List.of(EntityType.STRAY, EntityType.BREEZE));

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

        user.getStackInHand(hand).decrement(1);

        if(!world.isClient())
        {

            // start new thread
            new Thread(() -> {

                // reset list
                mobTypes = new ArrayList<>(List.of(EntityType.STRAY, EntityType.BREEZE));
                int maxMobs = 5;

                for(int i = 1; i <= 3; i++)
                {

                    // add illusioner at wave 3
                    if(i == 3)
                    {
                        mobTypes.add(EntityType.ILLUSIONER);
                    }

                    user.sendMessage(Text.of("Wave " + i), true);
                    world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EVOKER_PREPARE_WOLOLO, SoundCategory.BLOCKS, 3, 1);
                    // summon mobs in radius
                    summonMobs((ServerWorld) world, user.getBlockPos(), 5, maxMobs);
                    maxMobs += 3;

                    // wait 15 sec
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }).start();
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    private void summonMobs(ServerWorld world, BlockPos center, int minMobs, int maxMobs)
    {
        // random num gen
        int mobCount = minMobs + world.random.nextInt(maxMobs - minMobs + 1);
        for (int i = 0; i < mobCount; i++) {
            EntityType<?> randomMob = mobTypes.get(world.random.nextInt(mobTypes.size()));
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

                        // give bows to strays
                        if(mob.getType() == EntityType.STRAY)
                        {
                            world.spawnEntity(mob);
                            mob.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                            mob.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
                        }
                        else
                        {
                            world.spawnEntity(mob);
                        }

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
                                    5, // Number of particles per iteration
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

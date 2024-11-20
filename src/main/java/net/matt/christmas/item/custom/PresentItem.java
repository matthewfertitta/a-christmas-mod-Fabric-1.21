package net.matt.christmas.item.custom;

import net.matt.christmas.item.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class PresentItem extends Item {

    // Constructor to take in the loot table identifier
    public PresentItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        stack.decrement(1);

        // I hate lootTables so much so we ball with this for now
        if (!world.isClient) {
            List<Item> drops = List.of(
                    Items.DIAMOND,
                    Items.LEATHER,
                    Items.STRING,
                    Items.BONE,
                    ModItems.CANDY_CANE,
                    ModItems.EGGNOG
            );
            Random random = world.getRandom();

            int getDrop = MathHelper.nextInt(random, 0, drops.size());
            int howManyDrop = MathHelper.nextInt(random, 0, 3);

            ItemStack rewardStack = new ItemStack(drops.get(getDrop), howManyDrop);
            if (world instanceof ServerWorld serverWorld)
            {
                BlockPos playerPos = player.getBlockPos();
                ItemEntity itemEntity = new ItemEntity(serverWorld, playerPos.getX(), playerPos.getY(), playerPos.getZ(), rewardStack);

                itemEntity.setVelocity(0, 0, 0);

                serverWorld.spawnEntity(itemEntity);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}

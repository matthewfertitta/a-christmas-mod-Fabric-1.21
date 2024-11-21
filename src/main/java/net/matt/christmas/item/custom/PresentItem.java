package net.matt.christmas.item.custom;

import net.matt.christmas.item.ModItems;
import net.matt.christmas.util.GetRandomItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Map;

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

            Map<Item, Double> drops = Map.of(
                    ModItems.CANDY_CANE, 0.20,
                    ModItems.EGGNOG, 0.15,
                    Items.BONE, 0.14,
                    Items.ROTTEN_FLESH, 0.15,
                    Items.DIAMOND, 0.01,
                    Items.COOKIE, 0.20,
                    Items.MILK_BUCKET, 0.15
            );

            GetRandomItem itemGen = new GetRandomItem(drops, world);

            Random random = world.getRandom();
            // bonus roll
            int bonus = 0;
            if(player.hasStatusEffect(StatusEffects.LUCK)) { bonus++; }

            // main roll
            for(int i = 0; i < MathHelper.nextInt(random, 1, 2 + bonus); i++)
            {
                int howManyDrop = MathHelper.nextInt(random, 0, 3);
                ItemStack rewardStack = new ItemStack((ItemConvertible) itemGen.getRandomDrop(), howManyDrop);

                if (world instanceof ServerWorld serverWorld)
                {
                    BlockPos playerPos = player.getBlockPos();
                    ItemEntity itemEntity = new ItemEntity(serverWorld, playerPos.getX(), playerPos.getY(), playerPos.getZ(), rewardStack);

                    itemEntity.setVelocity(0, 0, 0);

                    serverWorld.spawnEntity(itemEntity);
                }
            }

        }

        return TypedActionResult.success(stack, world.isClient());
    }
}

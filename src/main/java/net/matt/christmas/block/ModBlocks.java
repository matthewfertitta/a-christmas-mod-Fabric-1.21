package net.matt.christmas.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.matt.christmas.Christmas;
import net.matt.christmas.block.custom.MagicBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block MAGIC_BLOCK = registerBlock(
            "magic_block",
            new MagicBlock(
            AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
            ));

    public static final Block PEPPERMINT_BLOCK = registerBlock(
            "peppermint_block",
            new Block(
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_STAIRS = registerBlock(
            "peppermint_stairs",
            new StairsBlock(
                    ModBlocks.PEPPERMINT_BLOCK.getDefaultState(),
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_SLAB = registerBlock(
            "peppermint_slab",
            new SlabBlock(
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_BUTTON = registerBlock(
            "peppermint_button",
            new ButtonBlock(
                    BlockSetType.IRON, 2,
                    AbstractBlock.Settings.create()
                            .noCollision()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_PRESSURE_PLATE = registerBlock(
            "peppermint_pressure_plate",
            new PressurePlateBlock(
                    BlockSetType.IRON,
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_FENCE = registerBlock(
            "peppermint_fence",
            new FenceBlock(
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_FENCE_GATE = registerBlock(
            "peppermint_fence_gate",
            new FenceGateBlock(
                    WoodType.ACACIA,
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_WALL = registerBlock(
            "peppermint_wall",
            new WallBlock(
                    AbstractBlock.Settings.create()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_DOOR = registerBlock(
            "peppermint_door",
            new DoorBlock(
                    BlockSetType.IRON,
                    AbstractBlock.Settings.create()
                            .nonOpaque()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));
    public static final Block PEPPERMINT_TRAPDOOR = registerBlock(
            "peppermint_trapdoor",
            new TrapdoorBlock(
                    BlockSetType.IRON,
                    AbstractBlock.Settings.create()
                            .nonOpaque()
                            .strength(2f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.BONE)
            ));

    /*
    // how to add a block that drops experience
    // UniformIntProvider.create(2,5) is 2-5 xp's
    // translations, models, items and all that junk aren't implemented for this guy
    public static final Block CANDY_CANE_ORE = registerBlock(
            "candy_cane_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(2,5),
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    */


    // registers the actual block as a whole
    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Christmas.MOD_ID, name), block);
    }

    // registers the item for the block
    private static void registerBlockItem(String name, Block block)
    {
        Registry.register(Registries.ITEM, Identifier.of(Christmas.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks()
    {
        Christmas.LOGGER.info("Registering Mod Blocks for " + Christmas.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(PEPPERMINT_BLOCK);
        });
    }
}

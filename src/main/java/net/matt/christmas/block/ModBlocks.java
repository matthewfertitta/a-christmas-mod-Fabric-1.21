package net.matt.christmas.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.matt.christmas.Christmas;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block PEPPERMINT_BLOCK = registerBlock(
            "peppermint_block",
            new Block(
            AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BONE)
            )
    );




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

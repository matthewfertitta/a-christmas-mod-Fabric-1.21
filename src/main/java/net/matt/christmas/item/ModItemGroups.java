package net.matt.christmas.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.matt.christmas.Christmas;
import net.matt.christmas.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    // makes the creative tab for these items
    public static final ItemGroup CHRISTMAS_ITEMS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(Christmas.MOD_ID, "christmas_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.PEPPERMINT_BLOCK))
                    .displayName(Text.translatable("itemgroup.christmas.christmas_items"))
                    // this is where you add items to the tab
                    .entries((displayContext, entries) -> {
                        // items
                        entries.add(ModItems.CANDY_CANE);
                        entries.add(ModItems.PRESENT);

                        // food
                        entries.add(ModItems.EGGNOG);
                        entries.add(ModItems.HOT_COCOA);
                        entries.add(ModItems.COLD_COCOA);

                        // special
                        entries.add(ModItems.FROST_SUMMON);

                        //blocks
                        entries.add(ModBlocks.PEPPERMINT_BLOCK);
                        entries.add(ModBlocks.PEPPERMINT_STAIRS);
                        entries.add(ModBlocks.PEPPERMINT_SLAB);
                        entries.add(ModBlocks.PEPPERMINT_BUTTON);
                        entries.add(ModBlocks.PEPPERMINT_PRESSURE_PLATE);
                        entries.add(ModBlocks.PEPPERMINT_FENCE);
                        entries.add(ModBlocks.PEPPERMINT_FENCE_GATE);
                        entries.add(ModBlocks.PEPPERMINT_WALL);
                        entries.add(ModBlocks.PEPPERMINT_DOOR);
                        entries.add(ModBlocks.PEPPERMINT_TRAPDOOR);
                        //entries.add(ModBlocks.MAGIC_BLOCK);
                    }).build());

    public static void registerItemGroups()
    {
        Christmas.LOGGER.info("Registering Item Groups for " + Christmas.MOD_ID);
    }
}

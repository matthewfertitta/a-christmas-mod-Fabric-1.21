package net.matt.christmas.util;

import net.matt.christmas.Christmas;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks
    {
        // add new block tags here

        // helper method to make block tags
        private static TagKey<Block> createTag(String name)
        {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Christmas.MOD_ID, name));
        }
    }

    public static class Items
    {
        // add new item tags here
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        // helper method to make item tags
        private static TagKey<Item> createTag(String name)
        {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Christmas.MOD_ID, name));
        }
    }
}

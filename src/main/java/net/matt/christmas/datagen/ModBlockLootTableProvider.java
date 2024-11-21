package net.matt.christmas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.matt.christmas.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {

    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    // generates loot-tables here
    @Override
    public void generate() {
        addDrop(ModBlocks.PEPPERMINT_BLOCK);

        addDrop(ModBlocks.PEPPERMINT_STAIRS);
        addDrop(ModBlocks.PEPPERMINT_SLAB, slabDrops(ModBlocks.PEPPERMINT_SLAB));

        addDrop(ModBlocks.PEPPERMINT_BUTTON);
        addDrop(ModBlocks.PEPPERMINT_PRESSURE_PLATE);

        addDrop(ModBlocks.PEPPERMINT_WALL);
        addDrop(ModBlocks.PEPPERMINT_FENCE);
        addDrop(ModBlocks.PEPPERMINT_FENCE_GATE);

        addDrop(ModBlocks.PEPPERMINT_DOOR, doorDrops(ModBlocks.PEPPERMINT_DOOR));
        addDrop(ModBlocks.PEPPERMINT_TRAPDOOR);

        // single drop
        //addDrop(ModBlocks.MAGIC_BLOCK, oreDrops(ModBlocks.MAGIC_BLOCK, ModItems.PRESENT));
        // multiple drops
        //addDrop(ModBlocks.MAGIC_BLOCK, multipleOreDrops(ModBlocks.MAGIC_BLOCK, ModItems.PRESENT, 1, 5));
    }

    // helper method for ores
    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}

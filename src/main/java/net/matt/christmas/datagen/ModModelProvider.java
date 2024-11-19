package net.matt.christmas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.matt.christmas.block.ModBlocks;
import net.matt.christmas.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    // generates block models here
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PEPPERMINT_BLOCK);
    }

    // generates item models here
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.EGGNOG, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROST_SUMMON, Models.GENERATED);
        itemModelGenerator.register(ModItems.CANDY_CANE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRESENT, Models.GENERATED);
    }
}

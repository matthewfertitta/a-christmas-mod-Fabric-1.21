package net.matt.christmas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.matt.christmas.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    // adds tags to blocks
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.MAGIC_BLOCK)
                .add(ModBlocks.PEPPERMINT_BLOCK)
                .add(ModBlocks.PEPPERMINT_BLOCK)
                .add(ModBlocks.PEPPERMINT_STAIRS)
                .add(ModBlocks.PEPPERMINT_SLAB)
                .add(ModBlocks.PEPPERMINT_BUTTON)
                .add(ModBlocks.PEPPERMINT_PRESSURE_PLATE)
                .add(ModBlocks.PEPPERMINT_WALL)
                .add(ModBlocks.PEPPERMINT_FENCE)
                .add(ModBlocks.PEPPERMINT_FENCE_GATE)
                .add(ModBlocks.PEPPERMINT_DOOR)
                .add(ModBlocks.PEPPERMINT_TRAPDOOR);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PEPPERMINT_BLOCK)
                .add(ModBlocks.PEPPERMINT_BLOCK)
                .add(ModBlocks.PEPPERMINT_STAIRS)
                .add(ModBlocks.PEPPERMINT_SLAB)
                .add(ModBlocks.PEPPERMINT_BUTTON)
                .add(ModBlocks.PEPPERMINT_PRESSURE_PLATE)
                .add(ModBlocks.PEPPERMINT_WALL)
                .add(ModBlocks.PEPPERMINT_FENCE)
                .add(ModBlocks.PEPPERMINT_FENCE_GATE)
                .add(ModBlocks.PEPPERMINT_DOOR)
                .add(ModBlocks.PEPPERMINT_TRAPDOOR);

        getOrCreateTagBuilder(BlockTags.FENCES).add(ModBlocks.PEPPERMINT_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.PEPPERMINT_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS).add(ModBlocks.PEPPERMINT_WALL);
    }
}

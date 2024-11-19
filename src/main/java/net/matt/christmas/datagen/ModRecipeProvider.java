package net.matt.christmas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.matt.christmas.Christmas;
import net.matt.christmas.block.ModBlocks;
import net.matt.christmas.item.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    // make recipes here!!!
    @Override
    public void generate(RecipeExporter exporter) {

        //List<ItemConvertible> PINK_GARNET_SMELTABLES = List.of(ModItems.CANDY_CANE, ModItems.CANDY_CANE);

        //offerSmelting(exporter, PINK_GARNET_SMELTABLES, RecipeCategory.MISC, ModItems.PRESENT, 0.25f, 200, "present");
        //offerBlasting(exporter, PINK_GARNET_SMELTABLES, RecipeCategory.MISC, ModItems.PRESENT, 0.25f, 200, "present");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CANDY_CANE, RecipeCategory.DECORATIONS, ModBlocks.PEPPERMINT_BLOCK);

        // Shaped Recipe Example
        /*
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PEPPERMINT_BLOCK)
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .input('R', ModItems.CANDY_CANE)
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
         */

        // Shapeless Recipe Builder
        // Identifier.of just prevents multiple recipe names from conflicting
        /*
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CANDY_CANE, 9)
                .input(ModBlocks.PEPPERMINT_BLOCK)
                .criterion(hasItem(ModBlocks.PEPPERMINT_BLOCK), conditionsFromItem(ModBlocks.PEPPERMINT_BLOCK))
                .offerTo(exporter, Identifier.of(Christmas.MOD_ID, "candy_cane_from_magic_block"));
        */
    }
}

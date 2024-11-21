package net.matt.christmas.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.matt.christmas.Christmas;
import net.matt.christmas.block.ModBlocks;
import net.matt.christmas.item.ModItems;
import net.matt.christmas.item.custom.FrostSummonItem;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
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

        // Peppermint Block
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.CANDY_CANE, RecipeCategory.DECORATIONS, ModBlocks.PEPPERMINT_BLOCK);
        // Frost Summon
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FROST_SUMMON)
                .pattern("ISI")
                .pattern("SDS")
                .pattern("ISI")
                .input('I', Items.PACKED_ICE)
                .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                .input('S', Items.SNOW_BLOCK)
                .criterion(hasItem(Items.SNOW_BLOCK), conditionsFromItem(Items.SNOW_BLOCK))
                .input('D', Items.DIAMOND)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);
        // cold cocoa
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COLD_COCOA, 1)
                .input(Items.COCOA_BEANS)
                .input(Items.SUGAR)
                .input(Items.MILK_BUCKET)
                .criterion(hasItem(Items.COCOA_BEANS), conditionsFromItem(Items.COCOA_BEANS))
                .criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
                .criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter);
        // hot cocoa
        offerSmelting(exporter, List.of(ModItems.COLD_COCOA), RecipeCategory.FOOD, ModItems.HOT_COCOA, 0.25f, 200, "hot_cocoa");

        // peppermint stairs
        createStairsRecipe(ModBlocks.PEPPERMINT_STAIRS, Ingredient.ofItems(ModItems.CANDY_CANE))
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
        // peppermint slabs
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEPPERMINT_SLAB, ModItems.CANDY_CANE);
        // peppermint button
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PEPPERMINT_BUTTON)
                .input(ModItems.CANDY_CANE)
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
        // peppermint pressure plate
        offerPressurePlateRecipe(exporter, ModBlocks.PEPPERMINT_PRESSURE_PLATE, ModItems.CANDY_CANE);
        // peppermint fence
        createFenceRecipe(ModBlocks.PEPPERMINT_FENCE, Ingredient.ofItems(ModItems.CANDY_CANE))
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
        // peppermint fence gate
        createFenceGateRecipe(ModBlocks.PEPPERMINT_FENCE_GATE, Ingredient.ofItems(ModItems.CANDY_CANE))
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
        // peppermint wall
        offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEPPERMINT_WALL, ModItems.CANDY_CANE);
        // peppermint door
        createDoorRecipe(ModBlocks.PEPPERMINT_DOOR, Ingredient.ofItems(ModItems.CANDY_CANE))
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);
        // peppermint trapdoor
        createTrapdoorRecipe(ModBlocks.PEPPERMINT_TRAPDOOR, Ingredient.ofItems(ModItems.CANDY_CANE))
                .criterion(hasItem(ModItems.CANDY_CANE), conditionsFromItem(ModItems.CANDY_CANE))
                .offerTo(exporter);




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

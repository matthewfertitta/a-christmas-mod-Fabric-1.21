package net.matt.christmas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.matt.christmas.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;

public class ChristmasClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEPPERMINT_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEPPERMINT_TRAPDOOR, RenderLayer.getCutout());
    }
}

package net.matt.christmas;

import net.fabricmc.api.ModInitializer;

import net.matt.christmas.block.ModBlocks;
import net.matt.christmas.item.ModItemGroups;
import net.matt.christmas.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Christmas implements ModInitializer {
	public static final String MOD_ID = "christmas";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

	}
}
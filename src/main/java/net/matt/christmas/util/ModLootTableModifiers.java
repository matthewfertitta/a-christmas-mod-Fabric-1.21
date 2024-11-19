package net.matt.christmas.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.matt.christmas.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier ZOMBIE_ID = Identifier.of("minecraft", "entities/zombie");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && ZOMBIE_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(ModItems.PRESENT)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 3.0F))) // 1 to 3 items dropped
                        .conditionally(RandomChanceLootCondition.builder(.80f))); // Drops 80% of the time

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}

package com.qichen.basicmagicaldomain.datagen;

import net.minecraft.WorldVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerator {
    public ModDataGenerator(IEventBus eventBus){
        eventBus.addListener(ModDataGenerator::onGatherData);
    }

    public static void onGatherData(GatherDataEvent event) {
        var gen=event.getGenerator();
        var output=gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(),new ModItemModelProvider(output,helper));
        gen.addProvider(event.includeClient(),new ModBlockModelProvider(output,helper));
        gen.addProvider(event.includeClient(),new LangProvider(output,"en_us"));
        gen.addProvider(event.includeClient(),new ChineseLangProvider(output));
        gen.addProvider(event.includeClient(),new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        gen.addProvider(event.includeClient(),new ModRecipeProvider(output,lookupProvider));
    }
}

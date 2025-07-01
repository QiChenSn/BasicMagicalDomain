package com.qichen.basicmagicaldomain.datagen;

import net.minecraft.WorldVersion;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.nio.file.Path;
import java.util.Collections;

public class ModDataGenerator {
    public ModDataGenerator(IEventBus eventBus){
        eventBus.addListener(ModDataGenerator::onGatherData);
    }

    public static void onGatherData(GatherDataEvent event) {
        var gen=event.getGenerator();
        var output=gen.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(),new ModItemModelProvider(output,helper));
        gen.addProvider(event.includeClient(),new ModBlockModelProvider(output,helper));
        gen.addProvider(event.includeClient(),new LangProvider(output,"en_us"));
        gen.addProvider(event.includeClient(),new ChineseLangProvider(output));
    }
}

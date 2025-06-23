package com.qichen.basicmagicaldomain.item;

import com.qichen.basicmagicaldomain.item.custom.rune.EarthRune;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemRegister {

    public static void register(IEventBus eventBus){
        EarthRune.ITEMS.register(eventBus);
    }
}

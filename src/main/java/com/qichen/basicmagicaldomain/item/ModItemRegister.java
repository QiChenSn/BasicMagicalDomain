package com.qichen.basicmagicaldomain.item;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.custom.MaigalAltar;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModItemRegister {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> Magical_RUNE = ITEMS.register("magical_rune",()->
            new MagicalRune(new Item.Properties(), 0, 60, 16, 60 * 20) {
                @Override
                public Consumer<RuneContext> getEffectConsumer() {
                    return null;
                }
            } // 范围30格，持续60秒
    );
    public static final DeferredItem<Item> MAGICAL_ALTAR_ITEM = ITEMS.register(
            "magical_altar_item",
            () -> new BlockItem(MaigalAltar.MAGICAL_ALTAR.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus){
        EarthRune.ITEMS.register(eventBus);
        WoodRune.ITEMS.register(eventBus);
        FireRune.ITEMS.register(eventBus);
        WaterRune.ITEMS.register(eventBus);
        MetalRune.ITEMS.register(eventBus);
        ModItemRegister.ITEMS.register(eventBus);
    }
}

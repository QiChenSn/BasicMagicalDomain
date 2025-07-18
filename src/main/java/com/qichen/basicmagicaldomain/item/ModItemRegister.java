package com.qichen.basicmagicaldomain.item;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.ModBlockRegister;
import com.qichen.basicmagicaldomain.block.custom.MagicalAltar;
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
    public static final DeferredItem<Item> EARTH_RUNE = ITEMS.register("earth_rune",()->
            new EarthRune(new Item.Properties(), EarthRune.Earth, 60,16, 60*20) // 范围30格，持续60秒
    );
    public static final DeferredItem<Item> FIRE_RUNE = ITEMS.register("fire_rune",()->
            new FireRune(new Item.Properties(), FireRune.Fire, 60, 16, 60*20) // 范围16格，持续60秒
    );
    public static final DeferredItem<Item> METAL_Rune = ITEMS.register("metal_rune",()->
            new MetalRune(new Item.Properties(), MetalRune.Metal, 60,16, 60*20) // 范围30格，持续60秒
    );
    public static final DeferredItem<Item> WATER_RUNE = ITEMS.register("water_rune",()->
            new WaterRune(new Item.Properties(), WaterRune.Water, 60, 16, 60*20) // 范围16格，持续60秒
    );
    public static final DeferredItem<Item> Wood_Rune = ITEMS.register("wood_rune",()->
            new WoodRune(new Item.Properties(), WoodRune.Wood, 60,16, 60*20) // 范围30格，持续60秒
    );
    public static final DeferredItem<Item> MAGICAL_ALTAR_ITEM = ITEMS.register(
            "magical_altar_item",
            () -> new BlockItem(ModBlockRegister.MAGICAL_ALTAR.get(), new Item.Properties())
    );

    public static void register(IEventBus eventBus){
        ModItemRegister.ITEMS.register(eventBus);
    }
}

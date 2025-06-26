package com.qichen.basicmagicaldomain.item.custom.rune;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class WaterRune extends MagicalRune{
    public WaterRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, type, USE_DURATION, range, effect_time);
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> WATER_Rune = ITEMS.register("water_rune",()->
            new WaterRune(new Properties(), Water, 60,16, 60*20) // 范围30格，持续60秒
    );

    @Override
    public Consumer<RuneContext> getEffectConsumer() {
        return null;
    }
}

package com.qichen.basicmagicaldomain.item.custom.rune;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EarthRune extends MagicalRune{

    public EarthRune(Properties properties, int type, int range) {
        super(properties, MagicalRune.Earth, 5);
    }
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> EARTH_RUNE=ITEMS.registerItem("earth_rune",Item::new,new Item.Properties());
    @Override
    public void use_effect(ItemStack itemStack) {

    }
}

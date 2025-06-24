package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.custom.rune.EarthRune;
import com.qichen.basicmagicaldomain.item.custom.rune.WoodRune;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output, String locale) {
        super(output, BasicMagicalDomain.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(EarthRune.EARTH_RUNE.get(), "Earth Rune");
        add(WoodRune.Wood_Rune.get(), "Wood Rune");
        add("itemGroup." + BasicMagicalDomain.MODID + ".item","Item Tab");
    }
}

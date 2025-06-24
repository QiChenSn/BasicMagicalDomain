package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.custom.rune.EarthRune;
import com.qichen.basicmagicaldomain.item.custom.rune.WoodRune;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper helper){
        super(output, BasicMagicalDomain.MODID,helper);
    }

    @Override
    protected void registerModels() {
        handheldItem(EarthRune.EARTH_RUNE);
        handheldItem(WoodRune.Wood_Rune);
    }
    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(BasicMagicalDomain.MODID,"item/" + item.getId().getPath()));
    }
}

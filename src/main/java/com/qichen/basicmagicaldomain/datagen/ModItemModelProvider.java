package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
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
        handheldItem(WaterRune.WATER_RUNE);
        handheldItem(FireRune.FIRE_RUNE);
        handheldItem(MetalRune.METAL_Rune);
        handheldItem(ModItemRegister.Magical_RUNE
        );
    }
    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(BasicMagicalDomain.MODID,"item/" + item.getId().getPath()));
    }
}

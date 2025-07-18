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
        handheldItem(ModItemRegister.EARTH_RUNE);
        handheldItem(ModItemRegister.Wood_Rune);
        handheldItem(ModItemRegister.WATER_RUNE);
        handheldItem(ModItemRegister.FIRE_RUNE);
        handheldItem(ModItemRegister.METAL_Rune);
        handheldItem(ModItemRegister.Magical_RUNE);
        
        // 为方块物品生成模型 - 使用简单的物品模型
        basicItem(ModItemRegister.MAGICAL_ALTAR_ITEM.get());
    }
    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(BasicMagicalDomain.MODID,"item/" + item.getId().getPath()));
    }
}

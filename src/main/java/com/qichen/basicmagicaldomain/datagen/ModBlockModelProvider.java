package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.ModBlockRegister;
import com.qichen.basicmagicaldomain.block.custom.MagicalAltar;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, BasicMagicalDomain.MODID, helper);
    }

    @Override
    protected void registerModels() {
        // 为魔法祭坛生成方块模型
        cubeAll(ModBlockRegister.MAGICAL_ALTAR.getId().getPath(),
            ResourceLocation.fromNamespaceAndPath(BasicMagicalDomain.MODID, "block/magical_altar"));
    }
}

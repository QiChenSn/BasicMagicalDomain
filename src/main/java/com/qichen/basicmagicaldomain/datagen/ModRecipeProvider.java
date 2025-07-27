package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.MagicalRune;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        //石头 红石粉 石头
        //萤石粉 末影之眼 萤石粉
        //石头 红石粉 石头
        shaped(RecipeCategory.MISC,ModItemRegister.Magical_RUNE.get())
                .pattern("SRS")
                .pattern("YMY")
                .pattern("SRS")
                .define('S', Items.STONE)
                .define('R', Items.REDSTONE)
                .define('Y', Items.GLOWSTONE_DUST)
                .define('M', Items.ENDER_EYE)
                .unlockedBy("has_ender_eye",has(Items.ENDER_EYE))
                .save(recipeOutput);
        //下届之星 龙蛋 下届之星
        //烈焰棒 黑曜石 烈焰棒
        //黑曜石 黑曜石 黑曜石
        shaped(RecipeCategory.MISC,ModItemRegister.MAGICAL_ALTAR_ITEM.get())
                .pattern("XLX")
                .pattern("YHY")
                .pattern("HHH")
                .define('X',Items.NETHER_STAR)
                .define('L',Items.DRAGON_EGG)
                .define('Y',Items.BLAZE_ROD)
                .define('H',Items.OBSIDIAN)
                .unlockedBy("has_dragon_egg",has(Items.DRAGON_EGG))
                .save(recipeOutput);

    }
}

package com.qichen.basicmagicaldomain.item;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.custom.MaigalAltar;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BasicMagicalDomain.MODID);
    
    public static final Supplier<CreativeModeTab> MAGICAL_DOMAIN_TAB = CREATIVE_MODE_TAB.register("magical_domain_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BasicMagicalDomain.MODID + ".item"))
            .icon(() -> new ItemStack(ModItemRegister.Magical_RUNE.get()))
            .displayItems((params, output) -> {
                output.accept(EarthRune.EARTH_RUNE.get());
                output.accept(WoodRune.Wood_Rune.get());
                output.accept(WaterRune.WATER_RUNE.get());
                output.accept(MetalRune.METAL_Rune.get());
                output.accept(FireRune.FIRE_RUNE.get());
            })
            .build()
    );
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

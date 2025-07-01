package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output, String locale) {
        super(output, BasicMagicalDomain.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(EarthRune.EARTH_RUNE.get(), "§6Earth Rune");
        add(WoodRune.Wood_Rune.get(), "§aWood Rune");
        add(WaterRune.WATER_RUNE.get(), "§bWater Rune");
        add(FireRune.FIRE_RUNE.get(), "§cFire Rune");
        add(MetalRune.METAL_Rune.get(), "§6Metal Rune");
        add(ModItemRegister.Magical_RUNE.get(), "§dMagical Rune");
        add(ModItemRegister.MAGICAL_ALTAR_ITEM.get(), "§5Magical Altar");
        add("itemGroup." + BasicMagicalDomain.MODID + ".item","Basic Magical Domain");
        
        add("tooltip.basicmagicaldomain.earth_rune.title", "- Embodiment of Earth's Power");
        add("tooltip.basicmagicaldomain.earth_rune.function", "Function: Accelerate Plant Growth");
        add("tooltip.basicmagicaldomain.earth_rune.range_duration", "Range: 30 blocks | Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.earth_rune.hold_shift", "Hold Shift for detailed information");
        
        add("tooltip.basicmagicaldomain.earth_rune.detailed.main_function", "Main Functions:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.accelerate_growth", "• Accelerate plant growth");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.range", "• Range: 30 blocks");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.duration", "• Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.activation", "• Activation time: 3 seconds");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.applicable_plants", "Applicable Plants:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.crops", "• Crops, saplings, sugar cane");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.cactus", "• Cactus, bamboo, kelp");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.nether", "• Chorus flower, nether wart");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.usage", "Usage:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.hold_right", "• Hold the rune and right-click for 3 seconds");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.release", "• Release to activate plant growth effect");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.auto_accelerate", "• Automatically accelerate plant growth every 2 seconds");
        
        add("tooltip.basicmagicaldomain.fire_rune.title", "- Embodiment of Fire's Fury");
        add("tooltip.basicmagicaldomain.fire_rune.function", "Function: Fire Burst Attack");
        add("tooltip.basicmagicaldomain.fire_rune.range_duration", "Range: 16 blocks | Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.fire_rune.hold_shift", "Hold Shift for detailed information");
        
        add("tooltip.basicmagicaldomain.fire_rune.detailed.main_function", "Main Functions:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.fire_burst", "• Fire burst attack on enemies");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.range", "• Range: 16 blocks");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.duration", "• Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.activation", "• Activation time: 3 seconds");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.effects", "Attack Effects:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.fire_damage", "• Fire damage based on distance");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.burning", "• Ignite enemies for 5 seconds");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.confusion", "• Apply confusion effect");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.usage", "Usage:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.hold_right", "• Hold the rune and right-click for 3 seconds");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.release", "• Release to activate fire burst");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.auto_burst", "• Automatically burst every 3 seconds");
        
        add("tooltip.basicmagicaldomain.water_rune.title", "- Embodiment of Water's Flow");
        add("tooltip.basicmagicaldomain.water_rune.function", "Function: Water Shockwave Defense");
        add("tooltip.basicmagicaldomain.water_rune.range_duration", "Range: 16 blocks | Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.water_rune.hold_shift", "Hold Shift for detailed information");
        
        add("tooltip.basicmagicaldomain.water_rune.detailed.main_function", "Main Functions:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.shockwave", "• Water shockwave defense");
        add("tooltip.basicmagicaldomain.water_rune.detailed.range", "• Range: 16 blocks");
        add("tooltip.basicmagicaldomain.water_rune.detailed.duration", "• Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.water_rune.detailed.activation", "• Activation time: 3 seconds");
        add("tooltip.basicmagicaldomain.water_rune.detailed.effects", "Defense Effects:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.knockback", "• Knockback enemies");
        add("tooltip.basicmagicaldomain.water_rune.detailed.slow", "• Slow down enemies");
        add("tooltip.basicmagicaldomain.water_rune.detailed.weakness", "• Apply weakness effect");
        add("tooltip.basicmagicaldomain.water_rune.detailed.usage", "Usage:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.hold_right", "• Hold the rune and right-click for 3 seconds");
        add("tooltip.basicmagicaldomain.water_rune.detailed.release", "• Release to activate water shockwave");
        add("tooltip.basicmagicaldomain.water_rune.detailed.auto_shockwave", "• Automatically shockwave every 5 seconds");
        
        add("tooltip.basicmagicaldomain.wood_rune.title", "- Embodiment of Life's Vitality");
        add("tooltip.basicmagicaldomain.wood_rune.function", "Function: Life Regeneration Aura");
        add("tooltip.basicmagicaldomain.wood_rune.range_duration", "Range: 16 blocks | Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.wood_rune.hold_shift", "Hold Shift for detailed information");
        
        add("tooltip.basicmagicaldomain.wood_rune.detailed.main_function", "Main Functions:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.regeneration", "• Life regeneration aura");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.range", "• Range: 16 blocks");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.duration", "• Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.activation", "• Activation time: 3 seconds");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.effects", "Healing Effects:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.health_regen", "• Regenerate health for all players");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.saturation", "• Restore hunger and saturation");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.continuous", "• Continuous healing effect");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.usage", "Usage:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.hold_right", "• Hold the rune and right-click for 3 seconds");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.release", "• Release to activate healing aura");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.auto_heal", "• Automatically heal all nearby players");
        
        add("tooltip.basicmagicaldomain.metal_rune.title", "- Embodiment of Metal's Fortune");
        add("tooltip.basicmagicaldomain.metal_rune.function", "Function: Ore Detection & Mining");
        add("tooltip.basicmagicaldomain.metal_rune.range_duration", "Range: 16 blocks | Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.metal_rune.hold_shift", "Hold Shift for detailed information");
        
        add("tooltip.basicmagicaldomain.metal_rune.detailed.main_function", "Main Functions:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.ore_detection", "• Detect ores in range");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.range", "• Range: 16 blocks");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.duration", "• Duration: 60 seconds");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.activation", "• Activation time: 3 seconds");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.effects", "Mining Effects:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.auto_mining", "• Automatically mine detected ores");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.random_drops", "• Generate random ore drops");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.success_rate", "• 50% success rate per attempt");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.usage", "Usage:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.hold_right", "• Hold the rune and right-click for 3 seconds");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.release", "• Release to activate ore detection");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.auto_detect", "• Automatically detect and mine every second");
    }
}

package com.qichen.basicmagicaldomain.datagen;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ChineseLangProvider extends LanguageProvider {
    public ChineseLangProvider(PackOutput output) {
        super(output, BasicMagicalDomain.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // 物品名称 - 使用与tooltip标题相同的颜色
        add(EarthRune.EARTH_RUNE.get(), "§6土之符文"); // 金色
        add(WoodRune.Wood_Rune.get(), "§a木之符文"); // 绿色
        add(WaterRune.WATER_RUNE.get(), "§b水之符文"); // 青色
        add(FireRune.FIRE_RUNE.get(), "§c火之符文"); // 红色
        add(MetalRune.METAL_Rune.get(), "§6金之符文"); // 金色
        add(ModItemRegister.Magical_RUNE.get(), "§d魔法符文"); // 紫色
        add("itemGroup." + BasicMagicalDomain.MODID + ".item", "基础魔法领域");
        
        // 土之符文提示文本
        add("tooltip.basicmagicaldomain.earth_rune.title", "- 大地之力的化身");
        add("tooltip.basicmagicaldomain.earth_rune.function", "功能: 加速植物生长");
        add("tooltip.basicmagicaldomain.earth_rune.range_duration", "范围: 30格 | 持续: 60秒");
        add("tooltip.basicmagicaldomain.earth_rune.hold_shift", "按住Shift查看详细信息");
        
        // 土之符文详细提示
        add("tooltip.basicmagicaldomain.earth_rune.detailed.main_function", "主要功能:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.accelerate_growth", "• 加速植物生长");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.range", "• 范围: 30格");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.duration", "• 持续时间: 60秒");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.activation", "• 激活时间: 3秒");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.applicable_plants", "适用植物:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.crops", "• 农作物、树苗、甘蔗");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.cactus", "• 仙人掌、竹子、海带");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.nether", "• 紫颂花、地狱疣");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.usage", "使用方式:");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.hold_right", "• 手持符文，按住右键蓄力3秒");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.release", "• 释放后激活植物生长效果");
        add("tooltip.basicmagicaldomain.earth_rune.detailed.auto_accelerate", "• 每2秒自动加速一次植物生长");
        
        // 火之符文提示文本
        add("tooltip.basicmagicaldomain.fire_rune.title", "- 烈焰之怒的化身");
        add("tooltip.basicmagicaldomain.fire_rune.function", "功能: 火焰爆发攻击");
        add("tooltip.basicmagicaldomain.fire_rune.range_duration", "范围: 16格 | 持续: 60秒");
        add("tooltip.basicmagicaldomain.fire_rune.hold_shift", "按住Shift查看详细信息");
        
        // 火之符文详细提示
        add("tooltip.basicmagicaldomain.fire_rune.detailed.main_function", "主要功能:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.fire_burst", "• 对敌人进行火焰爆发攻击");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.range", "• 范围: 16格");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.duration", "• 持续时间: 60秒");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.activation", "• 激活时间: 3秒");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.effects", "攻击效果:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.fire_damage", "• 根据距离造成火焰伤害");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.burning", "• 点燃敌人5秒");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.confusion", "• 施加混乱效果");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.usage", "使用方式:");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.hold_right", "• 手持符文，按住右键蓄力3秒");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.release", "• 释放后激活火焰爆发");
        add("tooltip.basicmagicaldomain.fire_rune.detailed.auto_burst", "• 每3秒自动爆发一次");
        
        // 水之符文提示文本
        add("tooltip.basicmagicaldomain.water_rune.title", "- 流水之力的化身");
        add("tooltip.basicmagicaldomain.water_rune.function", "功能: 水冲击波防御");
        add("tooltip.basicmagicaldomain.water_rune.range_duration", "范围: 16格 | 持续: 60秒");
        add("tooltip.basicmagicaldomain.water_rune.hold_shift", "按住Shift查看详细信息");
        
        // 水之符文详细提示
        add("tooltip.basicmagicaldomain.water_rune.detailed.main_function", "主要功能:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.shockwave", "• 水冲击波防御");
        add("tooltip.basicmagicaldomain.water_rune.detailed.range", "• 范围: 16格");
        add("tooltip.basicmagicaldomain.water_rune.detailed.duration", "• 持续时间: 60秒");
        add("tooltip.basicmagicaldomain.water_rune.detailed.activation", "• 激活时间: 3秒");
        add("tooltip.basicmagicaldomain.water_rune.detailed.effects", "防御效果:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.knockback", "• 击退敌人");
        add("tooltip.basicmagicaldomain.water_rune.detailed.slow", "• 减缓敌人速度");
        add("tooltip.basicmagicaldomain.water_rune.detailed.weakness", "• 施加虚弱效果");
        add("tooltip.basicmagicaldomain.water_rune.detailed.usage", "使用方式:");
        add("tooltip.basicmagicaldomain.water_rune.detailed.hold_right", "• 手持符文，按住右键蓄力3秒");
        add("tooltip.basicmagicaldomain.water_rune.detailed.release", "• 释放后激活水冲击波");
        add("tooltip.basicmagicaldomain.water_rune.detailed.auto_shockwave", "• 每5秒自动冲击波一次");
        
        // 木之符文提示文本
        add("tooltip.basicmagicaldomain.wood_rune.title", "- 生命活力的化身");
        add("tooltip.basicmagicaldomain.wood_rune.function", "功能: 生命恢复光环");
        add("tooltip.basicmagicaldomain.wood_rune.range_duration", "范围: 16格 | 持续: 60秒");
        add("tooltip.basicmagicaldomain.wood_rune.hold_shift", "按住Shift查看详细信息");
        
        // 木之符文详细提示
        add("tooltip.basicmagicaldomain.wood_rune.detailed.main_function", "主要功能:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.regeneration", "• 生命恢复光环");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.range", "• 范围: 16格");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.duration", "• 持续时间: 60秒");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.activation", "• 激活时间: 3秒");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.effects", "治疗效果:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.health_regen", "• 为所有玩家恢复生命值");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.saturation", "• 恢复饥饿值和饱和度");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.continuous", "• 持续治疗效果");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.usage", "使用方式:");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.hold_right", "• 手持符文，按住右键蓄力3秒");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.release", "• 释放后激活治疗光环");
        add("tooltip.basicmagicaldomain.wood_rune.detailed.auto_heal", "• 自动治疗附近所有玩家");
        
        // 金之符文提示文本
        add("tooltip.basicmagicaldomain.metal_rune.title", "- 金属财富的化身");
        add("tooltip.basicmagicaldomain.metal_rune.function", "功能: 矿石探测与挖掘");
        add("tooltip.basicmagicaldomain.metal_rune.range_duration", "范围: 16格 | 持续: 60秒");
        add("tooltip.basicmagicaldomain.metal_rune.hold_shift", "按住Shift查看详细信息");
        
        // 金之符文详细提示
        add("tooltip.basicmagicaldomain.metal_rune.detailed.main_function", "主要功能:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.ore_detection", "• 探测范围内的矿石");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.range", "• 范围: 16格");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.duration", "• 持续时间: 60秒");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.activation", "• 激活时间: 3秒");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.effects", "挖掘效果:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.auto_mining", "• 自动挖掘探测到的矿石");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.random_drops", "• 生成随机矿石掉落物");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.success_rate", "• 每次尝试50%成功率");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.usage", "使用方式:");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.hold_right", "• 手持符文，按住右键蓄力3秒");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.release", "• 释放后激活矿石探测");
        add("tooltip.basicmagicaldomain.metal_rune.detailed.auto_detect", "• 每秒自动探测并挖掘");
    }
} 
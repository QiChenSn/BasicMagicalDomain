package com.qichen.basicmagicaldomain.item.custom.rune;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.openjdk.nashorn.api.tree.ConditionalExpressionTree;

import java.util.function.Consumer;

public class WoodRune extends MagicalRune{
    public WoodRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, type, USE_DURATION, range, effect_time);
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> Wood_Rune = ITEMS.register("wood_rune",()->
            new WoodRune(new Item.Properties(), Wood, 60,16, 60*20) // 范围30格，持续60秒
    );

    @Override
    public Consumer<RuneContext> getEffectConsumer(){
        return context->{
            Player player=context.getPlayer();
            Level level = context.getLevel();
            AABB area=new AABB(player.getX()-this.range,player.getY()-this.range,player.getZ()-this.range,player.getX()+this.range,player.getY()+this.range,player.getZ()+this.range);
            level.getEntitiesOfClass(Player.class,area).forEach(aplayer -> {
                // 持续恢复生命值（每秒1❤️，持续5秒）
                aplayer.addEffect(new MobEffectInstance(
                        MobEffects.REGENERATION, // 再生效果
                        this.effect_time,    // 持续时间（100 ticks = 5秒）
                        0,      // 效果等级（0级=每秒恢复1❤️）
                        false,  // 无粒子效果
                        true    // 显示图标
                ));

                // 持续恢复饱食度（每秒恢复1点饥饿值和饱和度，持续5秒）
                aplayer.addEffect(new MobEffectInstance(
                        MobEffects.SATURATION, // 饱和效果（瞬间恢复，但通过延长持续时间模拟持续恢复）
                        this.effect_time,     // 每2tick执行一次（每秒10次）
                        0,      // 每次恢复1点饥饿值和饱和度
                        false,
                        true
                ));});
            };
        };
    }

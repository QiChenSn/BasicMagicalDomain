package com.qichen.basicmagicaldomain.item.custom.rune;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lwjgl.glfw.GLFW;
import org.openjdk.nashorn.api.tree.ConditionalExpressionTree;

import java.util.List;
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
    @OnlyIn(Dist.CLIENT) // 确保只在客户端执行
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        // 简略说明（始终显示）
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.title").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.function").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.range_duration").withStyle(ChatFormatting.GRAY));

        // 检测 Shift 键状态
        boolean isShiftDown = isShiftKeyPressed();

        if (isShiftDown) {
            addDetailedTooltip(tooltipComponents);
        } else {
            tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.hold_shift").withStyle(ChatFormatting.YELLOW));
        }
    }

    // 检测 Shift 键是否按下
    @OnlyIn(Dist.CLIENT)
    private boolean isShiftKeyPressed() {
        Minecraft minecraft = Minecraft.getInstance();
        long windowHandle = minecraft.getWindow().getWindow();

        return GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
    }

    // 添加详细提示内容
    private void addDetailedTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.main_function").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.regeneration").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.range").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.duration").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.activation").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.effects").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.health_regen").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.saturation").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.continuous").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.usage").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.hold_right").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.release").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.wood_rune.detailed.auto_heal").withStyle(ChatFormatting.GRAY));
    }

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

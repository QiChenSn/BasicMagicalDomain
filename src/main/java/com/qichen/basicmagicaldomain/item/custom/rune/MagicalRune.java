package com.qichen.basicmagicaldomain.item.custom.rune;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public abstract class MagicalRune extends Item {
    protected final int type;
    protected final int range;
    protected final int effect_time;
    public static int Metal=1;
    public static int Wood=2;
    public static int Water=3;
    public static int Fire=4;
    public static int Earth=5;
    protected final int USE_DURATION;

    public MagicalRune(Properties properties,int type,int USE_DURATION,int range,int effect_time) {
        super(properties);
        this.type=type;
        this.range=range;
        this.effect_time=effect_time;
        this.USE_DURATION=USE_DURATION;
    }

    public abstract Consumer<RuneContext> getEffectConsumer();

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 5201314;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // 使用弓箭的动画效果
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        // 仅在服务器端启动使用过程
        if (!level.isClientSide) {
            //LOGGER.info("开始蓄力");
            player.startUsingItem(usedHand);
        }

        // 播放开始使用的音效
        level.playSound(player, player.getX(), player.getY(), player.getZ(),
                SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                SoundSource.PLAYERS,
                0.5F, 0.8F);

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // 计算实际使用时间（单位：tick）
        int useTime = this.getUseDuration(stack,entity) - timeLeft;

        // 仅在服务器端处理
        if (!level.isClientSide && entity instanceof Player) {
            Player player = (Player) entity;

            // 检查是否达到3秒（60 tick）
            if (useTime >= this.USE_DURATION) {
                //LOGGER.info("符文使用成功！");

                // 播放成功音效
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.ENCHANTMENT_TABLE_USE,
                        SoundSource.PLAYERS,
                        1.0F, 1.0F);

                // 激活效果
                RuneContext context=new RuneContext(player,level,stack,useTime);
                getEffectConsumer().accept(context);

                // 消耗一个符文（可选）
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                //LOGGER.info("蓄力中断，使用取消");
            }
        }
    }

    public class RuneContext {
        private final Player player;
        private final Level level;
        private final ItemStack stack;
        private final int useTime;

        private RuneContext(Player player, Level level, ItemStack stack, int useTime) {
            this.player = player;
            this.level = level;
            this.stack = stack;
            this.useTime = useTime;
        }

        public Player getPlayer() {
            return player;
        }

        public int getUseTime() {
            return useTime;
        }

        public ItemStack getStack() {
            return stack;
        }

        public Level getLevel() {
            return level;
        }
    }
}

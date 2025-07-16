package com.qichen.basicmagicaldomain.item.custom.rune;

import com.mojang.logging.LogUtils;
import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class WaterRune extends MagicalRune {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 存储玩家激活效果的计时器（玩家UUID -> 剩余时间）
    private static final Map<UUID, Integer> activeEffects = new HashMap<>();

    public WaterRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, MagicalRune.Water, USE_DURATION, range, effect_time);
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> WATER_RUNE = ITEMS.register("water_rune",()->
            new WaterRune(new Item.Properties(), Water, 60, 16, 60*20) // 范围16格，持续60秒
    );

    @Override
    @OnlyIn(Dist.CLIENT) // 确保只在客户端执行
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        // 简略说明（始终显示）
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.title").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.function").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.range_duration").withStyle(ChatFormatting.GRAY));

        // 检测 Shift 键状态
        boolean isShiftDown = isShiftKeyPressed();

        if (isShiftDown) {
            addDetailedTooltip(tooltipComponents);
        } else {
            tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.hold_shift").withStyle(ChatFormatting.YELLOW));
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
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.main_function").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.shockwave").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.range").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.duration").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.activation").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.effects").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.knockback").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.slow").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.weakness").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.usage").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.hold_right").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.release").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.water_rune.detailed.auto_shockwave").withStyle(ChatFormatting.GRAY));
    }

    // 激活水之防御效果
    private void activateWaterDefenseEffect(Player player) {
        UUID playerId = player.getUUID();

        // 设置效果持续时间
        activeEffects.put(playerId, this.effect_time);

        // 立即应用一次冲击波效果
        applyWaterShockwave(player);

        // 播放激活音效
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.WATER_AMBIENT,
                    SoundSource.PLAYERS,
                    1.0F, 1.0F);
        }

        LOGGER.info("已激活水之防御效果，范围: {}格，持续时间: {}秒", range, this.effect_time / 20);
    }

    // 应用水冲击波效果
    public static void applyWaterShockwave(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            BlockPos center = player.blockPosition();
            int range = 8; // 冲击波范围

            // 计算效果区域
            AABB area = new AABB(
                    center.getX() - range, center.getY() - 2, center.getZ() - range,
                    center.getX() + range, center.getY() + 3, center.getZ() + range
            );

            int entitiesAffected = 0;

            // 获取区域内的所有实体
            for (Entity entity : serverLevel.getEntities(null, area)) {
                if (entity instanceof LivingEntity livingEntity && entity != player) {
                    // 只对敌对生物生效
                    if (livingEntity.getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER) {
                        // 计算推开方向和距离
                        Vec3 playerPos = player.position();
                        Vec3 entityPos = entity.position();
                        Vec3 direction = entityPos.subtract(playerPos).normalize();
                        
                        // 根据距离计算推开力度
                        double distance = playerPos.distanceTo(entityPos);
                        double force = Math.max(0.5, 2.0 - distance / range);
                        
                        // 应用推开效果
                        entity.setDeltaMovement(direction.scale(force));
                        entity.hurtMarked = true;
                        
                        // 应用减速效果
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                        
                        entitiesAffected++;
                    }
                }
            }

            LOGGER.info("水冲击波影响了 {} 个实体", entitiesAffected);

            // 视觉效果（水粒子效果）
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.DRIPPING_WATER,
                    center.getX(), center.getY() + 1, center.getZ(),
                    200,
                    range, 2, range,
                    0.3
            );
            
            // 播放冲击波音效
            serverLevel.playSound(null, center.getX(), center.getY(), center.getZ(),
                    SoundEvents.GENERIC_SPLASH,
                    SoundSource.PLAYERS,
                    1.0F, 0.8F);
        }
    }

    // 在服务器tick中更新效果
    public static void onServerTick(MinecraftServer server) {
        // 每tick减少计时器并应用效果
        activeEffects.entrySet().removeIf(entry -> {
            UUID playerId = entry.getKey();
            int remainingTime = entry.getValue() - 1;

            if (remainingTime <= 0) {
                LOGGER.info("玩家 {} 的水之防御效果已结束", playerId);
                return true;
            }

            // 每5秒应用一次冲击波效果
            if (remainingTime % 100 == 0) {
                ServerPlayer player = server.getPlayerList().getPlayer(playerId);
                if (player != null) {
                    applyWaterShockwave(player);
                }
            }

            entry.setValue(remainingTime);
            return false;
        });
    }
    public void applyOnAltar(Level level, BlockPos pos) {

        BlockPos center = pos;
        int range = this.range; // 冲击波范围

        // 计算效果区域
        AABB area = new AABB(
                center.getX() - range, center.getY() - 2, center.getZ() - range,
                center.getX() + range, center.getY() + 3, center.getZ() + range
        );

        int entitiesAffected = 0;
        if(level.isClientSide)return;
        ServerLevel serverLevel= (ServerLevel) level;
        // 获取区域内的所有实体
        for (Entity entity : serverLevel.getEntities(null, area)) {
            if (entity instanceof LivingEntity livingEntity ) {
                // 只对敌对生物生效
                if (livingEntity.getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER) {
                    // 计算推开方向和距离
                    Vec3 playerPos = center.getCenter();
                    Vec3 entityPos = entity.position();
                    Vec3 direction = entityPos.subtract(playerPos).normalize();

                    // 根据距离计算推开力度
                    double distance = playerPos.distanceTo(entityPos);
                    double force = Math.max(0.5, 2.0 - distance / range);

                    // 应用推开效果
                    entity.setDeltaMovement(direction.scale(force));
                    entity.hurtMarked = true;

                    // 应用减速效果
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));

                    entitiesAffected++;
                }
            }


            // 视觉效果（水粒子效果）
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.DRIPPING_WATER,
                    center.getX(), center.getY() + 1, center.getZ(),
                    200,
                    range, 2, range,
                    0.3
            );

            // 播放冲击波音效
            serverLevel.playSound(null, center.getX(), center.getY(), center.getZ(),
                    SoundEvents.GENERIC_SPLASH,
                    SoundSource.PLAYERS,
                    1.0F, 0.8F);
        }
    }
    @Override
    public Consumer<RuneContext> getEffectConsumer() {
        return context -> {
            activateWaterDefenseEffect(context.getPlayer());
        };
    }
}

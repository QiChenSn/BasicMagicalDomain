package com.qichen.basicmagicaldomain.item.custom.rune;

import com.mojang.logging.LogUtils;
import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class FireRune extends MagicalRune {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 存储玩家激活效果的计时器（玩家UUID -> 剩余时间）
    private static final Map<UUID, Integer> activeEffects = new HashMap<>();

    public FireRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, MagicalRune.Fire, USE_DURATION, range, effect_time);
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> FIRE_RUNE = ITEMS.register("fire_rune",()->
            new FireRune(new Item.Properties(), Fire, 60, 16, 60*20) // 范围16格，持续60秒
    );

    // 激活火焰爆发效果
    private void activateFireBurstEffect(Player player) {
        UUID playerId = player.getUUID();

        // 设置效果持续时间
        activeEffects.put(playerId, this.effect_time);

        // 立即应用一次火焰爆发效果
        applyFireBurst(player);

        // 播放激活音效
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRE_AMBIENT,
                    SoundSource.PLAYERS,
                    1.0F, 1.0F);
        }

        LOGGER.info("已激活火焰爆发效果，范围: {}格，持续时间: {}秒", range, this.effect_time / 20);
    }

    // 应用火焰爆发效果
    public static void applyFireBurst(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            BlockPos center = player.blockPosition();
            int range = 8; // 火焰爆发范围

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
                        // 计算距离
                        double distance = player.position().distanceTo(entity.position());
                        
                        // 根据距离计算伤害
                        float damage = Math.max(2.0f, 8.0f - (float)(distance * 0.5));
                        
                        // 造成火焰伤害
                        livingEntity.hurt(serverLevel.damageSources().fireball(null, player), damage);
                        
                        // 应用燃烧效果
                        livingEntity.igniteForSeconds(5);
                        
                        // 应用混乱效果
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
                        
                        entitiesAffected++;
                    }
                }
            }

            LOGGER.info("火焰爆发影响了 {} 个实体", entitiesAffected);

            // 视觉效果（火焰粒子效果）
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.FLAME,
                    center.getX(), center.getY() + 1, center.getZ(),
                    300,
                    range, 2, range,
                    0.2
            );
            
            // 播放火焰爆发音效
            serverLevel.playSound(null, center.getX(), center.getY(), center.getZ(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.PLAYERS,
                    0.8F, 1.2F);
        }
    }

    // 在服务器tick中更新效果
    public static void onServerTick(MinecraftServer server) {
        // 每tick减少计时器并应用效果
        activeEffects.entrySet().removeIf(entry -> {
            UUID playerId = entry.getKey();
            int remainingTime = entry.getValue() - 1;

            if (remainingTime <= 0) {
                LOGGER.info("玩家 {} 的火焰爆发效果已结束", playerId);
                return true;
            }

            // 每3秒应用一次火焰爆发效果
            if (remainingTime % 60 == 0) {
                ServerPlayer player = server.getPlayerList().getPlayer(playerId);
                if (player != null) {
                    applyFireBurst(player);
                }
            }

            entry.setValue(remainingTime);
            return false;
        });
    }

    @Override
    public Consumer<RuneContext> getEffectConsumer() {
        return context -> {
            activateFireBurstEffect(context.getPlayer());
        };
    }
}

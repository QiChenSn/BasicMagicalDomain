package com.qichen.basicmagicaldomain.item.custom.rune;

import com.mojang.logging.LogUtils;
import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EarthRune extends MagicalRune {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 使用时长（单位：tick，60 tick = 3秒）
    private static final int USE_DURATION = 60;

    // 效果范围（以玩家为中心的半径）
    private final int range;

    // 效果持续时间（秒）
    private final int effectTime;

    // 存储玩家激活效果的计时器（玩家UUID -> 剩余时间）
    private static final Map<UUID, Integer> activeEffects = new HashMap<>();

    public EarthRune(Properties properties, int type, int range, int effectTime) {
        super(properties, MagicalRune.Earth, 5, range);
        this.range = range;
        this.effectTime = effectTime * 20; // 转换为tick
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> EARTH_RUNE = ITEMS.register("earth_rune",()->
            new EarthRune(new Item.Properties(), Earth, 30, 60) // 范围30格，持续60秒
    );

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        // 仅在服务器端启动使用过程
        if (!level.isClientSide) {
            LOGGER.info("开始蓄力");
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
            if (useTime >= USE_DURATION) {
                LOGGER.info("符文使用成功！");

                // 播放成功音效
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.ENCHANTMENT_TABLE_USE,
                        SoundSource.PLAYERS,
                        1.0F, 1.0F);

                // 激活植物生长效果
                activateGrowthEffect(player);

                // 消耗一个符文（可选）
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                LOGGER.info("蓄力中断，使用取消");
            }
        }
    }

    // 激活植物生长效果
    private void activateGrowthEffect(Player player) {
        UUID playerId = player.getUUID();

        // 设置效果持续时间
        activeEffects.put(playerId, effectTime);

        // 立即应用一次效果
        applyGrowthEffect(player);

        LOGGER.info("已激活植物生长效果，范围: {}格，持续时间: {}秒", range, effectTime / 20);
    }

    // 应用植物生长效果
    public static void applyGrowthEffect(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            BlockPos center = player.blockPosition();
            int range = 30; // 效果范围

            // 计算效果区域
            AABB area = new AABB(
                    center.getX() - range, center.getY() - 5, center.getZ() - range,
                    center.getX() + range, center.getY() + 5, center.getZ() + range
            );

            int plantsAffected = 0;

            // 遍历区域内的所有方块
            for (int x = (int) area.minX; x <= area.maxX; x++) {
                for (int y = (int) area.minY; y <= area.maxY; y++) {
                    for (int z = (int) area.minZ; z <= area.maxZ; z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        BlockState state = serverLevel.getBlockState(pos);

                        // 检查是否是植物
                        if (isPlant(state)) {
                            // 加速植物生长（施加额外的随机刻）
                            for (int i = 0; i < 10; i++) {
                                state.randomTick(serverLevel, pos, serverLevel.random);
                            }
                            plantsAffected++;
                        }
                    }
                }
            }

            LOGGER.info("加速了 {} 株植物的生长", plantsAffected);

            // 视觉效果（粒子效果）
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                    center.getX(), center.getY() + 1, center.getZ(),
                    100,
                    range, 2, range,
                    0.1
            );
        }
    }

    // 检查方块是否是植物
    private static boolean isPlant(BlockState state) {
        return state.is(BlockTags.CROPS) || // 农作物
                state.is(BlockTags.SAPLINGS) || // 树苗
                state.getBlock() == Blocks.SUGAR_CANE || // 甘蔗
                state.getBlock() == Blocks.CACTUS || // 仙人掌
                state.getBlock() == Blocks.BAMBOO || // 竹子
                state.getBlock() == Blocks.CHORUS_FLOWER || // 紫颂花
                state.getBlock() == Blocks.KELP || // 海带
                state.getBlock() == Blocks.KELP_PLANT || // 海带植物
                state.getBlock() == Blocks.NETHER_WART; // 地狱疣
    }

    // 在服务器tick中更新效果
    public static void onServerTick(MinecraftServer server) {
        // 每tick减少计时器并应用效果
        activeEffects.entrySet().removeIf(entry -> {
            UUID playerId = entry.getKey();
            int remainingTime = entry.getValue() - 1;

            if (remainingTime <= 0) {
                LOGGER.info("玩家 {} 的植物生长效果已结束", playerId);
                return true;
            }

            // 每2秒应用一次效果
            if (remainingTime % 40 == 0) {
                ServerPlayer player = server.getPlayerList().getPlayer(playerId);
                if (player != null) {
                    applyGrowthEffect(player);
                }
            }

            entry.setValue(remainingTime);
            return false;
        });
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 5201314;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // 使用弓箭的动画效果
    }
}
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
import java.util.function.Consumer;

public class EarthRune extends MagicalRune {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 存储玩家激活效果的计时器（玩家UUID -> 剩余时间）
    private static final Map<UUID, Integer> activeEffects = new HashMap<>();

    public EarthRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, MagicalRune.Earth, USE_DURATION,range,effect_time);
    }

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BasicMagicalDomain.MODID);
    public static final DeferredItem<Item> EARTH_RUNE = ITEMS.register("earth_rune",()->
            new EarthRune(new Item.Properties(), Earth, 60,16, 60*20) // 范围30格，持续60秒
    );



    // 激活植物生长效果
    private void activateGrowthEffect(Player player) {
        UUID playerId = player.getUUID();

        // 设置效果持续时间
        activeEffects.put(playerId, this.effect_time);

        // 立即应用一次效果
        applyGrowthEffect(player);

        //LOGGER.info("已激活植物生长效果，范围: {}格，持续时间: {}秒", range, this.effect_time / 20);
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

            //LOGGER.info("加速了 {} 株植物的生长", plantsAffected);

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
                //LOGGER.info("玩家 {} 的植物生长效果已结束", playerId);
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
    public Consumer<RuneContext> getEffectConsumer() {
        return context->{
            activateGrowthEffect(context.getPlayer());
        };
    }
}
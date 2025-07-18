package com.qichen.basicmagicaldomain.item.custom.rune;

import com.mojang.logging.LogUtils;
import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

public class EarthRune extends MagicalRune {
    private static final Logger LOGGER = LogUtils.getLogger();

    // 存储玩家激活效果的计时器（玩家UUID -> 剩余时间）
    private static final Map<UUID, Integer> activeEffects = new HashMap<>();

    public EarthRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, MagicalRune.Earth, USE_DURATION,range,effect_time);
    }


    @Override
    @OnlyIn(Dist.CLIENT) // 确保只在客户端执行
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        // 简略说明（始终显示）
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.title").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.function").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.range_duration").withStyle(ChatFormatting.GRAY));

        // 检测 Shift 键状态
        boolean isShiftDown = isShiftKeyPressed();

        if (isShiftDown) {
            addDetailedTooltip(tooltipComponents);
        } else {
            tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.hold_shift").withStyle(ChatFormatting.YELLOW));
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
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.main_function").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.accelerate_growth").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.range").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.duration").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.activation").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.applicable_plants").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.crops").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.cactus").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.nether").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.usage").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.hold_right").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.release").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.earth_rune.detailed.auto_accelerate").withStyle(ChatFormatting.GRAY));
    }


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

    public void applyOnAltar(Level level, BlockPos Pos) {
        if(level.isClientSide)return;
        BlockPos center = Pos;
        int range = this.range; // 效果范围

        // 计算效果区域
        AABB area = new AABB(
                center.getX() - range, center.getY() - 5, center.getZ() - range,
                center.getX() + range, center.getY() + 5, center.getZ() + range
        );

        int plantsAffected = 0;
        ServerLevel serverLevel= (ServerLevel) level;
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

    @Override
    public Consumer<RuneContext> getEffectConsumer() {
        return context->{
            activateGrowthEffect(context.getPlayer());
        };
    }
}
package com.qichen.basicmagicaldomain.item.custom.rune;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.util.ItemOperator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.lwjgl.glfw.GLFW;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.SharedConstants.TICKS_PER_SECOND;
import static net.minecraft.world.item.Items.NETHERITE_PICKAXE;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.minecraft.server.level.ServerPlayer;

public class MetalRune extends MagicalRune{

    public MetalRune(Properties properties, int type, int USE_DURATION, int range, int effect_time) {
        super(properties, type, USE_DURATION, range, effect_time);
    }

    @Override
    @OnlyIn(Dist.CLIENT) // 确保只在客户端执行
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        // 简略说明（始终显示）
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.title").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.function").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.range_duration").withStyle(ChatFormatting.GRAY));

        // 检测 Shift 键状态
        boolean isShiftDown = isShiftKeyPressed();

        if (isShiftDown) {
            addDetailedTooltip(tooltipComponents);
        } else {
            tooltipComponents.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.hold_shift").withStyle(ChatFormatting.YELLOW));
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
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.main_function").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.ore_detection").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.range").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.duration").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.activation").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.effects").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.auto_mining").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.random_drops").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.success_rate").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.usage").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.hold_right").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.release").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.basicmagicaldomain.metal_rune.detailed.auto_detect").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public Consumer<RuneContext> getEffectConsumer() {
        return context->{
            Player player=context.getPlayer();
//            //For test
//            List<BlockPos> oresInRange = findOresInRange(player, this.range);
//            for(BlockPos bps:oresInRange){
//                generateRandomDrop((ServerLevel) context.getLevel(),bps,player);
//            }
            startOreDetectionTask(player);
        };
    }

    private List<BlockPos> findOresInRange(LivingEntity entity, int range) {
        List<BlockPos> ores = new ArrayList<>();
        BlockPos center = entity.blockPosition();
        Level level = entity.level();

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    // 使用NeoForge矿石标签检测
                    if (state.is(Tags.Blocks.ORES)) {
                        ores.add(pos);
                    }
                }
            }
        }
        return ores;
    }
    private void generateRandomDrop(ServerLevel level, BlockPos pos, LivingEntity entity) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        // 创建掉落参数（模拟玩家开采）
        LootParams.Builder paramsBuilder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.TOOL, NETHERITE_PICKAXE.getDefaultInstance()); // 使用当前物品作为工具

        LootParams params = paramsBuilder.create(LootContextParamSets.BLOCK);

        // 获取随机掉落物
        List<ItemStack> drops = block.defaultBlockState().getDrops(paramsBuilder);
        if (!drops.isEmpty()) {
            ItemStack randomDrop = drops.get(level.random.nextInt(drops.size())).copy();
            randomDrop.setCount(1); // 每次只掉1个

            // 在玩家位置生成掉落物实体
            ItemEntity itemEntity = new ItemEntity(
                    level,
                    entity.getX(), entity.getY() + 0.5, entity.getZ(),
                    randomDrop
            );
            level.addFreshEntity(itemEntity);
        }
    }
    private void startOreDetectionTask(LivingEntity entity) {
        WeakReference<LivingEntity> entityRef = new WeakReference<>(entity);
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        int EFFECT_DURATION=this.effect_time;
        int RANGE=this.range;
        //成功概率
        double successProbability=0.5;
        // 定义递归任务
        Runnable task = new Runnable() {
            int secondsPassed = 0;

            @Override
            public void run() {
                LivingEntity livingEntity = entityRef.get();
                if (livingEntity == null || !livingEntity.isAlive() || secondsPassed >= EFFECT_DURATION) {
                    return; // 终止条件
                }

                Level level = livingEntity.level();
                if (level instanceof ServerLevel serverLevel) {
                    // 1. 检测范围内的矿石
                    List<BlockPos> ores = findOresInRange(livingEntity, RANGE);

                    // 2. 随机选择矿石并生成掉落物
                    //有百分之50的概率失败
                    if (!ores.isEmpty()&&secondsPassed%40==0&&Math.random()<successProbability) {
                        BlockPos randomOrePos = ores.get(level.random.nextInt(ores.size()));
                        generateRandomDrop(serverLevel, randomOrePos, livingEntity);
                    }
                }

                secondsPassed++;
                // 安排下一次执行（1秒后）
                server.tell(new TickTask(server.getTickCount() + TICKS_PER_SECOND, this));
            }
        };

        // 立即启动第一次任务
        server.tell(new TickTask(server.getTickCount(), task));
    }

    public static int timer=0;
    public void applyOnAltar(Level level, BlockPos pos, IItemHandler iItemHandler) {
        if (level.isClientSide) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        // 查找范围内所有矿石
        List<BlockPos> ores = new ArrayList<>();
        BlockPos center = pos;
        int range = 30;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos checkPos = center.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);
                    if (state.is(Tags.Blocks.ORES)) {
                        ores.add(checkPos);
                    }
                }
            }
        }

        // 随机插入容器
        timer++;
        timer %= 3;
        if (timer != 0) {
            return;
        }

        double successProbability = 0.5;
        if (!ores.isEmpty() && Math.random() < successProbability) {
            BlockPos randomOrePos = ores.get(level.random.nextInt(ores.size()));
            BlockState state = level.getBlockState(randomOrePos);
            // 创建伪玩家
            ServerPlayer fakePlayer = FakePlayerFactory.getMinecraft(serverLevel);
            LootParams.Builder paramsBuilder = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(randomOrePos))
                    .withParameter(LootContextParams.BLOCK_STATE, state)
                    .withParameter(LootContextParams.TOOL, NETHERITE_PICKAXE.getDefaultInstance())
                    .withParameter(LootContextParams.THIS_ENTITY, fakePlayer);
            List<ItemStack> drops = state.getDrops(paramsBuilder);
            if (!drops.isEmpty()) {
                ItemStack randomDrop = drops.get(level.random.nextInt(drops.size())).copy();
                randomDrop.setCount(1);
                ItemOperator.tryInsertItem(iItemHandler, randomDrop);
            }
        }
    }
}

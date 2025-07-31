package com.qichen.basicmagicaldomain.event;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;

import static com.qichen.basicmagicaldomain.BasicMagicalDomain.LOGGER;

@EventBusSubscriber(modid = BasicMagicalDomain.MODID)
public class RuneCraftEvent {

    // 1. 定义要检测的「源物品」（只检测这一种物品）
    private static final DeferredItem<Item> SOURCE_ITEM_REGISTRY = ModItemRegister.Magical_RUNE;

    // 2. 定义「方块类型→目标物品」的映射关系（可根据需求扩展）
    private static final Map<Block, DeferredItem<Item>> BLOCK_TO_RESULT_ITEM = new HashMap<>();

    // 静态初始化映射关系（方块→生成的物品）
    static {
        // 水元素符文
        BLOCK_TO_RESULT_ITEM.put(Blocks.WATER, ModItemRegister.WATER_RUNE);
        
        // 火元素符文
        BLOCK_TO_RESULT_ITEM.put(Blocks.LAVA, ModItemRegister.FIRE_RUNE);
        
        // 金元素符文 - 金属相关方块
        BLOCK_TO_RESULT_ITEM.put(Blocks.GOLD_BLOCK, ModItemRegister.METAL_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.IRON_BLOCK, ModItemRegister.METAL_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.COPPER_BLOCK, ModItemRegister.METAL_Rune);
        
        // 木元素符文 - 木材相关方块
        BLOCK_TO_RESULT_ITEM.put(Blocks.OAK_LOG, ModItemRegister.Wood_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.SPRUCE_LOG, ModItemRegister.Wood_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.BIRCH_LOG, ModItemRegister.Wood_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.JUNGLE_LOG, ModItemRegister.Wood_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.ACACIA_LOG, ModItemRegister.Wood_Rune);
        BLOCK_TO_RESULT_ITEM.put(Blocks.DARK_OAK_LOG, ModItemRegister.Wood_Rune);
        
        // 土元素符文 - 土壤相关方块
        BLOCK_TO_RESULT_ITEM.put(Blocks.DIRT, ModItemRegister.EARTH_RUNE);
        BLOCK_TO_RESULT_ITEM.put(Blocks.STONE, ModItemRegister.EARTH_RUNE);
        BLOCK_TO_RESULT_ITEM.put(Blocks.SAND, ModItemRegister.EARTH_RUNE);
        BLOCK_TO_RESULT_ITEM.put(Blocks.CLAY, ModItemRegister.EARTH_RUNE);
    }

    @SubscribeEvent
    public static void checkSourceItemAndGenerate(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        Level level = entity.level();

        // 仅在服务器端处理（避免客户端生成物品导致同步问题）
        if (level.isClientSide()) {
            return;
        }

        // 过滤：仅处理「源物品」的实体
        if (!(entity instanceof ItemEntity itemEntity)) {
            return;
        }
        ItemStack sourceStack = itemEntity.getItem();
        if (!sourceStack.is(SOURCE_ITEM_REGISTRY.get())) {
            return; // 不是要检测的源物品，直接跳过
        }

        // 3. 获取源物品位置，检测物品所在位置和下面的方块
        BlockPos sourcePos = itemEntity.blockPosition();
        Block matchedBlock = null;
        BlockPos matchedBlockPos = null;

        // 检查物品所在位置的方块
        Block currentBlock = level.getBlockState(sourcePos).getBlock();
        if (BLOCK_TO_RESULT_ITEM.containsKey(currentBlock)) {
            matchedBlock = currentBlock;
            matchedBlockPos = sourcePos;
        }
        
        // 如果物品所在位置没有匹配的方块，检查下面的方块
        if (matchedBlock == null) {
            BlockPos belowPos = sourcePos.below();
            Block belowBlock = level.getBlockState(belowPos).getBlock();
            if (BLOCK_TO_RESULT_ITEM.containsKey(belowBlock)) {
                matchedBlock = belowBlock;
                matchedBlockPos = belowPos;
            }
        }

        // 4. 若找到匹配的方块，生成对应物品并消耗源物品和方块
        if (matchedBlock != null && matchedBlockPos != null) {
            DeferredItem<Item> resultItemRegistry = BLOCK_TO_RESULT_ITEM.get(matchedBlock);
            if (resultItemRegistry != null) {
                // 在需要时才调用 get() 方法获取物品实例
                Item resultItem = resultItemRegistry.get();
                if (resultItem != null) {
                    // 消耗匹配的方块（替换为空气）
                    level.setBlock(matchedBlockPos, Blocks.AIR.defaultBlockState(), 3);
                    
                    // 生成目标物品实体（位置与源物品相同）
                    ItemEntity resultEntity = new ItemEntity(
                            level,
                            sourcePos.getX() + 0.5, // 中心坐标
                            sourcePos.getY() + 0.5,
                            sourcePos.getZ() + 0.5,
                            new ItemStack(resultItem)
                    );
                    level.addFreshEntity(resultEntity);

                    // 消耗源物品（移除源物品实体）
                    itemEntity.discard(); // 直接删除源物品
                }
            }
        }
    }
}
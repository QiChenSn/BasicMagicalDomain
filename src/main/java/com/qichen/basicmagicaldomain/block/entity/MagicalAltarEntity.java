package com.qichen.basicmagicaldomain.block.entity;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.ModBlockRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import com.qichen.basicmagicaldomain.screen.custom.MagicalAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.qichen.basicmagicaldomain.block.custom.MagicalAltar;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MagicalAltarEntity extends BlockEntity implements MenuProvider {
    public MagicalAltarEntity(BlockPos pos, BlockState blockState) {
        super(MAGICAL_ALTAR_ENTITY.get(), pos, blockState);
    }

    // 在模组初始化类中定义注册器
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BasicMagicalDomain.MODID);

    public static final Supplier<BlockEntityType<MagicalAltarEntity>> MAGICAL_ALTAR_ENTITY = BLOCK_ENTITY_TYPES.register(
            "magical_altar_entity",
            () -> BlockEntityType.Builder.of(
                    MagicalAltarEntity::new, // 实体构造函数
                    ModBlockRegister.MAGICAL_ALTAR.get() // 关联的方块
            ).build(null)
    );

    //实现物品存储
    private static final int INVENTORY_SIZE = 1;
    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            // 当物品栏内容发生变化时，这个方法会被调用
            // 我们需要在这里调用 setChanged() 来通知Minecraft该方块实体的数据已更新，需要保存
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return this.getStackInSlot(slot).isEmpty()&&stack.getItem() instanceof MagicalRune rune;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public void clearContents() {
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    // 提供一个公共方法来获取物品栏处理器，方便从其他地方访问
    public IItemHandler getItemHandler() {
        return itemHandler;
    }
    // --- 数据持久化 ---

    /**
     * 当世界保存时，这个方法被调用，用于将方块实体的数据写入NBT
     *
     * @param pTag        NBT标签，我们将把数据写入这里
     * @param pRegistries 注册表查找
     */
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        // 调用父类的方法，这是一个好习惯
        super.saveAdditional(pTag, pRegistries);
        // 将我们的 itemHandler 序列化（保存）到NBT中
        // "inventory" 是一个自定义的键名，你可以任意命名
        pTag.put("rune", itemHandler.serializeNBT(pRegistries));
    }

    /**
     * 当世界加载时，这个方法被调用，用于从NBT中读取方块实体的数据
     *
     * @param pTag        包含方块实体数据的NBT标签
     * @param pRegistries 注册表查找
     */
    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        // 调用父类的方法
        super.loadAdditional(pTag, pRegistries);
        // 检查NBT中是否包含我们的物品栏数据
        if (pTag.contains("rune")) {
            // 如果有，就反序列化（加载）数据到我们的 itemHandler 中
            itemHandler.deserializeNBT(pRegistries, pTag.getCompound("rune"));
        }
    }

    //根据物品实现逻辑
    static int tickcount=0;
    public static void tick(Level level, BlockPos pos, BlockState state, MagicalAltarEntity be) {
        // 从物品栏的第一个（也是唯一一个）槽位获取物品
        ItemStack stack = be.itemHandler.getStackInSlot(0);

        if (stack.isEmpty()) {
            return; // 后面逻辑不执行
        }
        tickcount++;
        tickcount%=20;
        if(tickcount!=0)return;
        // 判断放入的是否是符文
        if (stack.getItem() instanceof MagicalRune rune) {
            if (rune instanceof FireRune) {
                // 火符文效果
                ((FireRune) rune).applyOnAltar(level,pos);
            } else if (rune instanceof WaterRune) {
                // 水符文效果
                ((WaterRune) rune).applyOnAltar(level,pos);
            } else if (rune instanceof EarthRune) {
                // 土符文效果
                ((EarthRune) rune).applyOnAltar(level,pos);
            } else if (rune instanceof MetalRune) {
                // 金符文效果
                // 遍历六个方向，查找第一个有IItemHandler能力的方块实体
                IItemHandler foundHandler = null;
                for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.values()) {
                    BlockPos neighborPos = pos.relative(direction);
                    IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, neighborPos, direction.getOpposite());
                    if (handler != null) {
                        foundHandler = handler;
                        break;
                    }
                }
                if (foundHandler != null) {
                    ((MetalRune) rune).applyOnAltar(level, pos, foundHandler);
                }
            } else if (rune instanceof WoodRune) {
                // 木符文效果
                ((WoodRune) rune).applyOnAltar(level,pos);
            } else {
                // 其他符文（如魔法符文等）效果
            }
        }
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Magical Altar");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MagicalAltarMenu(containerId, playerInventory, this);
    }
}
package com.qichen.basicmagicaldomain.util;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class ItemOperator {
    public static boolean tryInsertItem(IItemHandler handler, ItemStack stack) {
        // 创建物品副本避免修改原堆叠
        ItemStack toInsert = stack.copy();

        // 尝试插入物品
        for (int slot = 0; slot < handler.getSlots() && !toInsert.isEmpty(); slot++) {
            // 模拟插入测试是否可行
            ItemStack simulated = handler.insertItem(slot, toInsert, true);

            // 如果模拟成功（返回空或部分剩余）
            if (simulated.getCount() < toInsert.getCount()) {
                // 实际执行插入
                toInsert = handler.insertItem(slot, toInsert, false);
            }
        }
        return toInsert.isEmpty();
    }
}

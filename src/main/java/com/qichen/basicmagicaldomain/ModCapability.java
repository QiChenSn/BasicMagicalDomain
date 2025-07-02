package com.qichen.basicmagicaldomain;

import com.qichen.basicmagicaldomain.block.entity.MagicalAltarEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = BasicMagicalDomain.MODID)
public class ModCapability {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // 为我们的方块实体注册 ITEM capability
        // 这会告诉NeoForge，MyChestBlockEntity 类型的方块实体可以提供一个 IItemHandler
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK, // 我们要注册的是方块的物品处理能力
                MagicalAltarEntity.MAGICAL_ALTAR_ENTITY.get(), // 目标方块实体的类型
                // 下面是一个 lambda 表达式，定义了如何从方块实体实例中获取 IItemHandler
                // (blockEntity, context) -> blockEntity.getItemHandler()
                // 当外部（如漏斗）请求物品能力时，这个表达式会被执行
                // 它会调用我们之前在 MyChestBlockEntity 中写的 getItemHandler() 方法
                (be, context) -> be.getItemHandler()
        );
    }
}

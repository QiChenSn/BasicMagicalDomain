package com.qichen.basicmagicaldomain.block;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.entity.MagicalAltarEntity;
import com.qichen.basicmagicaldomain.item.ModItemRegister;
import com.qichen.basicmagicaldomain.item.custom.rune.*;
import net.neoforged.bus.api.IEventBus;
import com.qichen.basicmagicaldomain.block.custom.MaigalAltar;

public class ModBlockRegister {
    public static void register(IEventBus eventBus){
        MaigalAltar.BLOCKS.register(eventBus);
        MagicalAltarEntity.BLOCK_ENTITY_TYPES.register(eventBus);
    }
}

package com.qichen.basicmagicaldomain.block;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.custom.MagicalAltar;
import com.qichen.basicmagicaldomain.block.entity.MagicalAltarEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockRegister {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BasicMagicalDomain.MODID);
    public static final DeferredBlock<MagicalAltar> MAGICAL_ALTAR =
            BLOCKS.register("magical_altar", () -> new MagicalAltar(BlockBehaviour.Properties.of()
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 7)));

    public static void register(IEventBus eventBus){
        ModBlockRegister.BLOCKS.register(eventBus);
        MagicalAltarEntity.BLOCK_ENTITY_TYPES.register(eventBus);
    }
}

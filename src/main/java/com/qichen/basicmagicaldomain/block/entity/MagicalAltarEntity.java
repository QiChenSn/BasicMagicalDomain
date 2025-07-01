package com.qichen.basicmagicaldomain.block.entity;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.qichen.basicmagicaldomain.block.custom.MaigalAltar;
import java.util.function.Supplier;

public class MagicalAltarEntity extends BlockEntity {
    public MagicalAltarEntity( BlockPos pos, BlockState blockState) {
        super(MAGICAL_ALTAR_ENTITY.get(), pos, blockState);
    }
    // 在模组初始化类中定义注册器
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BasicMagicalDomain.MODID);

    public static final Supplier<BlockEntityType<MagicalAltarEntity>> MAGICAL_ALTAR_ENTITY = BLOCK_ENTITY_TYPES.register(
            "magical_altar_entity",
            () -> BlockEntityType.Builder.of(
                    MagicalAltarEntity::new, // 实体构造函数
                    MaigalAltar.MAGICAL_ALTAR.get() // 关联的方块
            ).build(null)
    );
}

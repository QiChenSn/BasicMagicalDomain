package com.qichen.basicmagicaldomain.block.custom;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.entity.MagicalAltarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class MaigalAltar extends Block implements EntityBlock {
    public MaigalAltar(Properties properties) {
        super(properties);
    }

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BasicMagicalDomain.MODID);
    public static final DeferredBlock<MaigalAltar> MAGICAL_ALTAR =
            BLOCKS.register("magical_altar", () -> new MaigalAltar(BlockBehaviour.Properties.of()
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.GRAVEL)
                    .lightLevel(state -> 7)));
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagicalAltarEntity(blockPos,blockState);
    }
}

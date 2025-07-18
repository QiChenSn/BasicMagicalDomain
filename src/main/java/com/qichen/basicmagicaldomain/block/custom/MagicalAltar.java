package com.qichen.basicmagicaldomain.block.custom;

import com.qichen.basicmagicaldomain.BasicMagicalDomain;
import com.qichen.basicmagicaldomain.block.ModBlockRegister;
import com.qichen.basicmagicaldomain.block.entity.MagicalAltarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class MagicalAltar extends Block implements EntityBlock {
    public MagicalAltar(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagicalAltarEntity(blockPos,blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (blockEntityType == MagicalAltarEntity.MAGICAL_ALTAR_ENTITY.get()) {
            // 返回方块实体类的静态tick方法，参数需匹配BlockEntityTicker接口
            return (BlockEntityTicker<T>) (BlockEntityTicker<MagicalAltarEntity>) MagicalAltarEntity::tick;
        }
        return null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof MagicalAltarEntity magicalAltarEntity) {
            if(!level.isClientSide()) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(magicalAltarEntity, Component.literal("MagicalAltar")), pos);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}

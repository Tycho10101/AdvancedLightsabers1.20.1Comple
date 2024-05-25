package com.stelmods.lightsabers.common.block;


import com.stelmods.lightsabers.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockLightsaberForge extends BaseEntityBlock
{

    public Block block;

    public BlockLightsaberForge() {
        super(BlockBehaviour.Properties.of().strength(1.0F, 10.0F));
    }

    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModEntities.LIGHTSABER_FORGE.get().create(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide)
            return InteractionResult.SUCCESS;
        MenuProvider namedContainerProvider = this.getMenuProvider(state, worldIn, pos);
        if (!(player instanceof ServerPlayer))
            return InteractionResult.FAIL;
        NetworkHooks.openScreen((ServerPlayer) player, namedContainerProvider, (packetBuffer) -> {
            packetBuffer.writeBlockPos(pos);
        });
        return InteractionResult.SUCCESS;
    }
}

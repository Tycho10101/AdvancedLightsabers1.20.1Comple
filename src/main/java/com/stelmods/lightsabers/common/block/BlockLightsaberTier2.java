package com.stelmods.lightsabers.common.block;

import com.stelmods.lightsabers.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class BlockLightsaberTier2 extends BlockLightsaberForge{
    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModEntities.LIGHTSABER_FORGE_Tier2.get().create(pPos, pState);
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

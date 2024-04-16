package com.fiskmods.lightsabers.common.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityLightsaberForge extends BlockEntity
{
    public TileEntityLightsaberForge(BlockEntityType<?> type, BlockPos bPos, BlockState bState) {
		super(type, bPos, bState);
	}

	@Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(1.5, 0.5, 1.5);
    }
}

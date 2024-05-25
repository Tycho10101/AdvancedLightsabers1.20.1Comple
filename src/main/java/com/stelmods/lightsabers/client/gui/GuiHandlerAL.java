package com.stelmods.lightsabers.client.gui;

import com.stelmods.lightsabers.common.block.BlockLightsaberForge;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.container.*;
import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import com.stelmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.stelmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.stelmods.lightsabers.common.tileentity.TileEntitySithCoffin;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerAL implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
        case 0:
            return world.getBlock(x, y, z) instanceof BlockLightsaberForge ? new LightsaberForgeContainer(player.inventory, (LightsaberForgeBlockEntity) tile) : null;
        case 1:
            return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new ContainerSithCoffin(player.inventory, (TileEntitySithCoffin) tile) : null;
        case 3:
            return new ContainerCrystalPouch(player.inventory, new InventoryCrystalPouch(player, x));
        case 4:
            return tile instanceof TileEntityDisassemblyStation ? new ContainerDisassemblyStation(player.inventory, (TileEntityDisassemblyStation) tile) : null;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        switch (id)
        {
        case 0:
            return world.getBlock(x, y, z) instanceof BlockLightsaberForge ? new LightsaberForgeScreen(player.inventory, (LightsaberForgeBlockEntity) tile) : null;
        case 1:
            return world.getBlock(x, y, z) == ModBlocks.sithCoffin ? new GuiSithCoffin(player.inventory, (TileEntitySithCoffin) tile) : null;
        case 2:
            return world.getBlock(x, y, z) == ModBlocks.holocron ? new GuiForcePowers(null, player, (TileEntityHolocron) tile) : null;
        case 3:
            return new GuiCrystalPouch(player.inventory, new InventoryCrystalPouch(player, x));
        case 4:
            return tile instanceof TileEntityDisassemblyStation ? new GuiDisassemblyStation(player.inventory, (TileEntityDisassemblyStation) tile) : null;
        }

        return null;
    }
}

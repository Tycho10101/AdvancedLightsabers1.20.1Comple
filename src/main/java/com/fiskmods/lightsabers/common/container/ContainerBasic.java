package com.fiskmods.lightsabers.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ContainerBasic<T extends BlockEntity> extends AbstractContainerMenu
{
    protected final T tileentity;
    protected final Level worldObj;

    public ContainerBasic(T tile, MenuType<?> menuType, int containerId)
    {
    	super(menuType, containerId);
    	
        tileentity = tile;
        worldObj = tile != null ? tile.getLevel() : null;
    }

    public void addPlayerInventory(Inventory inventoryPlayer, int yOffset)
    {
        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlot(makeInventorySlot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + yOffset + i * 18));
            }
        }
        
        for (i = 0; i < 9; ++i)
        {
        	this.addSlot(makeInventorySlot(inventoryPlayer, i, 8 + i * 18, 142 + yOffset));
        }
    }

    public Slot makeInventorySlot(Inventory inventoryPlayer, int index, int x, int y)
    {
        return new Slot(inventoryPlayer, index, x, y);
    }

    @Override
    public boolean stillValid(Player player)
    {
        if (tileentity != null)
        {
            if (tileentity instanceof Container)
            {
                return ((Container)tileentity).stillValid(player);
            }

            return player.distanceToSqr(tileentity.getBlockPos().getX() + 0.5D, tileentity.getBlockPos().getY() + 0.5D, tileentity.getBlockPos().getZ() + 0.5D) <= 64.0D;
        }

        return true;
    }
    
    @Override
    protected boolean moveItemStackTo(ItemStack stackToMove, int fromId, int toId, boolean descending)
    {
        return moveItemStackTo(stackToMove, fromId, toId, descending, false);
    }
    
    protected boolean moveItemStackTo(ItemStack stackToMove, int fromId, int toId, boolean descending, boolean check)
    {
        boolean success = false;
        int id = fromId;

        if (descending)
        {
            id = toId - 1;
        }

        Slot slot;
        ItemStack dstStack;

        if (stackToMove.isStackable())
        {
            while (stackToMove.getCount() > 0 && (!descending && id < toId || descending && id >= fromId))
            {
                slot = this.slots.get(id);
                dstStack = slot.getItem();

                if ((!check || slot.mayPlace(stackToMove)) && dstStack != null && dstStack.getItem() == stackToMove.getItem() && (/*!stackToMove.getHasSubtypes() || */stackToMove.getDamageValue() == dstStack.getDamageValue()) && ItemStack.isSameItemSameTags(stackToMove, dstStack))
                {
                    int maxStackSize = Math.min(slot.container.getMaxStackSize(), Math.min(dstStack.getMaxStackSize(), slot.getMaxStackSize()));
                    int combinedStackSize = dstStack.getCount() + stackToMove.getCount();

                    if (combinedStackSize <= maxStackSize)
                    {
                        stackToMove.setCount(0);
                        dstStack.setCount(combinedStackSize);
                        slot.setChanged();
                        success = true;
                    }
                    else if (dstStack.getCount() < maxStackSize)
                    {
                        stackToMove.shrink(maxStackSize - dstStack.getCount());
                        dstStack.setCount(maxStackSize);
                        slot.setChanged();
                        success = true;
                    }
                }

                if (descending)
                {
                    --id;
                }
                else
                {
                    ++id;
                }
            }
        }

        if (stackToMove.getCount() > 0)
        {
            if (descending)
            {
                id = toId - 1;
            }
            else
            {
                id = fromId;
            }

            while (!descending && id < toId || descending && id >= fromId)
            {
                slot = this.slots.get(id);
                dstStack = slot.getItem();

                if ((!check || slot.mayPlace(stackToMove)) && dstStack == null)
                {
                    int maxStackSize = Math.min(slot.container.getMaxStackSize(), Math.min(stackToMove.getMaxStackSize(), slot.getMaxStackSize()));
                    ItemStack itemstack1 = stackToMove.copy();
                    itemstack1.setCount(Math.min(maxStackSize, itemstack1.getCount()));
                    slot.set(itemstack1);

                    maxStackSize = Math.min(slot.container.getMaxStackSize(), Math.min(slot.getItem().getMaxStackSize(), slot.getMaxStackSize()));
                    stackToMove.setCount(Math.max(stackToMove.getCount() - itemstack1.getCount(), 0));
                    slot.setChanged();
                    success = true;
                    break;
                }

                if (descending)
                {
                    --id;
                }
                else
                {
                    ++id;
                }
            }
        }

        return success;
    }
}

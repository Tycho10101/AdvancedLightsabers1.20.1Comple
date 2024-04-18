package com.fiskmods.lightsabers.common.container;

import java.util.Arrays;
import java.util.List;

import com.fiskmods.lightsabers.common.item.ILightsaberComponent;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryLightsaberForge implements Container
{
    private List<ItemStack> inventory = Arrays.asList(new ItemStack[8]);
    private ContainerLightsaberForge eventHandler;

    public LightsaberData result;

    public InventoryLightsaberForge(ContainerLightsaberForge container)
    {
        eventHandler = container;
    }

    public LightsaberData updateResult()
    {
        long hash = 0;
        
        for (int slot = 0; slot < getContainerSize(); ++slot)
        {
            ItemStack stack = getItem(slot);
            
            if (stack != null && stack.getItem() instanceof ILightsaberComponent)
            {
                ILightsaberComponent component = (ILightsaberComponent) stack.getItem();
                long fingerprint = component.getFingerprint(stack, slot);
                
                if (!component.isCompatibleSlot(stack, slot) || fingerprint != 0 && hash == (hash |= fingerprint))
                {
                    return null;
                }
                
                continue;
            }
            else if (stack == null && (slot == 6 || slot == 7))
            {
                continue;
            }
            
            if (slot != 5 || stack == null || !stack.is(ItemTags.FISHES))
            {
                return null;
            }
        }

        return new LightsaberData(hash);
    }

    @Override
    public int getContainerSize()
    {
        return this.inventory.size();
    }

    @Override
    public ItemStack getItem(int slot)
    {
        return slot >= getContainerSize() ? null : inventory.get(slot);
    }
    
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (inventory.get(slot) != null)
        {
            ItemStack itemstack = inventory.get(slot);
            inventory.set(slot, null);
            return itemstack;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        if (inventory.get(slot) != null)
        {
            ItemStack itemstack;

            if (inventory.get(slot).getCount() <= amount)
            {
                itemstack = inventory.get(slot);
                inventory.set(slot, null);
                eventHandler.slotsChanged(this);

                return itemstack;
            }
            else
            {
                itemstack = inventory.get(slot).split(amount);

                if (inventory.get(slot).getCount() == 0)
                {
                	inventory.set(slot, null);
                }

                eventHandler.slotsChanged(this);

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setItem(int slot, ItemStack itemstack)
    {
        inventory.set(slot, itemstack);
        eventHandler.slotsChanged(this);
    }

    @Override
    public void setChanged()
    {
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

	@Override
	public void clearContent() {
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack removeItemNoUpdate(int p_18951_) {
		return null;
	}
}

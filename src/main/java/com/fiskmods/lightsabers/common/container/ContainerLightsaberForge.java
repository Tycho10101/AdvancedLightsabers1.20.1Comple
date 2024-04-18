package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.item.ILightsaberComponent;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerLightsaberForge extends ContainerBasic<TileEntityLightsaberForge>
{
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public Container craftResult = new ResultContainer();

    public static final int[][] SLOTS = {{20, 17}, {20, 35}, {20, 53}, {20, 71}, {43, 71}, {66, 71}, {89, 71}, {107, 71}};
    
    public ContainerLightsaberForge(int id, Inventory inventoryPlayer)
    {
    	this(id, inventoryPlayer, (TileEntityLightsaberForge)null);
    }
    
    public ContainerLightsaberForge(int id, Inventory inventoryPlayer, TileEntityLightsaberForge tile)
    {
        super(tile, ALMenuType.LIGHTSABER_FORGE.get(), id);
        
        for (int slot = 0; slot < SLOTS.length; ++slot)
        {
            this.addSlot(new Input(slot, SLOTS[slot][0], SLOTS[slot][1]));
        }
        
        this.addSlot(new Output(0, 136, 87));
        addPlayerInventory(inventoryPlayer, 30);
        slotsChanged(craftMatrix);
    }
    
    static ItemStack setActive(ItemStack itemstack, boolean state)
    {
        itemstack.getOrCreateTag().putBoolean("active", state);
        
        return itemstack;
    }
    
    static ItemStack create()
    {
        ItemStack itemstack = new ItemStack(Items.STICK);
        
        CompoundTag tag = itemstack.getOrCreateTag();
        tag.putLong(ALConstants.TAG_LIGHTSABER, 0);
        
        return itemstack;
    }
    
	@Override
    public void slotsChanged(Container inventory)
    {
        craftMatrix.result = craftMatrix.updateResult();
        ItemStack result = craftMatrix.result == null ? null : setActive(create(), true);
        														//ItemLightsaberBase.setActive(craftMatrix.result.create(), true);
        
        if (result != null)
        {
            ItemStack itemstack = craftMatrix.getItem(5);
            
            if (itemstack != null && itemstack.is(ItemTags.FISHES))
            {
            	itemstack.getItem().getDescriptionId();
            	
                result.getTag().putString(ALConstants.TAG_LIGHTSABER_SPECIAL, ForgeRegistries.ITEMS.getKey(itemstack.getItem()).toString());
            }
        }
        
        craftResult.setItem(0, result);
    }
    
    @Override
    public void removed(Player player)
    {
        super.removed(player);

        if (!worldObj.isClientSide)
        {
            for (int i = 0; i < craftMatrix.getContainerSize(); ++i)
            {
                ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                	player.drop(itemstack, false);
                }
            }
        }
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.slots.get(slotId);
        int OUTPUT = 8;

        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (slotId == OUTPUT)
            {
                if (!moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 36 + 1, true))
                {
                    return null;
                }
                
                //slot.onSlotChange(itemstack1, itemstack);
                slot.onQuickCraft(itemstack1, itemstack);
            }
            else if (slotId > OUTPUT)
            {
                boolean flag = true;

                for (int i = 0; i < OUTPUT; ++i)
                {
                    Slot slot1 = ((Slot) this.slots.get(i));
                    Item item = itemstack1.getItem();

                    if (item instanceof ILightsaberComponent && ((ILightsaberComponent) item).isCompatibleSlot(itemstack, i))
                    {
                        if (!slot1.hasItem() && !moveItemStackTo(itemstack1, i, i + 1, false, true))
                        {
                            return null;
                        }
                        
                        flag = false;
                    }
                }
                
                if (flag)
                {
                    if (slotId >= OUTPUT + 1 && slotId < OUTPUT + 28)
                    {
                        if (!moveItemStackTo(itemstack1, OUTPUT + 28, OUTPUT + 37, false))
                        {
                            return null;
                        }
                    }
                    else if (slotId >= OUTPUT + 28 && slotId < OUTPUT + 37 && !moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 28, false))
                    {
                        return null;
                    }
                }
            }
            else if (!moveItemStackTo(itemstack1, OUTPUT + 1, OUTPUT + 37, false))
            {
                return null;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.set((ItemStack) null);
            }
            else
            {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return null;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemstack, Slot slot)
    {
        return slot.container != craftResult && super.canTakeItemForPickAll(itemstack, slot);
    }
    
    private class Input extends Slot
    {
        public Input(int id, int x, int y)
        {
            super(craftMatrix, id, x, y);
            
            this.getNoItemIcon();
        }

        @Override
        public int getMaxStackSize()
        {
            return 1;
        }

        @Override
        public boolean mayPlace(ItemStack itemstack)
        {
            Item item = itemstack.getItem();
            
            if (!(item instanceof ILightsaberComponent) || !((ILightsaberComponent) item).isCompatibleSlot(itemstack, getSlotIndex()))
            {
                if (getSlotIndex() != 5 || itemstack.is(ItemTags.FISHES))
                {
                    return false;
                }
            }
            
            for (int slot = 0; slot < container.getContainerSize(); ++slot)
            {
                ItemStack stack = container.getItem(slot);
                
                if (stack != null && stack.getItem() == item && (stack.isDamageableItem() || stack.getDamageValue() == itemstack.getDamageValue()))
                {
                    return false;
                }
            }
            
            return true;
        }
        
        /**
        @Override
        @OnlyIn(Dist.CLIENT)
        public IIcon getBackgroundIconIndex()
        {
            return getSlotIndex() == 6 || getSlotIndex() == 7 ? ItemFocusingCrystal.outlineIcon : null;
        }
        **/
    }
    
    private class Output extends Slot
    {
        public Output(int id, int x, int y)
        {
            super(craftResult, id, x, y);
        }

        @Override
        public boolean mayPickup(Player player)
        {
            return craftMatrix.result != null && !craftMatrix.result.isTooShort();
        }

        @Override
        public boolean mayPlace(ItemStack itemstack)
        {
            return false;
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
        	net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);
        	
        	itemstack.onCraftedBy(worldObj, player, 1);
            //onCrafting(itemstack);

            for (int i = 0; i < craftMatrix.getContainerSize(); ++i)
            {
                ItemStack itemstack1 = craftMatrix.getItem(i);

                if (itemstack1 != null)
                {
                    craftMatrix.removeItem(i, 1);
                }
            }
            
            //ItemLightsaberBase.setActive(itemstack, false);
        }
    }
}

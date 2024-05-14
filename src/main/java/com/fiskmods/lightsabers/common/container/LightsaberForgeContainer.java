package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.LightsaberDoubleItem;
import com.fiskmods.lightsabers.common.item.LightsaberItem;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.item.parts.LightsaberBody;
import com.fiskmods.lightsabers.common.item.parts.LightsaberEmiter;
import com.fiskmods.lightsabers.common.item.parts.LightsaberPommel;
import com.fiskmods.lightsabers.common.item.parts.LightsaberSwitch;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberType;
import com.fiskmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class LightsaberForgeContainer extends AbstractContainerMenu
{
    public final LightsaberForgeBlockEntity TE;
    private final ContainerLevelAccess canInteractWith;
    public InventoryLightsaberForge craftMatrix = new InventoryLightsaberForge(this);
    public Container craftResult = new ResultContainer();

    public static final int[][] SLOTS = { {43, 71}, {89, 71}, {107, 71}};
    public static List<Slot> inputSlots = new ArrayList<>();
    private Slot outputSlot;
    private Slot bodySlot;
    private Slot switchSlot;
    private Slot emitterSlot;
    private Slot pommelSlot;
    private Slot crystalSlot;

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer)
    {
    	this(id, inventoryPlayer, (LightsaberForgeBlockEntity) null);
    }

    public LightsaberForgeContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf buf) {
        this(windowId, playerInventory, getTileEntity(playerInventory, buf));
    }

    public LightsaberForgeContainer(int id, Inventory inventoryPlayer, LightsaberForgeBlockEntity tile)
    {
        super( ModContainers.LIGHTSABER_FORGE.get(), id);
        inputSlots.clear();
        TE = tile;
        canInteractWith = ContainerLevelAccess.create(TE.getLevel(), TE.getBlockPos());
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                emitterSlot=  addSlot(new Input(iih, 4,20, 17, LightsaberEmiter.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                switchSlot = addSlot(new Input(iih, 5,20, 35, LightsaberSwitch.class) ));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                bodySlot = addSlot(new Input(iih, 6,20, 53, LightsaberBody.class)));
        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                pommelSlot = addSlot(new Input(iih, 7,20, 71, LightsaberPommel.class)));

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
               crystalSlot= addSlot(new SlotItemHandler(iih, 3,66, 71) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ItemCrystal;
                    }
                }));

        for (int slot = 0; slot < SLOTS.length; ++slot)
        {
            int finalSlot = slot;
            int x = SLOTS[finalSlot][0];
            int y = SLOTS[finalSlot][1];
            TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih ->
                    addSlot(new SlotItemHandler(iih, finalSlot, x, y) {
                        @Override
                        public boolean mayPlace(ItemStack stack) {
                            return true;
                        }
                    }));
        }

        TE.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iih -> outputSlot = addSlot(new Output( 8, 136, 87)));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) { addSlot(new Slot(inventoryPlayer, i, 8 + i * 18, 172));}
        inputSlots.add(bodySlot);
        inputSlots.add(switchSlot);
        inputSlots.add(emitterSlot);
        inputSlots.add(pommelSlot);

        slotsChanged(craftMatrix);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    private static LightsaberForgeBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf buf) {
        final BlockEntity te = playerInventory.player.level().getBlockEntity(buf.readBlockPos());
        if (te instanceof LightsaberForgeBlockEntity) {
            return (LightsaberForgeBlockEntity) te;
        }
        throw new IllegalStateException("Tile Entity mismatch with container");
    }
    
    private static ItemStack create()
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

        //craftResult.setItem(0, result);
    }

    private static ItemStack setActive(ItemStack itemstack, boolean state)
    {
        itemstack.getOrCreateTag().putBoolean("active", state);

        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWith, playerIn, ModBlocks.lightsaberForge.get());
    }

    /*
    @Override
    public ItemStack quickMoveStack(Player player, int slotId)
    {
        ItemStack itemstack = null;
        Slot slot = this.slots.get(slotId);
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
                    Slot slot1 = this.slots.get(i);
                    Item item = itemstack1.getItem();

                    if (item instanceof ILightsaberComponent && ((ILightsaberComponent) item).isCompatibleSlot(itemstack, i))
                    {
                        if (!slot1.hasItem() && !moveItemStackTo(itemstack1, i, i + 1, false))
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
                slot.set(null);
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
*/
    @Override
    public boolean canTakeItemForPickAll(ItemStack itemstack, Slot slot)
    {
        return slot.container != craftResult && super.canTakeItemForPickAll(itemstack, slot);
    }

    private class Input extends SlotItemHandler
    {
        Class c;
        public Input(IItemHandler iih, int id, int x, int y, Class c)
        {
            super(iih, id, x, y);
            this.c = c;
        }

        @Override
        public int getMaxStackSize()
        {
            return 1;
        }

        @Override
        public boolean mayPlace(ItemStack itemstack)
        {
                 return c.isInstance(itemstack.getItem());
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
            net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);

            itemstack.onCraftedBy(player.level(), player, 1);
            if(!player.level().isClientSide) {
                outputSlot.remove(1);
            }
        }

        @Override
        public void setChanged() {
            super.setChanged();
            boolean isCrafted = true;
            for (Slot inputSlot : inputSlots) {
                if (inputSlot.getItem().getItem() == Items.AIR)
                    isCrafted = false;
            }
            if(isCrafted)
            {
                ItemStack stack = new ItemStack(ModItems.lightsaber.get());
                stack.setTag(new CompoundTag());

                stack.getTag().putString("emitter", ForgeRegistries.ITEMS.getKey(emitterSlot.getItem().getItem()).toString());
                stack.getTag().putString("grip", ForgeRegistries.ITEMS.getKey(bodySlot.getItem().getItem()).toString());
                stack.getTag().putString("pommel", ForgeRegistries.ITEMS.getKey(pommelSlot.getItem().getItem()).toString());
                stack.getTag().putString("switch", ForgeRegistries.ITEMS.getKey(switchSlot.getItem().getItem()).toString());
                stack.getTag().putString("type", LightsaberType.SINGLE.toString());
                stack.getTag().putInt("color", CrystalColor.YELLOW.color);
                stack.getTag().putBoolean("active", false);
                outputSlot.set(stack);
            }
        }
    }

    private class Output extends Slot
    {
        public Output(int id, int x, int y)
        {
            super(craftResult, id, x, y);
        }

        @Override
        public void setByPlayer(ItemStack stack) {
            super.setByPlayer(stack);
            if(stack != null && stack.getItem() instanceof LightsaberItem)
            {
                bodySlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("grip"))).getDefaultInstance());
                emitterSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("emitter"))).getDefaultInstance());
                pommelSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("pommel"))).getDefaultInstance());
                switchSlot.set(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getTag().getString("switch"))).getDefaultInstance());
            }
        }

        @Override
        public boolean mayPlace(ItemStack stack)
        {
            return stack.getItem() instanceof LightsaberItem && !(stack.getItem() instanceof LightsaberDoubleItem);
        }

        @Override
        public void onTake(Player player, ItemStack itemstack)
        {
        	net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(player, itemstack, this.container);
        	
        	itemstack.onCraftedBy(player.level(), player, 1);
            if(!player.level().isClientSide) {
                inputSlots.forEach(slot ->
                        slot.remove(1));
            }
            itemstack.getTag().putBoolean("active", false);

        }
    }
}

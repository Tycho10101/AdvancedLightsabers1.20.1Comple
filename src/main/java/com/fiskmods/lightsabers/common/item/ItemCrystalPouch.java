package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;=
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ItemCrystalPouch extends Item
{
    public static final UUID NULL_UUID = UUID.randomUUID();
    
    @SideOnly(Side.CLIENT)
    private IIcon overlay;
    
    public ItemCrystalPouch()
    {
        super(new Item.Properties().stacksTo(1));
        ..setMaxStackSize(1);
        //setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, Level world, Player player)
    {
        if (!itemstack.hasTag())
        {
            itemstack.setTag(new CompoundTag());
        }

        player.openGui(Lightsabers.getInstance(), 3, world, player.getInventory().currentItem, 0, 0);
        return itemstack;
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, Level world, Entity entity, int slot, boolean inHand)
    {
        if (!world.isRemote)
        {
            if (!itemstack.hasTag() || !itemstack.getTag().hasUUID(ALConstants.TAG_POUCH_UUID))//.hasKey(ALConstants.TAG_POUCH_UUID, NBT.TAG_STRING))
            {
                getUUID(itemstack); // Assign UUID
            }
        }
    }
    
    @Override
    public void getSubItems(Item item, CreativeModeTab tab, List list)
    {
        for (CrystalColor color : CrystalColor.values())
        {
            list.add(ItemCrystal.create(color, ModItems.crystalPouch));
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack itemstack)
    {
        return ItemCrystal.rarityMap.get(ItemCrystal.get(itemstack));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, Player player, List list, boolean advanced)
    {
        list.add(ItemCrystal.get(itemstack).getLocalizedName());
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int pass)
    {
        return pass == 1 ? ItemCrystal.get(itemstack).color : super.getColorFromItemStack(itemstack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 1 ? overlay : itemIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        super.registerIcons(iconRegister);
        overlay = iconRegister.registerIcon(getIconString() + "_overlay");
    }
    
    public static boolean isPouch(ItemStack stack)
    {
        return stack != null && stack.getItem() == ModItems.crystalPouch;
    }
    
    public static UUID getUUID(ItemStack stack)
    {
        if (!isPouch(stack))
        {
            return NULL_UUID;
        }
        
        if (!stack.hasTag())
        {
            stack.setTag(new CompoundTag());
        }
        
        if (!stack.getTag().hasKey(ALConstants.TAG_POUCH_UUID, CompoundTag.TAG_STRING))
        {
            UUID uuid = UUID.randomUUID();
            stack.getTag().putString(ALConstants.TAG_POUCH_UUID, uuid.toString());
            
            return uuid;
        }
        
        return UUID.fromString(stack.getTag().getString(ALConstants.TAG_POUCH_UUID));
    }
}

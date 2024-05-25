package com.stelmods.lightsabers.common.proxy;

import com.stelmods.lightsabers.ALReflection;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.gui.GuiOverlay;
import com.stelmods.lightsabers.client.render.entity.RenderForceLightning;
import com.stelmods.lightsabers.client.render.entity.RenderLightsaber;
import com.stelmods.lightsabers.client.render.entity.RenderSithGhost;
import com.stelmods.lightsabers.client.render.hilt.HiltRendererManager;
import com.stelmods.lightsabers.client.render.item.RenderItemCrystal;
import com.stelmods.lightsabers.client.render.item.RenderItemDisassemblyStation;
import com.stelmods.lightsabers.client.render.item.RenderItemDoubleLightsaber;
import com.stelmods.lightsabers.client.render.item.RenderItemHolocron;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaberForge;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaberPart;
import com.stelmods.lightsabers.client.render.item.RenderItemLightsaberStand;
import com.stelmods.lightsabers.client.render.item.RenderItemSithCoffin;
import com.stelmods.lightsabers.client.render.item.RenderItemSithStoneCoffin;
import com.stelmods.lightsabers.client.render.tile.RenderCrystal;
import com.stelmods.lightsabers.client.render.tile.RenderDisassemblyStation;
import com.stelmods.lightsabers.client.render.tile.RenderHolocron;
import com.stelmods.lightsabers.client.render.tile.RenderLightsaberForge;
import com.stelmods.lightsabers.client.render.tile.RenderLightsaberStand;
import com.stelmods.lightsabers.client.render.tile.RenderSithCoffin;
import com.stelmods.lightsabers.client.render.tile.RenderSithStoneCoffin;
import com.stelmods.lightsabers.common.block.ModBlocks;
import com.stelmods.lightsabers.common.entity.EntityForceLightning;
import com.stelmods.lightsabers.common.entity.EntityLightsaber;
import com.stelmods.lightsabers.common.entity.EntitySithGhost;
import com.stelmods.lightsabers.common.event.ClientEventHandler;
import com.stelmods.lightsabers.common.event.ClientEventHandlerBG;
import com.stelmods.lightsabers.common.item.ModItems;
import com.stelmods.lightsabers.common.keybinds.ALKeyBinds;
import com.stelmods.lightsabers.common.lightsaber.PartType;
import com.stelmods.lightsabers.common.tileentity.*;
import com.stelmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.battlegear2.api.core.BattlegearUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
        ALReflection.client();
        ALKeyBinds.register();

        registerEventHandler(new ClientEventHandler());
        registerEventHandler(new GuiOverlay());

        if (Lightsabers.isBattlegearLoaded)
        {
            BattlegearUtils.RENDER_BUS.register(new ClientEventHandlerBG());
        }
    }

    @Override
    public void init()
    {
        super.init();
        HiltRendererManager.register();
        
        MinecraftForgeClient.registerItemRenderer(ModItems.lightsaber, new RenderItemLightsaber());
        MinecraftForgeClient.registerItemRenderer(ModItems.doubleLightsaber, new RenderItemDoubleLightsaber());
        MinecraftForgeClient.registerItemRenderer(ModItems.emitter, new RenderItemLightsaberPart(PartType.EMITTER));
        MinecraftForgeClient.registerItemRenderer(ModItems.switchSection, new RenderItemLightsaberPart(PartType.SWITCH_SECTION));
        MinecraftForgeClient.registerItemRenderer(ModItems.grip, new RenderItemLightsaberPart(PartType.BODY));
        MinecraftForgeClient.registerItemRenderer(ModItems.pommel, new RenderItemLightsaberPart(PartType.POMMEL));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberCrystal), new RenderItemCrystal());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeLight), new RenderItemLightsaberForge());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberForgeDark), new RenderItemLightsaberForge());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightsaberStand), RenderItemLightsaberStand.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.disassemblyStation), RenderItemDisassemblyStation.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithCoffin), new RenderItemSithCoffin());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.sithStoneCoffin), new RenderItemSithStoneCoffin());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.holocron), new RenderItemHolocron());

        RenderingRegistry.registerEntityRenderingHandler(EntityLightsaber.class, new RenderLightsaber());
        RenderingRegistry.registerEntityRenderingHandler(EntitySithGhost.class, new RenderSithGhost());
        RenderingRegistry.registerEntityRenderingHandler(EntityForceLightning.class, new RenderForceLightning());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
        ClientRegistry.bindTileEntitySpecialRenderer(LightsaberForgeBlockEntity.class, new RenderLightsaberForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLightsaberStand.class, new RenderLightsaberStand());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisassemblyStation.class, new RenderDisassemblyStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithCoffin.class, new RenderSithCoffin());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySithStoneCoffin.class, new RenderSithStoneCoffin());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolocron.class, new RenderHolocron());
    }
    
    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public float getRenderTick()
    {
        return ClientEventHandler.renderTick;
    }

    @Override
    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public boolean isClientPlayer(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer && !(entity instanceof EntityOtherPlayerMP);
    }
}

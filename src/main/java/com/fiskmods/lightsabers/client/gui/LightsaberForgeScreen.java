package com.fiskmods.lightsabers.client.gui;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.container.LightsaberForgeContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class LightsaberForgeScreen extends AbstractContainerScreen<LightsaberForgeContainer>
{
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");
    
    public LightsaberForgeScreen(LightsaberForgeContainer menu, Inventory inventoryPlayer, Component component /*, TileEntityLightsaberForge tile */)
    {
        super(menu, inventoryPlayer, component);
        this.imageWidth = 175;
        this.imageHeight = 195;
        this.height = 195;
        this.init();

    }
    
    @Override
    protected void renderLabels(GuiGraphics p_281635_, int mouseX, int mouseY) {
        /*String s = I18n.format("gui.lightsaber_forge");
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);
        
        LightsaberForgeContainer container = (LightsaberForgeContainer) inventorySlots;
        InventoryLightsaberForge inventory = container.craftMatrix;
        LightsaberData data = inventory.result;

        if (data != null)
        {
            if (data.isTooShort())
            {
                GL11.glColor4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(GUI_TEXTURES);
                drawTexturedModalRect(131, 65, 176, 0, 26, 17);
                
                GL11.glTranslatef(0, 0, 300);
                drawString(fontRendererObj, I18n.format("gui.lightsaber_forge.too_short"), 45, 64 - fontRendererObj.FONT_HEIGHT, 0xD74848);
            }
            else
            {
                GL11.glTranslatef(0, 0, 300);
                drawString(fontRendererObj, I18n.format("%s cm", ItemStack.field_111284_a.format(data.getHeightCm())), 45, 64 - fontRendererObj.FONT_HEIGHT, -1);
            }
            
            GL11.glTranslatef(0, 0, -300);
        }*/
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY)
    {
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURES);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, imageWidth, 195);


        //gui.drawString(font, "The Cat is a lie", xPos, yPos, 0x040404, false);
        //drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        /*L11.glColor4f(1, 1, 1, 1);


        LightsaberForgeContainer container = (LightsaberForgeContainer) inventorySlots;
        InventoryLightsaberForge inventory = container.craftMatrix;
        LightsaberData data = inventory.result;

        if (data != null)
        {
            float spin = mc.thePlayer.ticksExisted + partialTicks;

            /*GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glTranslatef(guiLeft + 110, guiTop + 40, 20);
            GL11.glRotatef((float) Math.sin(spin / 20) * 2.5F, 0, 1, 0);
            GL11.glRotatef((float) Math.sin(spin / 20 + 2) * 2.5F, 0, 0, 1);
            GL11.glRotatef(-90, 0, 0, 1);
            GL11.glRotatef(90 + spin, 0, 1, 0);
            GL11.glScalef(-20, 20, 20);
            RenderHelper.enableGUIStandardItemLighting();
            ALRenderHelper.startGlScissor(guiLeft + 43, guiTop + 17, 113, 47);
            ALRenderHelper.renderLightsaber(data, container.craftResult.getStackInSlot(0), false);
            ALRenderHelper.endGlScissor();
            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
            RenderHelper.disableStandardItemLighting();
        }
        else
        {
            Hilt hilt = null;
            
            for (int slot = 0; slot < 4; ++slot)
            {
                ItemStack stack = inventory.getStackInSlot(slot);

                if (stack != null)
                {
                    if (hilt == null || hilt == ItemLightsaberPart.get(stack))
                    {
                        hilt = ItemLightsaberPart.get(stack);
                    }
                    else
                    {
                        hilt = null;
                        break;
                    }
                }
            }
            
            if (hilt == null)
            {
                hilt = Iterables.get(Hilt.REGISTRY, (mc.thePlayer.ticksExisted / 20) % Hilt.REGISTRY.getKeys().size());
            }
            
            ALRenderHelper.setupRenderItemIntoGUI();
            GL11.glColor4f(0.6F, 0.6F, 0.6F, 0.125F);
            boolean prevColor = itemRender.renderWithColor;
            itemRender.renderWithColor = false;
            
            if (hilt != null)
            {
                for (int slot = 0; slot < 4; ++slot)
                {
                    if (inventory.getStackInSlot(slot) == null)
                    {
                        int[] pos = LightsaberForgeContainer.SLOTS[slot];
                        itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ItemLightsaberPart.create(PartType.values()[slot], hilt), guiLeft + pos[0], guiTop + pos[1]);
                    }
                }
                
                if (inventory.getStackInSlot(5) == null)
                {
                    int[] pos = LightsaberForgeContainer.SLOTS[5];
                    
                    GL11.glColor4f(1, 1, 1, 0.25F);
                    itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ItemCrystal.create(hilt.getColor()), guiLeft + pos[0], guiTop + pos[1]);
                }
            }
            
            itemRender.renderWithColor = prevColor;
            GL11.glColor4f(1, 1, 1, 1);
            ALRenderHelper.finishRenderItemIntoGUI();
        }*/
    }
}

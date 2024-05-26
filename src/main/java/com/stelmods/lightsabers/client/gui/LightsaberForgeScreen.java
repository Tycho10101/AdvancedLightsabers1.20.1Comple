package com.stelmods.lightsabers.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class LightsaberForgeScreen extends AbstractContainerScreen<LightsaberForgeContainer>
{
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");
    private float rotate = 0;
    public LightsaberForgeScreen(LightsaberForgeContainer menu, Inventory inventoryPlayer, Component component /*, TileEntityLightsaberForge tile */)
    {
        super(menu, inventoryPlayer, component);
        this.imageWidth = 175;
        this.imageHeight = 195;
        this.height = 195;
        this.init();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int p_283661_, int p_281248_, float p_281886_) {
        super.render(guiGraphics, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(guiGraphics, p_283661_, p_281248_);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int mouseX, int mouseY) {

    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY)
    {
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURES);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, imageWidth, 195);
        ItemStack itemStack = this.menu.getOutputSlot().getItem().copy();
        PoseStack matrixstack = gui.pose();
        //BakedModel bakedmodel = this.minecraft.getItemRenderer().getModel(itemStack, Minecraft.getInstance().level, Minecraft.getInstance().player, 1);

        matrixstack.pushPose();

        matrixstack.translate(0, 0,0);
        matrixstack.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 1f));
        MultiBufferSource.BufferSource bufferSource = gui.bufferSource();
        //this.minecraft.getItemRenderer().render(itemStack, ItemDisplayContext.GUI, false, matrixstack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        gui.renderItem(itemStack, this.leftPos + 99,this.topPos +29);
        matrixstack.popPose();
    }
}

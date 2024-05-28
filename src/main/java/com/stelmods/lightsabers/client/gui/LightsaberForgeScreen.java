package com.stelmods.lightsabers.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.model.ModelLightsaberBlade;
import com.stelmods.lightsabers.common.container.LightsaberForgeContainer;
import com.stelmods.lightsabers.common.item.ItemCrystal;
import com.stelmods.lightsabers.common.item.LightsaberItem;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import static com.stelmods.lightsabers.client.render.item.RenderItemLightsaber.getTotalHeight;


@OnlyIn(Dist.CLIENT)
public class LightsaberForgeScreen extends AbstractContainerScreen<LightsaberForgeContainer> {
    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Lightsabers.MODID, "textures/gui/container/lightsaber_forge.png");
    private float rotate = 0;

    public LightsaberForgeScreen(LightsaberForgeContainer menu, Inventory inventoryPlayer, Component component /*, TileEntityLightsaberForge tile */) {
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
    protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindForSetup(GUI_TEXTURES);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_TEXTURES, this.leftPos, this.topPos, 0, 0, imageWidth, 195);
        ItemStack itemStack = this.menu.getOutputSlot().getItem().copy();

        if(itemStack.getItem() instanceof LightsaberItem && itemStack.getItem() != Items.AIR){
            PoseStack matrixstack = gui.pose();
            matrixstack.pushPose();

            matrixstack.translate(this.leftPos + 150    , this.topPos + 36, 0);
            matrixstack.mulPose(Axis.ZP.rotationDegrees(90));
            matrixstack.mulPose(Axis.YP.rotationDegrees(rotate = (rotate % 360) + 2f));
            matrixstack.scale(75,75,75);

            //RenderSystem.enableScissor(this.leftPos + 43, this.topPos + 17, 113, 47);
            renderSingle(ItemDisplayContext.GUI, matrixstack, gui.bufferSource(), 0xffffff, itemStack);
            RenderSystem.disableScissor();
            matrixstack.popPose();
        }
    }

    private void renderSingle(ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, ItemStack itemStack) {

        CompoundTag tag = itemStack.getTag();
        float height = getTotalHeight(tag);

        matrixStack.pushPose();


        matrixStack.pushPose();
        ItemCrystal i = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("color")));
        float[] rgb = i.getCrystalColor().getRGB();
        matrixStack.scale(1.4f, .8f, 1.4f);
        matrixStack.translate(0, height * 1.3, 0);
        BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Lightsabers.MODID, "item/blade"));

        ModelLightsaberBlade.renderOuter(itemStack, rgb, buffer.getBuffer(
                RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), true)
        ), matrixStack, model, combinedLightIn);
        matrixStack.popPose();

        //render inner blade
        matrixStack.pushPose();
        matrixStack.scale(.5f, .80f, .5f);
        matrixStack.translate(0, height * 1.25, 0);
        ModelLightsaberBlade.renderInner(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(RenderType.solid()),
                false, matrixStack, model, combinedLightIn);

        matrixStack.popPose();

        height = renderPart(tag.getString("emitter"), height, (byte) 1, itemDisplayContext, matrixStack, buffer, combinedLightIn);
        height = renderPart(tag.getString("switch"), height, (byte) 1, itemDisplayContext, matrixStack, buffer, combinedLightIn);
        height = renderPart(tag.getString("grip"), height, (byte) 1, itemDisplayContext, matrixStack, buffer, combinedLightIn);
        renderPart(tag.getString("pommel"), height, (byte) -1, itemDisplayContext, matrixStack, buffer, combinedLightIn);

        matrixStack.popPose();
    }

    private float renderPart(String name, float height, byte y, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn) {
        matrixStack.pushPose();
        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        height = height - part.getHeight();
        BakedModel bm = Minecraft.getInstance().getItemRenderer().getModel(part.getDefaultInstance(), null, null, 1);
        matrixStack.translate(0, y * height, 0);
        Minecraft.getInstance().getItemRenderer().render(part.getDefaultInstance(), itemDisplayContext, false, matrixStack, buffer, combinedLightIn, OverlayTexture.NO_OVERLAY, bm);
        matrixStack.popPose();
        return height;
    }
}

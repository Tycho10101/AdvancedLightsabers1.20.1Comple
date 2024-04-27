package com.fiskmods.lightsabers.client.render.item;

import com.fiskmods.lightsabers.common.item.LightsaberPart;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberType;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;

public class RenderItemLightsaber extends BlockEntityWithoutLevelRenderer // implements IItemRenderer
{
    private ItemRenderer renderItem;
    public static final RenderItemLightsaber bewlr = new RenderItemLightsaber(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    public RenderItemLightsaber(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int p_108835_) {
        //super.renderByItem(p_108830_, p_270899_, p_108832_, p_108833_, p_108834_, p_108835_);

        this.renderItem = Minecraft.getInstance().getItemRenderer();
        CompoundTag tag = itemStack.getTag();
        String type = tag.getString("type");

        if(!type.equals(""))
        {
            LightsaberType typeL = LightsaberType.valueOf(type);

            switch (typeL) {
                case SINGLE -> {
                    matrixStack.pushPose();

                    matrixStack.translate(0, -(getHeight(tag.getString("grip")) + getHeight(tag.getString("pommel"))
                            - getTotalHeight(tag) / 2) / 16, 0);
                    renderSingle(tag, itemDisplayContext, matrixStack, buffer, combinedLightIn);
                    matrixStack.popPose();
                }

                case DOUBLE -> {
                }
            }

        }

    }

    private float getTotalHeight(CompoundTag tag){
        return  getHeight(tag.getString("switch")) + getHeight(tag.getString("emitter")) +
                getHeight(tag.getString("grip"))+ getHeight(tag.getString("pommel"));
    }
    private float getHeight(String name)
    {
        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        return part.getHeight();
    }

    private void renderSingle(CompoundTag tag,ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn)
    {
        matrixStack.pushPose();
        float height = getTotalHeight(tag);
        height = renderPart(tag.getString("emitter"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("switch"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("grip"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("pommel"), height,(byte) -1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        matrixStack.popPose();
    }

    private float renderPart(String name,float height, byte y, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn)
    {
        matrixStack.pushPose();
        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        height = height - part.getHeight();
        BakedModel bm = renderItem.getModel(part.getDefaultInstance(), null, null, 1);
        matrixStack.translate(0,y*height,0);
        renderItem.render(part.getDefaultInstance(), itemDisplayContext, false, matrixStack, buffer, combinedLightIn, OverlayTexture.NO_OVERLAY, bm);
        matrixStack.popPose();
        return height;
    }
}
    /*
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type == ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... args)
    {
        LightsaberData data = LightsaberData.get(stack);
        GL11.glPushMatrix();
        
        if (type ==  ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glRotatef(-100, 0, 1, 0);
            GL11.glRotatef(-150, 1, 0, 0);
            GL11.glRotatef(5, 0, 0, 1);
            GL11.glTranslatef(0, 0.275F, 0.85F);

            float scale = 0.2F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaber(stack, false);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            GL11.glTranslatef(0.7F, 0.3F, 0);
            GL11.glRotatef(-150, 0, 0, 1);
            GL11.glRotatef(-85, 0, 1, 0);

            if (args[1] instanceof EntityLivingBase)
            {
                ModelHelper.applyLightsaberItemRotation((EntityLivingBase) args[1], stack);
            }

            float scale = 0.175F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaber(data, stack, true);
        }
        else if (type == ItemRenderType.ENTITY)
        {
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(180, 0, 1, 0);
            
            float scale = 0.3F;
            GL11.glScalef(scale, scale, scale);
            
            if (((EntityItem) args[1]).hoverStart != 0)
            {
                GL11.glTranslatef(0, -data.getHeight() / 48, 0);
            }
            
            if (stack.hasDisplayName() && (stack.getDisplayName().equals("Dinnerbone") || stack.getDisplayName().equals("Grumm")))
            {
                GL11.glRotatef(180, 1, 0, 0);
            }
            
            ALRenderHelper.renderLightsaberHilt(data);
        }
        else if (type == ItemRenderType.INVENTORY)
        {
            Tessellator tessellator = Tessellator.instance;
            float[] rgb = data.getRGB(stack);
            float triangle = 4;
            
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1);
            tessellator.startDrawingQuads();
            tessellator.addVertex(triangle / 2, triangle / 2, 0);
            tessellator.addVertex(triangle, 0, 0);
            tessellator.addVertex(0, 0, 0);
            tessellator.addVertex(0, triangle, 0);
            tessellator.draw();

            if (data.hasFocusingCrystal(FocusingCrystal.INVERTING))
            {
                triangle /= 1.5F;
                GL11.glPushMatrix();
                GL11.glColor4f(0, 0, 0, 1);
                GL11.glTranslatef(triangle / 8, triangle / 8, 0);
                tessellator.startDrawingQuads();
                tessellator.addVertex(triangle / 2, triangle / 2, 0);
                tessellator.addVertex(triangle, 0, 0);
                tessellator.addVertex(0, 0, 0);
                tessellator.addVertex(0, triangle, 0);
                tessellator.draw();
                GL11.glPopMatrix();
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glTranslatef(-2, 3, 0);
            GL11.glScalef(10, 10, 10);
            GL11.glTranslatef(1, 0.5F, 1);
            GL11.glScalef(1, 1, -1);
            GL11.glRotatef(210, 1, 0, 0);
            GL11.glRotatef(45, 0, 1, 0);
            GL11.glRotatef(-90, 0, 1, 0);
            GL11.glRotatef(-20, 0, 0, 1);
            GL11.glRotatef(-45, 1, 0, 0);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(-110, 0, 1, 0);
            
            if (stack.hasDisplayName() && (stack.getDisplayName().equals("Dinnerbone") || stack.getDisplayName().equals("Grumm")))
            {
                GL11.glRotatef(180, 1, 0, 0);
            }
            
            GL11.glTranslatef(0, 0.05F, 0);

            float scale = 0.5F;
            GL11.glScalef(scale, scale, scale);
            ALRenderHelper.renderLightsaberHilt(data);
        }
        
        GL11.glPopMatrix();
    }
}
*/
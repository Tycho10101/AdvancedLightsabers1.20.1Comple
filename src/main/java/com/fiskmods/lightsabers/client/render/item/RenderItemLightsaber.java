package com.fiskmods.lightsabers.client.render.item;

import com.fiskmods.lightsabers.common.item.LightsaberPart;
import com.fiskmods.lightsabers.common.lightsaber.FocusingCrystal;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberType;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.fiskmods.lightsabers.helper.ModelHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;

public class RenderItemLightsaber extends ItemInHandRenderer// implements IItemRenderer
{
    private ItemRenderer renderItem;

    public RenderItemLightsaber(Minecraft instance, EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
        super(instance, dispatcher, itemRenderer);
    }
}
@Override
public void renderItem(LivingEntity p_270072_, ItemStack p_270793_, ItemDisplayContext p_270837_, boolean p_270203_, PoseStack p_270974_, MultiBufferSource p_270686_, int p_270103_) {
    matrixStack.pushPose();
        this.renderItem = Minecraft.getInstance().getItemRenderer();
        CompoundTag tag = itemStack.getTag();
        LightsaberType type = LightsaberType.valueOf(tag.getString("type"));
        switch(type)
        {
            case SINGLE -> {
                renderSingle(itemStack);
            }
            case DOUBLE -> {}
        }

        matrixStack.popPose();
    }

    public void renderSingle(ItemStack itemStack)
    {
        CompoundTag tag = itemStack.getTag();
        LightsaberPart lightsaberSwitch = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("switch")));
        LightsaberPart lightsaberPommel = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("pommel")));
        LightsaberPart lightsaberGrip = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("grip")));
        LightsaberPart lightsaberEmitter = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("emitter")));

    }
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

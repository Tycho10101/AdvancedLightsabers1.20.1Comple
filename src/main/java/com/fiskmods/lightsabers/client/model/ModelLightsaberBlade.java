package com.fiskmods.lightsabers.client.model;

import com.fiskmods.lightsabers.Lightsabers;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.model.data.ModelData;

public class ModelLightsaberBlade //extends ModelBase
{
    //public ModelRenderer blade;
    public int bladeLength;

    public ModelLightsaberBlade(int length) {
        //textureWidth = 64;
        //textureHeight = 32;
        //blade = new ModelRenderer(this, 0, 0);
        //blade.addBox(-0.5F, -length, -0.5F, 1, length, 1);
        bladeLength = length;
    }

    public void renderInner(ItemStack stack, float[] rgb, VertexConsumer vc, boolean isCrossguard, PoseStack matrixStack, BakedModel bm, int combineLight)
    {
        //boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);


        /*if (isCrossguard && fineCut)
        {
            matrixStack.scale(1, 1.2F, 1);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING))
        {
            vc.color(0f, 0f, 0f, 1f);
        }
        else if (data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            vc.color(rgb[0], rgb[1], rgb[2], 1);
        }
        else
        {
            vc.color(1, 1, 1, 1);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            matrixStack.scale(0.6F, 1, 0.6F);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.CRACKED))
        {
            float divider = 60;
            int ticks = Minecraft.getInstance().thePlayer.ticksExisted;
            Random rand = new Random(ticks % 100 * 1000);
            Random prev = new Random((ticks - 1) % 100 * 1000);

            Supplier<Float> nextFloat = () -> ALRenderHelper.median(rand.nextFloat(), prev.nextFloat());

            for (int i = 0; i < 4; ++i)
            {
                matrixStack.pushPose();

                if (i != 0)
                {

                    GL11.glTranslatef((nextFloat.get() - 0.5F) / divider, 0, (nextFloat.get() - 0.5F) / divider);

                    for (int j = 0; j < bladeLength; ++j)
                    {
                        GL11.glPushMatrix();
                        GL11.glRotatef(nextFloat.get() * 360, 0, 1, 0);
                        GL11.glRotatef(90, 1, 0, 0);
                        GL11.glTranslatef(0, 0.05F - (1 - nextFloat.get() * 0.2F) / 16, (1 + nextFloat.get() * bladeLength) / 16);
                        ALRenderHelper.drawTip(0.04F, 0);
                        GL11.glPopMatrix();
                    }
                }

                if (!fineCut)
                {
                    blade.render(0.0625F);
                    GL11.glTranslatef(0, -(0.5F + bladeLength) / 16, 1F / 32);
                    ALRenderHelper.drawTip(0.03125F, 0.125F);
                }

                matrixStack.popPose();
            }
        }

        if (fineCut)
        {
            Tesselator tessellator = Tesselator.getInstance();
            float f = 0.0625F;
            float length = f * bladeLength * 0.7F;
            float edge = f * 1.5F;
            float edgeAngle = -f * 1.5F;
            float length1 = f * bladeLength * 0.3F;
            float edge1 = f / 2;
            float tip = f * 1.5F;

             tessellator.getBuilder()
                    .vertex(-f / 2, -length, f / 2)
                    .vertex(0, -length, edge)
                    .vertex(0, edgeAngle, edge)
                    .vertex(-f / 2, -f, f / 2)
                    .vertex(f / 2, -length, f / 2)
                    .vertex(0, -length, edge)
                    .vertex(0, edgeAngle, edge)
                    .vertex(f / 2, -f, f / 2)
                    .vertex(f / 2, -f, f / 2)
                    .vertex(0, edgeAngle, edge)
                    .vertex(0, edgeAngle, edge)
                    .vertex(-f / 2, -f, f / 2)
                    .vertex(-f / 2, 0 - length, f / 2)
                    .vertex(-f / 2, -length1 - length, edge1)
                    .vertex(0, -length1 - length, edge1)
                    .vertex(0, 0 - length, edge)
                    .vertex(f / 2, 0 - length, f / 2)
                    .vertex(f / 2, -length1 - length, edge1)
                    .vertex(0, -length1 - length, edge1)
                    .vertex(0, 0 - length, edge)
                    .vertex(-f / 2, 0 - f * bladeLength, f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(-f / 2, 0 - f * bladeLength, -f / 2)
                    .vertex(f / 2, 0 - f * bladeLength, f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(f / 2, 0 - f * bladeLength, -f / 2)
                    .vertex(-f / 2, 0 - f * bladeLength, -f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(f / 2, 0 - f * bladeLength, -f / 2)
                    .vertex(-f / 2, 0 - f * bladeLength, f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(0, -tip - f * bladeLength, -f / 2)
                    .vertex(f / 2, 0 - f * bladeLength, f / 2).endVertex();


             //blade.render(0.0625F);
        }
        else
        {
            //blade.render(0.0625F);
            GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
            ALRenderHelper.drawTip(0.03125F, 0.125F);
        }*/
        for (BakedQuad quad : bm.getQuads(null, null, RandomSource.create(), ModelData.EMPTY,
                RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"))
        )) {

            vc.putBulkData(matrixStack.last(), quad, rgb[0], rgb[1], rgb[2], 1f, combineLight, OverlayTexture.NO_OVERLAY, true);
        }
        //GL11.glColor4f(1, 1, 1, 1);
    }

    public void renderOuter(ItemStack itemstack, float[] rgb, VertexConsumer vc, PoseStack matrixStack, BakedModel bm, int combineLight) {
        //boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);
        int smooth = 10;
        float width = 0.2F;
        float xscale = 1f;
        float heightScale = 1f;
        float zScale = 1f;
        float bloomAlpha = 0.15F;

        //TODO fix focus crystals
        /* if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            width = 0.4F;
            smooth = 7;
            bloomAlpha = 0.07F;
        }
        
        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING) && data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            rgb = new float[3];
            bloomAlpha *= 1.5F;
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        if (fineCut)
        {
            xscale *= 0.55F;
            heightScale *= 0.925F;
            zScale *= 1.1F;
        }

        if (inWorld)
        {
            width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
            smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
        }

        if (itemstack.getDisplayName().equals("jeb_"))
        {
            smooth *= 0.25F;
        }*/
        boolean fineCut = false;
        int layerCount = 5 * smooth;

        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);


        for (int i = 0; i < layerCount; ++i) {

            float scale = 1 + i * (width / smooth);
            float f4 = (float) i / (layerCount * 50);

            matrixStack.pushPose();
            float test = (1 - f4 * (fineCut ? 0.003F : 0.005F)) * heightScale;
            matrixStack.scale(scale * xscale, test, scale * zScale);
            float t = -f4 / 400 + 0.04F;
            matrixStack.translate(0, t, 0);

            /*if (fineCut)
            {
                matrixStack.translate(0, 0, 0.005F + f4 * 0.00001F);
            }*/
            for (BakedQuad quad : bm.getQuads(null, null, RandomSource.create(), ModelData.EMPTY,
                    RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"))
            )) {

                vc.putBulkData(matrixStack.last(), quad, rgb[0], rgb[1], rgb[2], bloomAlpha / smooth, combineLight, OverlayTexture.NO_OVERLAY, true);
            }
            //blade.render(0.0625F);
            matrixStack.popPose();
        }
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
//        if (data.hasFocusingCrystal(FocusingCrystal.CHARGED))
//        {
//            renderLightning(data, itemstack, rgb, inWorld, true);
//        }*/

        //vc.color(1f, 1, 1, 1);
        vc.endVertex();

    }

    /*public void renderCrossguardOuter(LightsaberData data, ItemStack itemstack, float[] rgb, boolean inWorld)
    {
        boolean fineCut = data.hasFocusingCrystal(FocusingCrystal.FINE_CUT);
        int smooth = 10;
        float width = 0.4F;
        float f = 1;
        float f1 = 1;
        float f2 = 1;
        float f3 = 0.1F;
        
        if (data.hasFocusingCrystal(FocusingCrystal.INVERTING) && data.hasFocusingCrystal(FocusingCrystal.PRISMATIC))
        {
            rgb = new float[3];
            RenderSystem.blendFunc(0x302, 0x303);
        }

        if (data.hasFocusingCrystal(FocusingCrystal.COMPRESSED))
        {
            width = 0.2F;
            smooth = 7;
            f1 = 0.9F;
            f3 = 0.07F;
        }

        if (fineCut)
        {
            f *= 0.55F;
            f1 *= 0.925F;
            f2 *= 1.3F;
        }

        if (inWorld)
        {
            width *= ModConfig.renderGlobalMultiplier * ModConfig.renderWidthMultiplier;
            smooth *= ModConfig.renderGlobalMultiplier * ModConfig.renderSmoothingMultiplier;
        }

        if (itemstack.getDisplayName().equals("jeb_"))
        {
            smooth *= 0.25F;
        }

        int layerCount = 5 * smooth;

        for (int i = 0; i < layerCount; ++i)
        {
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], f3 / smooth * (inWorld ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1));
            float scale = 1 + i * (width / smooth);
            float f4 = (float) i / layerCount * 50;

            GL11.glPushMatrix();
            GL11.glScaled(scale * f, (1 - f4 * 0.05F + 2F) * f1, scale * f2);
            GL11.glTranslatef(0, -f4 / 400 + 0.06F, 0);

            if (fineCut)
            {
                GL11.glTranslatef(0, 0, 0.005F + f4 * 0.00001F);
            }

            blade.render(0.0625F);
            GL11.glPopMatrix();
        }
        
//        if (data.hasFocusingCrystal(FocusingCrystal.CHARGED))
//        {
//            renderLightning(data, itemstack, rgb, inWorld, true);
//        }

        GL11.glColor4f(1, 1, 1, 1);
    } */

//    private void renderLightning(LightsaberData data, ItemStack itemstack, float[] rgb, boolean inWorld, boolean isCrossguard)
//    {
//        float divider = 60;
//        int ticks = Minecraft.getMinecraft().thePlayer.ticksExisted;
//        Random rand = new Random(ticks % 100 * 1000);
//        Random prev = new Random((ticks - 1) % 100 * 1000);
//
//        GL11.glColor4f(rgb[0], rgb[1], rgb[2], 0.5F * (inWorld ? ModConfig.renderGlobalMultiplier * ModConfig.renderOpacityMultiplier : 1));
//        Supplier<Float> nextFloat = () -> ALRenderHelper.median(rand.nextFloat(), prev.nextFloat());
//
//        for (int i = 0; i < 4; ++i)
//        {
//            GL11.glPushMatrix();
//
//            if (i != 0)
//            {
//                GL11.glTranslatef((nextFloat.get() - 0.5F) / divider, 0, (nextFloat.get() - 0.5F) / divider);
//
//                for (int j = 0; j < bladeLength; ++j)
//                {
//                    GL11.glPushMatrix();
//                    GL11.glRotatef(nextFloat.get() * 360, 0, 1, 0);
//                    GL11.glRotatef(90, 1, 0, 0);
//                    GL11.glTranslatef(0, 0.05F + (1 + nextFloat.get() * 0.2F) / 16, (1 + nextFloat.get() * bladeLength) / 16);
//                    ALRenderHelper.drawTip(0.04F, 0);
//                    GL11.glPopMatrix();
//                }
//            }
//
////            if (!fineCut)
////            {
////                blade.render(0.0625F);
////                GL11.glTranslatef(0, -0.0625F * (0.5F + bladeLength), 0.0625F / 2);
////                ALRenderHelper.drawTip(0.03125F, 0.125F);
////            }
//
//            GL11.glPopMatrix();
//        }
//    }
}

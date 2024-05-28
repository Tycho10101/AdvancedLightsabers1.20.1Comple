package com.stelmods.lightsabers.client.render.item;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.client.model.ModelLightsaberBlade;
import com.stelmods.lightsabers.common.item.ItemCrystal;
import com.stelmods.lightsabers.common.item.LightsaberPart;
import com.stelmods.lightsabers.common.lightsaber.LightsaberType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class RenderItemLightsaber extends BlockEntityWithoutLevelRenderer // implements IItemRenderer
{
    private ItemRenderer renderItem;
    public static final RenderItemLightsaber bewlr = new RenderItemLightsaber(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    public RenderItemLightsaber(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int p_108835_) {
        super.renderByItem(itemStack, itemDisplayContext, matrixStack, buffer, combinedLightIn, p_108835_);
        this.renderItem = Minecraft.getInstance().getItemRenderer();
        CompoundTag tag = itemStack.getTag();
        String type = tag.getString("type");

        if(!type.isEmpty())
        {
            LightsaberType typeL = LightsaberType.valueOf(type);

            switch (typeL) {
                case SINGLE -> renderSingle(tag, itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);

                case DOUBLE -> renderDouble(tag, itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);

            }
        }
    }

    public static float getTotalHeight(CompoundTag tag){
        return  getHeight(tag.getString("switch")) + getHeight(tag.getString("emitter")) +
                getHeight(tag.getString("grip"))+ getHeight(tag.getString("pommel"));
    }
    public static float getHeight(String name)
    {

        LightsaberPart part = (LightsaberPart) ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
        return part.getHeight();
    }

    private void renderDouble(CompoundTag tag,ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,ItemStack itemStack)
    {
        CompoundTag lowerTag = tag.getCompound("lower");
        float lowerHeight = getTotalHeight(lowerTag) - getHeight(lowerTag.getString("pommel"));

        CompoundTag upperTag = tag.getCompound("upper");
        float upperHeight = getTotalHeight(upperTag) - getHeight(upperTag.getString("pommel"));

        matrixStack.mulPose(Axis.XN.rotationDegrees(180));

        switch (itemDisplayContext)
        {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> matrixStack.translate(.5,-0.5, -.55);
            case FIRST_PERSON_RIGHT_HAND -> matrixStack.translate(.75,-.1,0);
            case GUI -> {
                matrixStack.translate(0.5,-0.5,0  );
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.scale(0.7f, 0.7f, 0.7f);
            }
            case FIXED -> {
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.translate(-.7,-0.5,-0.5);
            }
            case GROUND -> {
                matrixStack.translate(0.5,-1,-0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }
            default -> throw new IllegalStateException("Unexpected value: " + itemDisplayContext);
        }

        matrixStack.pushPose();
        if(lowerTag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {
                    matrixStack.pushPose();
                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(lowerTag.getString("color")));
                    float[] rgb = i.getCrystalColor().getRGB();
                    //outterblade
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, lowerHeight * 1, 0);
                    BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Lightsabers.MODID, "item/blade"));

                    ModelLightsaberBlade.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), true)
                    ), matrixStack, model, combinedLightIn);
                    matrixStack.popPose();
                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, lowerHeight * 1, 0);
                    ModelLightsaberBlade.renderInner(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(RenderType.solid()),
                            false, matrixStack, model, combinedLightIn);
                    matrixStack.popPose();
                }
            }
        }
        lowerHeight = renderPart(lowerTag.getString("emitter"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        lowerHeight = renderPart(lowerTag.getString("switch"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(lowerTag.getString("grip"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.mulPose(Axis.ZN.rotationDegrees(180));
        if(upperTag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {
                    //render outer blade
                    matrixStack.pushPose();
                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(upperTag.getString("color")));
                    float[] rgb = i.getCrystalColor().getRGB();
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, upperHeight * 1, 0);
                    BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Lightsabers.MODID, "item/blade"));

                    ModelLightsaberBlade.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), true)
                    ), matrixStack, model, combinedLightIn);
                    matrixStack.popPose();
                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, upperHeight * 1, 0);
                    ModelLightsaberBlade.renderInner(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(RenderType.solid()),
                            false, matrixStack, model, combinedLightIn);

                    matrixStack.popPose();
                }
            }
        }
        upperHeight = renderPart(upperTag.getString("emitter"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        upperHeight = renderPart(upperTag.getString("switch"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(upperTag.getString("grip"),upperHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        matrixStack.popPose();

    }


    private void renderSingle(CompoundTag tag,ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,ItemStack itemStack)
    {
        float height = getTotalHeight(tag);

        matrixStack.pushPose();

        switch (itemDisplayContext)
        {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> matrixStack.translate(0.5,0.53-(getHeight(tag.getString("grip"))+  getHeight(tag.getString("switch")) /2 + getHeight(tag.getString("pommel"))), 0.55);

            case FIRST_PERSON_RIGHT_HAND -> matrixStack.translate(.75,-.1,0);
            case GUI -> {
                matrixStack.translate(0.3,0.1f,0  );
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }
            case FIXED -> {

                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.mulPose(Axis.YP.rotationDegrees(180));
                matrixStack.translate(-.7,-0.5,-0.5);
            }
            case GROUND -> {
                matrixStack.translate(.25,0.5,0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }

        }
        if(tag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {

                    matrixStack.pushPose();
                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("color")));
                    float[] rgb = i.getCrystalColor().getRGB();
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, height * 1, 0);
                    BakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Lightsabers.MODID, "item/blade"));

                    ModelLightsaberBlade.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), true)
                    ), matrixStack, model, combinedLightIn);
                    matrixStack.popPose();

                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, height * 1.0, 0);
                    ModelLightsaberBlade.renderInner(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(RenderType.solid()),
                            false, matrixStack, model, combinedLightIn);

                    matrixStack.popPose();
                }
            }
        }
        height = renderPart(tag.getString("emitter"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("switch"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        height = renderPart(tag.getString("grip"),height, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(tag.getString("pommel"), height,(byte) -1,itemDisplayContext,matrixStack,buffer,combinedLightIn);

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

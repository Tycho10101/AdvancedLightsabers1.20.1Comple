package com.fiskmods.lightsabers.client.render.item;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.model.ModelLightsaberBlade;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.LightsaberPart;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberType;
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
    private static final ModelLightsaberBlade LIGHTSABER_BLADE = new ModelLightsaberBlade(38);
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
                case SINGLE -> {

                    matrixStack.pushPose();
                    matrixStack.translate(0,
                            -(getHeight(tag.getString("grip"))-getHeight(tag.getString("pommel"))/2)/2
                             //getTotalHeight(tag)-getHeight(tag.getString("emitter"))
                            , 0);
                    renderSingle(tag, itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);
                    matrixStack.popPose();
                }

                case DOUBLE -> renderDouble(tag, itemDisplayContext, matrixStack, buffer, combinedLightIn, itemStack);

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

    private void renderDouble(CompoundTag tag,ItemDisplayContext itemDisplayContext, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,ItemStack itemStack)
    {
        CompoundTag lowerTag = tag.getCompound("lower");
        float lowerHeight = getTotalHeight(lowerTag) - getHeight(lowerTag.getString("pommel"));

        CompoundTag upperTag = tag.getCompound("upper");
        float upperHeight = getTotalHeight(upperTag) - getHeight(upperTag.getString("pommel"));

        matrixStack.pushPose();
        matrixStack.mulPose(Axis.XN.rotationDegrees(180));

        switch (itemDisplayContext)
        {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> matrixStack.translate(.5,-0.5, -.6);
            case FIRST_PERSON_RIGHT_HAND -> {
                matrixStack.translate(.75,-.1,0);
            }
            case GUI -> {
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.scale(0.7f, 0.7f, 0.7f);
                matrixStack.translate(-0.0,-1,-0.5);
            }
            case FIXED -> {

                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.translate(-.7,-0.5,-0.5);
            }
            case GROUND -> {
                matrixStack.translate(.0,0.5,0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }
            default -> throw new IllegalStateException("Unexpected value: " + itemDisplayContext);
        }
        if(lowerTag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {
                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(lowerTag.getString("color")));
                    int color =  i.getCrystalColor().color;
                    float[] rgb = new float[]{((color & 0xff0000) >> 16) / 255f, ((color & 0xff00) >> 8) / 255f, (color & 0xff) / 255f};

                    //render outer blade
                    matrixStack.pushPose();
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, lowerHeight * 1.05, 0);
                    BakedModel m = renderItem.getModel(ModItems.blade.get().getDefaultInstance(), null, null, 1);
                    LIGHTSABER_BLADE.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), false)
                    ), matrixStack, m, combinedLightIn);
                    matrixStack.popPose();


                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, lowerHeight * 1.05, 0);
                    LIGHTSABER_BLADE.renderOuter(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(
                            RenderType.solid()
                    ), matrixStack, m, combinedLightIn);

                    matrixStack.popPose();
                }
            }
        }
        lowerHeight = renderPart(lowerTag.getString("emitter"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        lowerHeight = renderPart(lowerTag.getString("switch"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);
        renderPart(lowerTag.getString("grip"),lowerHeight, (byte) 1,itemDisplayContext,matrixStack,buffer,combinedLightIn);


        matrixStack.mulPose(Axis.XN.rotationDegrees(180));
        if(upperTag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {

                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(lowerTag.getString("color")));
                    int color =  i.getCrystalColor().color;
                    float[] rgb = new float[]{((color & 0xff0000) >> 16) / 255f, ((color & 0xff00) >> 8) / 255f, (color & 0xff) / 255f};

                    //render outer blade
                    matrixStack.pushPose();
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, upperHeight * 1.05, 0);
                    BakedModel m = renderItem.getModel(ModItems.blade.get().getDefaultInstance(), null, null, 1);
                    LIGHTSABER_BLADE.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), false)
                    ), matrixStack, m, combinedLightIn);
                    matrixStack.popPose();

                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, upperHeight * 1.05, 0);
                    LIGHTSABER_BLADE.renderOuter(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(
                            RenderType.solid()
                    ), matrixStack, m, combinedLightIn);

                    matrixStack.popPose();
                }
                default -> {
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
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> matrixStack.translate(.5,0.1, .6);
            case FIRST_PERSON_RIGHT_HAND -> matrixStack.translate(.75,-.1,0);
            case GUI -> {
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
                matrixStack.translate(-0.0,0.2,-0.5);
            }
            case FIXED -> {

                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45));
                matrixStack.mulPose(Axis.YP.rotationDegrees(180));
                matrixStack.translate(-.7,-0.5,-0.5);
            }
            case GROUND -> {
                matrixStack.translate(.0,0.5,0.5);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(45));
            }

        }
        if(tag.getBoolean("active")) {
            switch (itemDisplayContext) {
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND,
                     FIRST_PERSON_RIGHT_HAND -> {


                    ItemCrystal i  = (ItemCrystal) ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString("color")));
                    int color =  i.getCrystalColor().color;

                    //render outer blade
                    matrixStack.pushPose();
                    float[] rgb = new float[]{((color & 0xff0000) >> 16) / 255f, ((color & 0xff00) >> 8) / 255f, (color & 0xff) / 255f};
                    matrixStack.scale(1.4f, 1f, 1.4f);
                    matrixStack.translate(0, height * 1.05, 0);
                    BakedModel m = renderItem.getModel(ModItems.blade.get().getDefaultInstance(), null, null, 1);
                    LIGHTSABER_BLADE.renderOuter(itemStack, rgb, buffer.getBuffer(
                            RenderType.entityTranslucentEmissive(new ResourceLocation(Lightsabers.MODID, "textures/item/lightsaber/blade.png"), false)
                    ), matrixStack, m, combinedLightIn);
                    matrixStack.popPose();

                    //render inner blade
                    matrixStack.pushPose();
                    matrixStack.scale(.5f, .95f, .5f);
                    matrixStack.translate(0, height * 1.05, 0);
                    LIGHTSABER_BLADE.renderOuter(itemStack, new float[]{1.0f, 1.0f, 1.0f}, buffer.getBuffer(RenderType.solid()), matrixStack, m, combinedLightIn);

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

package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaber;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public abstract class LightsaberPart extends Item {

    private final float height;
    private final PartType partType;
    public LightsaberPart(float height, PartType partType)
    {
        super(new Properties().stacksTo(1).defaultDurability(0));
        this.height = height;
        this.partType = partType;

    }

    public PartType getPartType() { return partType; }

    public float getHeight() { return height; }
}

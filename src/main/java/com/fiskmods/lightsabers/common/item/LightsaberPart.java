package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.lightsaber.PartType;
import net.minecraft.world.item.Item;

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

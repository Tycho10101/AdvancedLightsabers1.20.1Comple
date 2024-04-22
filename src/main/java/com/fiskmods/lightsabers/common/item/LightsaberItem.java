package com.fiskmods.lightsabers.common.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class LightsaberItem extends SwordItem {
    public LightsaberItem(){
        super(Tiers.NETHERITE, 8,1, new Properties().defaultDurability(0).stacksTo(1));
    }
}

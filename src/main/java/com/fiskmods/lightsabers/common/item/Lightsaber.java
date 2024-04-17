package com.fiskmods.lightsabers.common.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class Lightsaber extends SwordItem {
    public  Lightsaber(Properties properties){
        super(Tiers.NETHERITE, 8,1, properties);
    }
}

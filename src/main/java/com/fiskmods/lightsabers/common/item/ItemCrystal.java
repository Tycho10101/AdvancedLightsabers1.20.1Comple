package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.Properties;

public class ItemCrystal extends Block
{
    private final CrystalColor crystalColor;
    private final Rarity rarity;

    public ItemCrystal(Rarity rarity, CrystalColor crystalColor) {
        super(Block.Properties.of().mapColor(MapColor.METAL).strength(1.0F, 10.0F));
        this.crystalColor = crystalColor;
        this.rarity = rarity;
    }

    public CrystalColor getCrystalColor() {
        return crystalColor;
    }

    public Rarity getRarity() {
        return rarity;
    }
}

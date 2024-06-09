package com.stelmods.lightsabers.common.item;

import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class ItemCrystal extends Block
{
    private final CrystalColor crystalColor;
    private final Rarity rarity;

    public ItemCrystal(Rarity rarity, CrystalColor crystalColor) {
        super(Block.Properties.of().mapColor(MapColor.METAL).strength(1.0F, 10.0F).noOcclusion());
        this.crystalColor = crystalColor;
        this.rarity = rarity;
    }


    public CrystalColor getCrystalColor() {
        return crystalColor;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}

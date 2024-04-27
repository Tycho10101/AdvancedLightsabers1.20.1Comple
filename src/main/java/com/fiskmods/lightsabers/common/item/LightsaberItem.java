package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.client.render.item.RenderItemLightsaber;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class LightsaberItem extends SwordItem implements IClientItemExtensions {

    public LightsaberItem() {
        super(Tiers.NETHERITE, 8, 1, new Item.Properties().stacksTo(1));
        //this.isCustomRenderer();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public RenderItemLightsaber getCustomRenderer() {
                return  RenderItemLightsaber.bewlr;
            }
        });
    }


}

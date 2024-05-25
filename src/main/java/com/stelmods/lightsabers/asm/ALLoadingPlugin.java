package com.stelmods.lightsabers.asm;

import java.util.List;

import com.stelmods.lightsabers.asm.transformers.ClassTransformerColor;
import com.stelmods.lightsabers.asm.transformers.ClassTransformerEffectRenderer;
import com.stelmods.lightsabers.asm.transformers.ClassTransformerEntityMob;
import com.stelmods.lightsabers.asm.transformers.ClassTransformerEntityPlayer;
import com.stelmods.lightsabers.asm.transformers.ClassTransformerModelBiped;
import com.stelmods.lightsabers.asm.transformers.ClassTransformerModelBipedMultiLayer;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.Side;
import fiskfille.utils.asm.AbstractLoadingPlugin;

@MCVersion("1.7.10")
@TransformerExclusions("com.stelmods.lightsabers.asm")
public class ALLoadingPlugin extends AbstractLoadingPlugin
{
    @Override
    public void setupTransformers(Side side, List<Class> list)
    {
        if (side.isClient())
        {
            list.add(ClassTransformerModelBiped.class);
            list.add(ClassTransformerModelBipedMultiLayer.class);
            list.add(ClassTransformerEffectRenderer.class);
            list.add(ClassTransformerColor.class);
        }
        
        list.add(ClassTransformerEntityMob.class);
        list.add(ClassTransformerEntityPlayer.class);
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }
}

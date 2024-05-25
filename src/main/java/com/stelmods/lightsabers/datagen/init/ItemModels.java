package com.stelmods.lightsabers.datagen.init;

import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Lightsabers.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(CrystalColor crystalColor: CrystalColor.values())
        {
            standardBlockItem(crystalColor.toString() + "_crystal");
        }
    }
    void standardBlockItem(String name) {
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(Lightsabers.MODID + ":block/" + name));
    }

}

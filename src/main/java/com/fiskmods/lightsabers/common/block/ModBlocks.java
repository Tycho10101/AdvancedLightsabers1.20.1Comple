package com.fiskmods.lightsabers.common.block;


import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.item.Rarity;
import com.fiskmods.lightsabers.common.lightsaber.CrystalColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class ModBlocks
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lightsabers.MODID);
    public  static  final RegistryObject<Block> redCrystal = createNewBlock("red_crystal",() -> new ItemCrystal(Rarity.RARE, CrystalColor.RED));
    public  static  final RegistryObject<Block> lightsaberForge = createNewBlock("lightsaber_forge", BlockLightsaberForge::new);


    private static <T extends Block> RegistryObject<T> createNewBlock(String name, Supplier<? extends T> block) {
        RegistryObject<T> newBlock = BLOCKS.register(name, block);
        createNewBlockItem(name, newBlock);
        return newBlock;
    }

    private static <T extends Block> void createNewBlockItem(String name, Supplier<? extends T> block) {
        Supplier<BlockItem> item = () -> new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties());
        ModItems.ITEMS.register(name, item);
    }
    /*
    public static Block lightsaberCrystal;
//    public static Block lightsaberCrystalGen;
    public static Block lightsaberForgeLight;
    public static Block lightsaberForgeDark;
    public static Block lightsaberStand;
    public static Block disassemblyStation;
    public static Block sithCoffin;
    public static Block sithStoneCoffin;
    public static Block holocron;

    public static Block lightForcestone;
    public static Block lightActivatedForcestone;
    public static Block lightForcestoneStairs;
    public static Block darkForcestone;
    public static Block darkActivatedForcestone;
    public static Block darkForcestoneStairs;
    public static BlockSlab forcestoneDoubleSlab;
    public static BlockSlab forcestoneSlab;

//    public static Block test;

    public static void register()
    {
        lightsaberCrystal = new BlockCrystal();
//        lightsaberCrystalGen = new BlockCrystalGen();
        lightsaberStand = new BlockLightsaberStand();
        disassemblyStation = new BlockDisassemblyStation();
        sithCoffin = new BlockSithCoffin();
        sithStoneCoffin = new BlockSithStoneCoffin();
        holocron = new BlockHolocron();

        lightForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
        lightActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
        lightForcestoneStairs = new BlockModStairs(lightForcestone, 0);
        darkForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
        darkActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
        darkForcestoneStairs = new BlockModStairs(darkForcestone, 0);
        forcestoneDoubleSlab = (BlockSlab) new BlockModSlab(true).setHardness(3.0F).setResistance(100.0F);
        forcestoneSlab = (BlockSlab) new BlockModSlab(false).setHardness(3.0F).setResistance(100.0F);

        lightsaberForgeLight = new BlockLightsaberForge(lightForcestone);
        lightsaberForgeDark = new BlockLightsaberForge(darkForcestone);
//        test = new BlockStructureSpawner();

        BlockRegistry.registerItemBlockAsTileEntity(lightsaberCrystal, "Lightsaber Crystal", TileEntityCrystal.class, ItemCrystal.class);
        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeLight, "Lightsaber Forge Light", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeDark, "Lightsaber Forge Dark", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
        BlockRegistry.registerTileEntity(lightsaberStand, "Lightsaber Stand", TileEntityLightsaberStand.class);
        BlockRegistry.registerItemBlockAsTileEntity(disassemblyStation, "Disassembly Station", TileEntityDisassemblyStation.class, ItemDisassemblyTable.class);
        BlockRegistry.registerItemBlockAsTileEntity(sithCoffin, "Sith Coffin", TileEntitySithCoffin.class, ItemSithCoffin.class);
        BlockRegistry.registerItemBlockAsTileEntity(sithStoneCoffin, "Sith Stone Coffin", TileEntitySithStoneCoffin.class, ItemSithStoneCoffin.class);
        BlockRegistry.registerItemBlockAsTileEntity(holocron, "Holocron", TileEntityHolocron.class, ItemHolocron.class);

        BlockRegistry.registerItemBlock(lightForcestone, "Light Forcestone", new ItemForcestone(lightForcestone));
        BlockRegistry.registerBlock(lightActivatedForcestone, "Light Activated Forcestone Pillar");
        BlockRegistry.registerBlock(lightForcestoneStairs, "Light Forcestone Stairs");
        BlockRegistry.registerItemBlock(darkForcestone, "Dark Forcestone", new ItemForcestone(darkForcestone));
        BlockRegistry.registerBlock(darkActivatedForcestone, "Dark Activated Forcestone Pillar");
        BlockRegistry.registerBlock(darkForcestoneStairs, "Dark Forcestone Stairs");
        BlockRegistry.registerItemBlock(forcestoneDoubleSlab, "Forcestone Double Slab", new ItemSlab(forcestoneDoubleSlab, forcestoneSlab, forcestoneDoubleSlab, true));
        BlockRegistry.registerItemBlock(forcestoneSlab, "Forcestone Slab", new ItemSlab(forcestoneSlab, forcestoneSlab, forcestoneDoubleSlab, false));

//        BlockRegistry.registerBlock(lightsaberCrystalGen, "Lightsaber Crystal Gen");
//		BlockRegistry.registerBlock(test, "Test");
    }*/
}

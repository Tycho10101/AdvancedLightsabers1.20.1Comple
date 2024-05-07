package com.fiskmods.lightsabers.common.item;


import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.parts.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lightsabers.MODID);


    public static final RegistryObject<Item>
        taronEmitter = registerEmitter("taron_emitter", .402f),
        taronGrip = registerBody("taron_grip", 0.542363f),
        taronSwitch = registerSwitch("taron_switch", 0.204343f),
        taronPommel = registerPommel("taron_pommel", 0.29844f);
    public static final RegistryObject<Item>
        testEmitter = registerEmitter("test_emitter", 0.180303f),
        testSwitch = registerSwitch("test_switch", 0.182134f),
        testGrip = registerBody("test_grip", 0.47213f),
        testPommel = registerPommel("test_pommel", 0.116575f);


    public static final RegistryObject<Item> lightsaber = ITEMS.register("lightsaber", LightsaberItem::new);
    public static final RegistryObject<Item> doubleLightsaber = ITEMS.register("lightsaber_double", LightsaberDoubleItem::new);
    public static final RegistryObject<Item> blade = ITEMS.register("blade", BladeItem::new);
    public static final RegistryObject<Item> testBlade = ITEMS.register("test_blade", BladeItem::new);
    private static RegistryObject<Item> registerPommel(String name, float height)
    {
        return ITEMS.register(name, () -> new LightsaberPommel(height));
    }

    private static RegistryObject<Item> registerBody(String name, float height)
    {
        return ITEMS.register(name, () -> new LightsaberBody(height));
    }

    private static RegistryObject<Item> registerSwitch(String name, float height)
    {
        return ITEMS.register(name, () -> new LightsaberSwitch(height));
    }

    private static RegistryObject<Item> registerEmitter(String name, float height)
    {
        return ITEMS.register(name, () -> new LightsaberEmiter(height));
    }

   /* public static Item circuitry;
    public static Item focusingCrystal;
    public static Item crystalPouch;

    public static Item emitter;
    public static Item switchSection;
    public static Item grip;
    public static Item pommel;

    public static Item lightsaber;
    public static Item doubleLightsaber;

    public static void register()
    {
        circuitry = new ItemCircuitry();
        focusingCrystal = new ItemFocusingCrystal();
        crystalPouch = new ItemCrystalPouch();

        emitter = new ItemLightsaberPart(PartType.EMITTER);
        switchSection = new ItemLightsaberPart(PartType.SWITCH_SECTION);
        grip = new ItemLightsaberPart(PartType.BODY);
        pommel = new ItemLightsaberPart(PartType.POMMEL);

        lightsaber = new ItemLightsaber();
        doubleLightsaber = new ItemDoubleLightsaber();
        

        ItemRegistry.registerItem(circuitry, "lightsaber_circuitry");
        ItemRegistry.registerItem(focusingCrystal, "focusing_crystal");
        ItemRegistry.registerItem(crystalPouch, "crystal_pouch");

        ItemRegistry.registerItem(emitter, "lightsaber_blade_emitter");
        ItemRegistry.registerItem(switchSection, "lightsaber_switch_module");
        ItemRegistry.registerItem(grip, "lightsaber_grip");
        ItemRegistry.registerItem(pommel, "lightsaber_pommel");

        ItemRegistry.registerItem(lightsaber, "lightsaber");
        ItemRegistry.registerItem(doubleLightsaber, "double_lightsaber");
    }*/
}

package com.stelmods.lightsabers.common.item;


import com.stelmods.lightsabers.Lightsabers;
import com.stelmods.lightsabers.common.item.parts.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lightsabers.MODID);



    public static final RegistryObject<Item>
            taronEmitter = registerEmitter("taron_emitter", .209f),
            taronGrip = registerBody("taron_grip", 0.271181f),
            taronSwitch = registerSwitch("taron_switch", 0.102172f),
            taronPommel = registerPommel("taron_pommel", 0.14922f);
    public static final RegistryObject<Item>
            testEmitter = registerEmitter("test_emitter", 0.180303f),
            testSwitch = registerSwitch("test_switch", 0.182134f),
            testGrip = registerBody("test_grip", 0.47213f),
            testPommel = registerPommel("test_pommel", 0.116575f);

    public static final RegistryObject<Item>
            revanEmitter = registerEmitter("revan_emitter", .1f),
            revanSwitch = registerSwitch("revan_switch", 0.114288f),
            revanGrip = registerBody("revan_grip", 0.30083f),
            revanPommel = registerPommel("revan_pommel", 0.041906f);

    public static final RegistryObject<Item> lightsaber = ITEMS.register("lightsaber", LightsaberItem::new);
    public static final RegistryObject<Item> doubleLightsaber = ITEMS.register("lightsaber_double", LightsaberDoubleItem::new);






    private static RegistryObject<Item> registerPommel(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberPommel(height));
    }

    private static RegistryObject<Item> registerBody(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberBody(height));
    }

    private static RegistryObject<Item> registerSwitch(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberSwitch(height));
    }

    private static RegistryObject<Item> registerEmitter(String name, float height) {
        return ITEMS.register(name, () -> new LightsaberEmiter(height));
    }
}

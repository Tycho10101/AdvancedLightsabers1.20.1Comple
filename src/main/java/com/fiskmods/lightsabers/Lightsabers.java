package com.fiskmods.lightsabers;

import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.container.ModContainers;
import com.fiskmods.lightsabers.common.entity.ModEntities;
import com.fiskmods.lightsabers.common.item.LightsaberItem;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.item.parts.BladeItem;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberType;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static com.fiskmods.lightsabers.Lightsabers.MODID;

@Mod(MODID)
public class Lightsabers
{
    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";
    public static final String VERSION = "${version}";
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static Lightsabers instance;
    private static final Supplier<List<ItemStack>> items = Suppliers.memoize(() ->
            ModItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof LightsaberItem)).filter(item -> !(item.get() instanceof BladeItem)).map(RegistryObject::get).map(ItemStack::new).toList());

    public static final RegistryObject<CreativeModeTab> lightsaber_tab = TABS.register(MODID, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.testEmitter.get()))
            .displayItems((params, output) -> {
                items.get().forEach(output::accept);
                items.get().forEach(output::accept);

                ItemStack testSaber = new ItemStack(ModItems.lightsaber.get());
                testSaber.setTag(new CompoundTag());
                testSaber.getTag().putString("emitter", ModItems.testEmitter.getId().toString());
                testSaber.getTag().putString("grip", ModItems.testGrip.getId().toString());
                testSaber.getTag().putString("pommel", ModItems.testPommel.getId().toString());
                testSaber.getTag().putString("switch", ModItems.testSwitch.getId().toString());
                testSaber.getTag().putString("type", LightsaberType.SINGLE.toString());
                testSaber.getTag().putString("color", ModBlocks.greenCrystal.getId().toString());
                testSaber.getTag().putBoolean("active", false);
                output.accept(testSaber);

                ItemStack taron = new ItemStack(ModItems.lightsaber.get());
                taron.setTag(new CompoundTag());
                taron.getTag().putString("emitter", ModItems.taronEmitter.getId().toString());
                taron.getTag().putString("grip", ModItems.taronGrip.getId().toString());
                taron.getTag().putString("pommel", ModItems.taronPommel.getId().toString());
                taron.getTag().putString("switch", ModItems.taronSwitch.getId().toString());
                taron.getTag().putString("type", LightsaberType.SINGLE.toString());
                taron.getTag().putString("color", ModBlocks.greenCrystal.getId().toString());
                taron.getTag().putBoolean("active", false);
                output.accept(taron);

                output.accept(registerDoubleSaber(taron, testSaber));
                output.accept(registerDoubleSaber(taron, taron));
            })
            .build());

    private static ItemStack registerDoubleSaber(ItemStack upper, ItemStack lower)
    {
        ItemStack itemStack = new ItemStack(ModItems.doubleLightsaber.get());
        itemStack.setTag(new CompoundTag());
        itemStack.getTag().putString("type", LightsaberType.DOUBLE.toString());
        itemStack.getTag().put("upper", upper.getTag());
        itemStack.getTag().put("lower", lower.getTag());
        return itemStack;
    }

	public static Lightsabers getInstance() {
		return instance;
	}

    
    public Lightsabers() {
    	instance = this;

       	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	bus.addListener(this::constructMod);
    	bus.addListener(this::doClientStuff);
    	bus.addListener(this::doServerStuff);
    	bus.addListener(this::doCommonStuff);
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModEntities.TILE_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        TABS.register(bus);
    }
    
    private void constructMod(final FMLConstructModEvent event) {
    	
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        ModContainers.registerGUIFactories();
    }
	
	private void doServerStuff(final FMLDedicatedServerSetupEvent event) {

	}
	
	private void doCommonStuff(final FMLCommonSetupEvent event) {

    }
    

}

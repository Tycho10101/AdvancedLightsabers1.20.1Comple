package com.fiskmods.lightsabers;

import com.fiskmods.lightsabers.common.item.LightsaberItem;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.C;

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
            ModItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof LightsaberItem)).map(RegistryObject::get).map(ItemStack::new).toList());

    public static final RegistryObject<CreativeModeTab> lightsaber_tab = TABS.register(MODID, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.testEmitter.get()))
            .displayItems((params, output) -> {items.get().forEach(output::accept);

                ItemStack itemStack = new ItemStack(LightsaberItem::new);
                itemStack.setTag(new CompoundTag());
                itemStack.getTag().putString("emitter", ModItems.testEmitter.getId().getNamespace());
                itemStack.getTag().putString("grip", ModItems.testGrip.getId().getNamespace());
                itemStack.getTag().putString("pommel", ModItems.testPommel.getId().getNamespace());
                itemStack.getTag().putString("switch", ModItems.testSwitch.getId().getNamespace());

                output.accept(itemStack);
            })
            .build());



	public static Lightsabers getInstance() {
		return instance;
	}
    
    /**
     * Proxy -> LifecycleEvents (FMLConstructModEvent, FMLClientSetupEvent, FMLDedicatedServerSetupEvent, FMLCommonSetupEvent)
    @SidedProxy(clientSide = "com.fiskmods.lightsabers.common.proxy.ClientProxy", serverSide = "com.fiskmods.lightsabers.common.proxy.CommonProxy")
    public static CommonProxy proxy;
	**/
    
    /**
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID)
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.lightsaber;
        }
    };
	**/
    
    public static boolean isBattlegearLoaded;
    public static boolean isDynamicLightsLoaded;
    
    public Lightsabers() {
    	instance = this;
        System.out.println("This is a test");
    	
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	bus.addListener(this::constructMod);
    	bus.addListener(this::doClientStuff);
    	bus.addListener(this::doServerStuff);
    	bus.addListener(this::doCommonStuff);
        ModItems.ITEMS.register(bus);
        TABS.register(bus);
    }
    
    private void constructMod(final FMLConstructModEvent event) {
    	
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {

    }
	
	private void doServerStuff(final FMLDedicatedServerSetupEvent event) {

	}
	
	private void doCommonStuff(final FMLCommonSetupEvent event) {

    }
    
    /**
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FiskUtils.hook(Hook.PREINIT);
        
        isBattlegearLoaded = Loader.isModLoaded(ALConstants.BATTLEGEAR);
        isDynamicLightsLoaded = Loader.isModLoaded(ALConstants.DYNAMIC_LIGHTS);

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ModConfig.load(config);

        if (config.hasChanged())
        {
            config.save();
        }
        
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FiskUtils.hook(Hook.INIT);
        proxy.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FiskUtils.hook(Hook.POSTINIT);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandForce());
    }**/
}

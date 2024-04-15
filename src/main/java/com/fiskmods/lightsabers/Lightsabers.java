package com.fiskmods.lightsabers;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod("lightsabers")
public class Lightsabers
{
    public static final String NAME = "Advanced Lightsabers";
    public static final String MODID = "lightsabers";
    public static final String VERSION = "${version}";
    
    private static Lightsabers instance;
	
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
    	
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	bus.addListener(this::constructMod);
    	bus.addListener(this::doClientStuff);
    	bus.addListener(this::doServerStuff);
    	bus.addListener(this::doCommonStuff);
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

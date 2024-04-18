package com.fiskmods.lightsabers.common.container;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.gui.GuiLightsaberForge;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ALMenuType {
	public static final DeferredRegister<MenuType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lightsabers.MODID);
	
	// Haven't figured out the meaning of parameter FeatureFlags yet
	public static final RegistryObject<MenuType<ContainerLightsaberForge>> LIGHTSABER_FORGE = BLOCK_ENTITIES.register("lightsaber_forge", () -> new MenuType<>(ContainerLightsaberForge::new, FeatureFlags.VANILLA_SET));
	
	static {
		MenuScreens.register(LIGHTSABER_FORGE.get(), GuiLightsaberForge::new);
	}
}

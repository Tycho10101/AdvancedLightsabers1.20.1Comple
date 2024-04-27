package com.fiskmods.lightsabers.common.entity;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.LightsaberItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Lightsabers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
}
/*private static int nextID = -1;
    
    public static void register()
    {
        registerEntity(EntityLightsaber.class, "Lightsaber", 64, 10, true);
        registerEntityWithEgg(EntitySithGhost.class, "SithGhost", 80, 1, true, 0x212121, 0xE2CECE);
    }
    
    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates)
    {
        EntityRegistry.registerModEntity(entityClass, name, ++nextID, Lightsabers.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }

    private static void registerEntityWithEgg(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int primary, int secondary)
    {
        EntityRegistry.registerGlobalEntityID(entityClass, Lightsabers.MODID + "." + name, EntityRegistry.findGlobalUniqueEntityId(), primary, secondary);
        registerEntity(entityClass, name, trackingRange, updateFrequency, sendVelocityUpdates);
    }
}
*/
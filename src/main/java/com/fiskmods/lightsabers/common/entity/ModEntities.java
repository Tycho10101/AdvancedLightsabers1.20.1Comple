package com.fiskmods.lightsabers.common.entity;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.tileentity.LightsaberForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Lightsabers.MODID);

    public static final RegistryObject<BlockEntityType<LightsaberForgeBlockEntity>> LIGHTSABER_FORGE = TILE_ENTITIES.register("lightsaber_forge", () -> BlockEntityType.Builder.of(LightsaberForgeBlockEntity::new, ModBlocks.lightsaberForge.get()).build(null));
}

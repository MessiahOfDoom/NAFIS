package de.schneider_oliver.nafis.block.entity;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.block.BlockRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {

	public static BlockEntityType<BasicForgeBlockEntity> BASIC_FORGE_BLOCK_ENTITY;
	public static BlockEntityType<ImprovedForgeBlockEntity> IMPROVED_FORGE_BLOCK_ENTITY;
	
	
	public static void register() {
		BASIC_FORGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ident("basic_forge_block_entity"), BlockEntityType.Builder.create(BasicForgeBlockEntity::new, BlockRegistry.BASIC_FORGE).build(null));
		IMPROVED_FORGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ident("improved_forge_block_entity"), BlockEntityType.Builder.create(ImprovedForgeBlockEntity::new, BlockRegistry.IMPROVED_FORGE).build(null));
	}
	
}

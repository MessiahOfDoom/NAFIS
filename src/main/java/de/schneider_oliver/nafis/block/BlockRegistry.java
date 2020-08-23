package de.schneider_oliver.nafis.block;

import static de.schneider_oliver.nafis.item.ItemRegistry.NAFIS_GROUP;
import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.doomedfabric.block.AutoOre;
import de.schneider_oliver.doomedfabric.block.AutoOreBlock;
import de.schneider_oliver.doomedfabric.block.AutoOreRegistry;
import de.schneider_oliver.nafis.block.block.AbstractForgeBlock;
import de.schneider_oliver.nafis.block.block.BasicForgeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

	public static final Material ROCK = new FabricMaterialBuilder(DyeColor.LIGHT_GRAY).build();
	
	public static final AbstractForgeBlock BASIC_FORGE = new BasicForgeBlock(FabricBlockSettings.of(ROCK).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(3, 10));
	public static final Block COPPER_ORE = new AutoOreBlock(FabricBlockSettings.of(ROCK).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(2, 5));
	public static final Block COPPER_BLOCK = new Block(FabricBlockSettings.of(ROCK).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(2, 5));
	public static final Block REINFORCED_STONE = new Block(FabricBlockSettings.of(ROCK).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(4, 50));
	public static final Block REINFORCED_COBBLESTONE = new Block(FabricBlockSettings.of(ROCK).requiresTool().breakByTool(FabricToolTags.PICKAXES, 1).strength(4, 50));
	
	public static void register() {
		register(BASIC_FORGE, ident("basic_forge"));
		registerOre(COPPER_ORE, ident("copper_ore"));
		register(COPPER_BLOCK, ident("copper_block"));
		register(REINFORCED_STONE, ident("reinforced_stone"));
		register(REINFORCED_COBBLESTONE, ident("reinforced_cobblestone"));
	}
	
	public static void register(Block block, Identifier identifier) {
		Registry.register(Registry.BLOCK, identifier, block);
		Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(NAFIS_GROUP)));
	}
	
	public static void registerOre(Block block, Identifier identifier) {
		Registry.register(Registry.BLOCK, identifier, block);
		Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(NAFIS_GROUP)));
		AutoOreRegistry.registerOre((AutoOre) block);
	}
	
}

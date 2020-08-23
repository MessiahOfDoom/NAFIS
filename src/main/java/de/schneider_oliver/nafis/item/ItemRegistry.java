package de.schneider_oliver.nafis.item;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.block.BlockRegistry;
import de.schneider_oliver.nafis.item.item.ItemSelfRemainderImpl;
import de.schneider_oliver.nafis.item.item.NafisAxe;
import de.schneider_oliver.nafis.item.item.NafisHoe;
import de.schneider_oliver.nafis.item.item.NafisPickaxe;
import de.schneider_oliver.nafis.item.item.NafisShovel;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

	public static final ItemGroup NAFIS_GROUP = FabricItemGroupBuilder.build(ident("nafis_tab"), () -> new ItemStack(BlockRegistry.BASIC_FORGE));
	
	public static final Item NAFIS_PICKAXE = new NafisPickaxe(new Item.Settings());
	public static final Item NAFIS_AXE = new NafisAxe(new Item.Settings());
	public static final Item NAFIS_SHOVEL = new NafisShovel(new Item.Settings());
	public static final Item NAFIS_HOE = new NafisHoe(new Item.Settings());

	public static final Item COPPER_INGOT = new Item(new Item.Settings().group(NAFIS_GROUP));
	
	public static final Item BLUEPRINT_EMPTY = new Item(new Item.Settings().group(NAFIS_GROUP));
	
	public static final Item BLUEPRINT_TOOL_HANDLE = new ItemSelfRemainderImpl(new Item.Settings().group(NAFIS_GROUP));
	public static final Item BLUEPRINT_PICKAXE_HEAD = new ItemSelfRemainderImpl(new Item.Settings().group(NAFIS_GROUP));
	public static final Item BLUEPRINT_AXE_HEAD = new ItemSelfRemainderImpl(new Item.Settings().group(NAFIS_GROUP));
	public static final Item BLUEPRINT_SHOVEL_HEAD = new ItemSelfRemainderImpl(new Item.Settings().group(NAFIS_GROUP));
	public static final Item BLUEPRINT_HOE_HEAD = new ItemSelfRemainderImpl(new Item.Settings().group(NAFIS_GROUP));


	
	public static void register() {
		Registry.register(Registry.ITEM, ident("nafis_pickaxe"), NAFIS_PICKAXE);
		Registry.register(Registry.ITEM, ident("nafis_axe"), NAFIS_AXE);
		Registry.register(Registry.ITEM, ident("nafis_shovel"), NAFIS_SHOVEL);
		Registry.register(Registry.ITEM, ident("nafis_hoe"), NAFIS_HOE);
		Registry.register(Registry.ITEM, ident("copper_ingot"), COPPER_INGOT);
		
		Registry.register(Registry.ITEM, ident("bp_empty"), BLUEPRINT_EMPTY);
		
		Registry.register(Registry.ITEM, ident("bp_tool_handle"), BLUEPRINT_TOOL_HANDLE);
		Registry.register(Registry.ITEM, ident("bp_pickaxe_head"), BLUEPRINT_PICKAXE_HEAD);
		Registry.register(Registry.ITEM, ident("bp_axe_head"), BLUEPRINT_AXE_HEAD);
		Registry.register(Registry.ITEM, ident("bp_shovel_head"), BLUEPRINT_SHOVEL_HEAD);
		Registry.register(Registry.ITEM, ident("bp_hoe_head"), BLUEPRINT_HOE_HEAD);
		
		PartSetRegistry.registerPartSet(ToolMaterials.WOOD, ident("wood"));
		PartSetRegistry.registerPartSet(ToolMaterials.STONE, ident("stone"));
		PartSetRegistry.registerPartSet(ToolMaterials.IRON, ident("iron"));
		PartSetRegistry.registerPartSet(ToolMaterials.GOLD, ident("gold"));
		PartSetRegistry.registerPartSet(ToolMaterials.DIAMOND, ident("diamond"));
		NafisToolMaterials.registerAllParts();
	}
	
	
}

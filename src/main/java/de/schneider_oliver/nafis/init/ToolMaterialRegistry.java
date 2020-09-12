package de.schneider_oliver.nafis.init;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.item.NafisToolMaterials;
import de.schneider_oliver.nafis.item.PartSetRegistry;
import net.minecraft.item.ToolMaterials;

class ToolMaterialRegistry {

	static void register() {
		PartSetRegistry.registerPartSet(ToolMaterials.WOOD, ident("wood"));
		PartSetRegistry.registerPartSet(ToolMaterials.STONE, ident("stone"));
		PartSetRegistry.registerPartSet(ToolMaterials.IRON, ident("iron"));
		PartSetRegistry.registerPartSet(ToolMaterials.GOLD, ident("gold"));
		PartSetRegistry.registerPartSet(ToolMaterials.DIAMOND, ident("diamond"));
		NafisToolMaterials.registerAllParts();
	}
	
}

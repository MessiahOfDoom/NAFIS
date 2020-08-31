package de.schneider_oliver.nafis.item;

import static de.schneider_oliver.nafis.utils.IdentUtils.*;
import static de.schneider_oliver.nafis.item.ItemRegistry.NAFIS_GROUP;

import de.schneider_oliver.nafis.item.item.BasicToolHead;
import de.schneider_oliver.nafis.item.item.BasicToolHandle;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartSetRegistry {

	public static void registerPartSet(ToolMaterial mat, Identifier materialName) {
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_pickaxe_head"), new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getDurability(), mat.getMiningLevel(), mat.getAttackDamage() + 1, TOOL_PART_PICKAXE_HEAD, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_axe_head"), new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getDurability(), mat.getMiningLevel(), mat.getAttackDamage() + 1.5F, TOOL_PART_AXE_HEAD, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_shovel_head"), new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getDurability(), mat.getMiningLevel(), mat.getAttackDamage(), TOOL_PART_SHOVEL_HEAD, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_hoe_head"), new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getDurability(), mat.getMiningLevel(), 0, TOOL_PART_HOE_HEAD, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_tool_handle"), new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getMiningSpeedMultiplier(), -3F, TOOL_PART_BASIC_HANDLE, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_weapon_handle"), new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getMiningSpeedMultiplier(), -2.8F, TOOL_PART_WEAPON_HANDLE, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_wide_guard"), new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getMiningSpeedMultiplier(), 0.5F, TOOL_PART_WIDE_GUARD, materialName));
		Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + "_broadsword_head"), new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), mat.getDurability(), mat.getMiningLevel(), mat.getAttackDamage() + 2, TOOL_PART_BROADSWORD_HEAD, materialName));
		
	}
	
}
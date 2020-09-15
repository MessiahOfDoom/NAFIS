/*******************************************************************************
 * Copyright (c) 2020 Oliver Schneider
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU General Public License version 3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package de.schneider_oliver.nafis.init;

import static de.schneider_oliver.nafis.item.ItemRegistry.NAFIS_GROUP;
import static de.schneider_oliver.nafis.utils.IdentUtils.*;

import de.schneider_oliver.nafis.item.PartSetRegistry;
import de.schneider_oliver.nafis.item.item.BasicToolHandle;
import de.schneider_oliver.nafis.item.item.BasicToolHead;
import net.minecraft.item.Item;

class PartTypeRegistry {

	static void register() {
		PartSetRegistry.addFactory( "_pickaxe_head", (mat, materialName) -> new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1),
				mat.getDurability(),
				mat.getMiningLevel(),
				mat.getAttackDamage() + 1,
				mat.getRepairIngredient(),
				TOOL_PART_PICKAXE_HEAD,
				materialName));
		PartSetRegistry.addFactory( "_axe_head", (mat, materialName) -> new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getDurability(), 
				mat.getMiningLevel(), 
				mat.getAttackDamage() + 1.5F, 
				mat.getRepairIngredient(),
				TOOL_PART_AXE_HEAD, 
				materialName));
		PartSetRegistry.addFactory( "_shovel_head", (mat, materialName) -> new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getDurability(), 
				mat.getMiningLevel(), 
				mat.getAttackDamage(), 
				mat.getRepairIngredient(),
				TOOL_PART_SHOVEL_HEAD, 
				materialName));
		PartSetRegistry.addFactory( "_hoe_head", (mat, materialName) -> new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getDurability(), 
				mat.getMiningLevel(), 
				0, 
				mat.getRepairIngredient(),
				TOOL_PART_HOE_HEAD,
				materialName));
		PartSetRegistry.addFactory( "_broadsword_head", (mat, materialName) -> new BasicToolHead(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getDurability(), 
				mat.getMiningLevel(), 
				mat.getAttackDamage() + 2, 
				mat.getRepairIngredient(),
				TOOL_PART_BROADSWORD_HEAD, 
				materialName));
		PartSetRegistry.addFactory( "_tool_handle", (mat, materialName) -> new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getMiningSpeedMultiplier(),
				-3.5F,
				TOOL_PART_BASIC_HANDLE,
				materialName));
		PartSetRegistry.addFactory( "_weapon_handle", (mat, materialName) -> new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1), 
				mat.getMiningSpeedMultiplier(),
				-2.8F,
				TOOL_PART_WEAPON_HANDLE,
				materialName));
		PartSetRegistry.addFactory("_wide_guard", (mat, materialName) -> new BasicToolHandle(new Item.Settings().group(NAFIS_GROUP).maxCount(1),
				mat.getMiningSpeedMultiplier(),
				0.5F,
				TOOL_PART_WIDE_GUARD,
				materialName));
	}
	
}

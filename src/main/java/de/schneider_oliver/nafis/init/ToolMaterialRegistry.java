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

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.item.NafisToolMaterials;
import de.schneider_oliver.nafis.item.PartSetRegistry;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

class ToolMaterialRegistry {

	static final Identifier WOOD = ident("wood");
	static final Identifier STONE = ident("stone");
	static final Identifier IRON = ident("iron");
	static final Identifier GOLD = ident("gold");
	static final Identifier DIAMOND = ident("diamond");
	static final Identifier NETHERITE = ident("netherite");
	
	static void register() {
		PartSetRegistry.registerPartSet(ToolMaterials.WOOD, WOOD);
		PartSetRegistry.registerPartSet(ToolMaterials.STONE, STONE);
		PartSetRegistry.registerPartSet(ToolMaterials.IRON, IRON);
		PartSetRegistry.registerPartSet(ToolMaterials.GOLD, GOLD);
		PartSetRegistry.registerPartSet(ToolMaterials.DIAMOND, DIAMOND);
		PartSetRegistry.registerPartSet(ToolMaterials.NETHERITE, NETHERITE);
		NafisToolMaterials.registerAllParts();
	}
	
}

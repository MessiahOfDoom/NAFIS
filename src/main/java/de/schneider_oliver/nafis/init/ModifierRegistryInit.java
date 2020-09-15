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
import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import de.schneider_oliver.nafis.modifiers.modifiers.CheapModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.DullModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.DurableModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.EfficientModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.HeavyModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.HellishModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.ShinyModifier;
import net.minecraft.util.Identifier;

class ModifierRegistryInit {

	private static final Identifier CHEAP = ident("cheap");
	private static final Identifier DULL = ident("dull");
	private static final Identifier DURABLE = ident("durable");
	private static final Identifier SHINY = ident("shiny");
	private static final Identifier HEAVY = ident("heavy");
	private static final Identifier HELLISH = ident("hellish");
	
	private static final Identifier EFFICIENT = ident("efficient");
	
	static void register() {
		ModifierRegistry.registerModifier(CHEAP, new CheapModifier(CHEAP));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.WOOD, CHEAP);
		ModifierRegistry.registerModifier(DULL, new DullModifier(DULL));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.GOLD, DULL);
		ModifierRegistry.registerModifier(DURABLE, new DurableModifier(DURABLE));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.IRON, DURABLE);
		ModifierRegistry.addModifierToMaterial(NafisToolMaterials.COPPER.getIdentifier(), DURABLE);
		ModifierRegistry.registerModifier(SHINY, new ShinyModifier(SHINY));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.DIAMOND, SHINY);
		ModifierRegistry.registerModifier(HEAVY, new HeavyModifier(HEAVY));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.STONE, HEAVY);
		ModifierRegistry.addModifierToMaterial(NafisToolMaterials.REINFORCED_STONE.getIdentifier(), HEAVY);
		ModifierRegistry.registerModifier(HELLISH, new HellishModifier(HELLISH));
		ModifierRegistry.addModifierToMaterial(ToolMaterialRegistry.NETHERITE, HELLISH);
		
		
		
		ModifierRegistry.registerModifier(EFFICIENT, new EfficientModifier(EFFICIENT));
		ModifierRegistry.registerApplier(new EfficientModifier.Applier(EFFICIENT));
		
	}
	
}

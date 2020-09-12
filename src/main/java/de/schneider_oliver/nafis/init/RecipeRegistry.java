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

import static de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipeRegistry.ABSTRACT_FORGE;
import static de.schneider_oliver.nafis.utils.IdentUtils.*;

import de.schneider_oliver.nafis.recipe.RecipeToolRepair;
import de.schneider_oliver.nafis.recipe.sets.NafisSets;
import de.schneider_oliver.nafis.recipe.toolforge.AbstractForgeRecipeReplacable;
import de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {


	public static SpecialRecipeSerializer<RecipeToolRepair> TOOL_REPAIR_SERIALIZER;

	static void register() {
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipeReplacable(NafisSets.setByID(TOOL_PART_PICKAXE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ToolTypeRegistry.NAFIS_PICKAXE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipeReplacable(NafisSets.setByID(TOOL_PART_AXE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ToolTypeRegistry.NAFIS_AXE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipeReplacable(NafisSets.setByID(TOOL_PART_SHOVEL_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ToolTypeRegistry.NAFIS_SHOVEL);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipeReplacable(NafisSets.setByID(TOOL_PART_HOE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ToolTypeRegistry.NAFIS_HOE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipeReplacable(NafisSets.setByID(TOOL_PART_BROADSWORD_HEAD), NafisSets.setByID(TOOL_PART_WIDE_GUARD), NafisSets.setByID(TOOL_PART_WEAPON_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ToolTypeRegistry.NAFIS_BROADSWORD);
			}
		});
		TOOL_REPAIR_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, ident("tool_repair"), new SpecialRecipeSerializer<RecipeToolRepair>(RecipeToolRepair::new));
		
	}

}

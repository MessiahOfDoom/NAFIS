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
package de.schneider_oliver.nafis.recipe.toolforge;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.Inventory;
import net.minecraft.util.Identifier;

public class ForgeRecipeRegistry {

	private static HashMap<Identifier, ArrayList<ForgeRecipe>> recipes = new HashMap<>();
	public static final Identifier ABSTRACT_FORGE = ident("abstract_forge");
	
	public static void addRecipe(Identifier identifier, ForgeRecipe recipe) {
		recipes.compute(identifier, (k, v) -> {
			if(v == null) {
				v = new ArrayList<>();
			}
			if(!v.contains(recipe))v.add(recipe);
			return v;
		});
	}

	public static ForgeRecipe findMatch(Identifier identifier, Inventory inventory) {
		ArrayList<ForgeRecipe> r1 = recipes.getOrDefault(identifier, new ArrayList<>());
		for(ForgeRecipe r: r1) {
			try {
				if(r.matches(inventory)) {
					return r;
				}
			}catch(Exception e) {
				r1.remove(r);
				
			}
		}
		ArrayList<ForgeRecipe> r2 = recipes.getOrDefault(ABSTRACT_FORGE, new ArrayList<>());
		for(ForgeRecipe r: r2) {
			try {
				if(r.matches(inventory)) {
					return r;
				}
			}catch(Exception e) {
				r2.remove(r);
				
			}
		}
		return null;
		
	}
	
}

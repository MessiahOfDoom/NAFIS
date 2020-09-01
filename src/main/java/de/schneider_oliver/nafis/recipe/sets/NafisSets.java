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
package de.schneider_oliver.nafis.recipe.sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class NafisSets {

	
	private static HashMap<Identifier, Set<Item>> oredict = new HashMap<>();
	
	public static Set<Item> setByID(Identifier id) {
		return oredict.compute(id, (k, v) -> {
			return v == null ? new HashSet<>() : v;
		});
	}
	
	public static void registerItem(Item item, Identifier id) {
		setByID(id).add(item);
	}
	
	public static void unregisterItem(Item item, Identifier id) {
		setByID(id).remove(item);
	}
	
}

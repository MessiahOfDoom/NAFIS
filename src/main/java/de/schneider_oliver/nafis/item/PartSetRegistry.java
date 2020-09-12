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
package de.schneider_oliver.nafis.item;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import java.util.HashMap;
import java.util.Map.Entry;

import de.schneider_oliver.nafis.api.ToolPartFactory;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartSetRegistry {

	private static HashMap<String, ToolPartFactory> factories = new HashMap<>();
	
	public static boolean addFactory(String identifierSuffix, ToolPartFactory factory) {
		return addFactory(identifierSuffix, factory, false);
	}
	
	public static boolean addFactory(String identifierSuffix, ToolPartFactory factory, boolean overwrite) {
		if(factories.containsKey(identifierSuffix) && !overwrite)return false;
		else return factories.put(identifierSuffix, factory) == null;
	}
	
	public static void registerPartSet(ToolMaterial mat, Identifier materialName) {
		for(Entry<String, ToolPartFactory> entry: factories.entrySet()) {
			Registry.register(Registry.ITEM, ident(materialName.getNamespace(), materialName.getPath() + entry.getKey()), entry.getValue().create(mat, materialName).asItem());
		}
	}
	
}

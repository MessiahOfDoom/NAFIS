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

import de.schneider_oliver.nafis.item.item.NafisAxe;
import de.schneider_oliver.nafis.item.item.NafisHoe;
import de.schneider_oliver.nafis.item.item.NafisPickaxe;
import de.schneider_oliver.nafis.item.item.NafisShovel;
import de.schneider_oliver.nafis.item.item.NafisSword;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

class ToolTypeRegistry {

	public static final Item NAFIS_PICKAXE = new NafisPickaxe(new Item.Settings());
	public static final Item NAFIS_AXE = new NafisAxe(new Item.Settings());
	public static final Item NAFIS_SHOVEL = new NafisShovel(new Item.Settings());
	public static final Item NAFIS_HOE = new NafisHoe(new Item.Settings());
	public static final Item NAFIS_BROADSWORD = new NafisSword(new Item.Settings());
	
	static void register() {
		Registry.register(Registry.ITEM, ident("nafis_pickaxe"), NAFIS_PICKAXE);
		Registry.register(Registry.ITEM, ident("nafis_axe"), NAFIS_AXE);
		Registry.register(Registry.ITEM, ident("nafis_shovel"), NAFIS_SHOVEL);
		Registry.register(Registry.ITEM, ident("nafis_hoe"), NAFIS_HOE);
		Registry.register(Registry.ITEM, ident("nafis_broadsword"), NAFIS_BROADSWORD);
	}
	
}

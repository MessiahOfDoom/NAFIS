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

import java.util.List;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface ForgeRecipe {

	
	public boolean matches(Inventory inv) throws Exception;
	
	public void craft(Inventory inv);
	
	public void consume(Inventory inv);
	
	default boolean doStacksMatch(ItemStack left, ItemStack right) {
		return areStacksEqualIgnoreCount(left, right) && left.getCount() <= right.getCount();
	}
	
	default boolean areStacksEqualIgnoreCount(ItemStack left, ItemStack right) {
		return ItemStack.areItemsEqual(left, right) && ItemStack.areTagsEqual(left, right);
	}
	
	public List<List<ItemStack>> getInputs();
	
	public ItemStack getCraftedStackNoNBT();
}

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

import java.util.ArrayList;

import de.schneider_oliver.nafis.item.item.AbstractToolComponent;
import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ReplacePartRecipe extends AbstractForgeRecipe {

	public ReplacePartRecipe(Object... ingredients) {
		super(ingredients);
	}
	
	@Override
	public ItemStack getCraftedStackNoNBT() {
		return null;
	}
	
	@Override
	public void craft(Inventory inv) {
		ItemStack out = inv.getStack(0).copy();
		ArrayList<ItemStack> items = NafisTool.getComponents(out);
		for(int i = 0; i < inv.size() - 1; i++) {
			if(!inv.getStack(i).isEmpty() && inv.getStack(i).getItem() instanceof AbstractToolComponent) {
				int j = 0;
				for(ItemStack stack: items) {
					if(stack.getItem() instanceof AbstractToolComponent) {
						if(((AbstractToolComponent)stack.getItem()).partIdentifier.equals(((AbstractToolComponent)inv.getStack(i).getItem()).partIdentifier)) {
							items.set(j, inv.getStack(i));
						}
					}
					j++;
				}
				items.add(inv.getStack(i));
			}
		}
		NafisTool.setComponents(out, items);
		inv.setStack(inv.size() - 1, out);
	}

}

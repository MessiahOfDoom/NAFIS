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
package de.schneider_oliver.nafis.recipe;

import de.schneider_oliver.nafis.init.RecipeRegistry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.common.item.PatchouliItems;

public class RecipeGuideBook extends SpecialCraftingRecipe{

	public RecipeGuideBook(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		boolean hasBook = false, hasPick = false;

		for(int i = 0; i < inv.size(); ++i) {
			ItemStack itemStack2 = inv.getStack(i);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.getItem().equals(Items.BOOK)) {
					if (hasBook) {
						return false;
					}
					hasBook = true;
				} else if (itemStack2.getItem().equals(Items.WOODEN_PICKAXE)) {
					if (hasPick) {
						return false;
					}
					hasPick = true;
				} else {
					return false;
				}
			}
		}
		return hasBook && hasPick;
	}

	@Override
	public ItemStack craft(CraftingInventory inv) {
		ItemStack out = new ItemStack(PatchouliItems.book);
		out.getOrCreateTag().putString("patchouli:book", "nafis:guide_book");
		return out;
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeRegistry.GUIDE_SERIALIZER;
	}

}

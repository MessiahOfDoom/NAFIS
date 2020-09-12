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

import static de.schneider_oliver.nafis.utils.Strings.TOOL_LEVEL_MODULE;

import java.util.List;

import com.google.common.collect.Lists;

import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.config.ToolLevelingModule;
import de.schneider_oliver.nafis.init.RecipeRegistry;
import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RecipeToolRepair extends SpecialCraftingRecipe{

	public RecipeToolRepair(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		ItemStack toolStack = ItemStack.EMPTY;
		List<ItemStack> list = Lists.newArrayList();

		for(int i = 0; i < inv.size(); ++i) {
			ItemStack itemStack2 = inv.getStack(i);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.getItem() instanceof NafisTool) {
					if (!toolStack.isEmpty()) {
						return false;
					}

					toolStack = itemStack2;
				} else {
					list.add(itemStack2);
				}
			}
		}
		if(!toolStack.isEmpty()) {
			Ingredient in = ((NafisTool)toolStack.getItem()).getRepairIngredient(toolStack);
			for(ItemStack stack: list) {
				if(!in.test(stack))return false;
			}
			return !list.isEmpty() && list.size() <= Math.ceil(toolStack.getDamage() * 4F / toolStack.getMaxDamage());
		}

		return false;
	}

	@Override
	public ItemStack craft(CraftingInventory inv) {
		ItemStack toolStack = ItemStack.EMPTY;
		List<ItemStack> list = Lists.newArrayList();
		
		for(int i = 0; i < inv.size(); ++i) {
			ItemStack itemStack2 = inv.getStack(i);
			if (!itemStack2.isEmpty()) {
				if (itemStack2.getItem() instanceof NafisTool) {
					if (!toolStack.isEmpty()) {
						return ItemStack.EMPTY;
					}

					toolStack = itemStack2.copy();
				} else {
					list.add(itemStack2);
				}
			}
		}
		
		if(!toolStack.isEmpty()) {
			Ingredient in = ((NafisTool)toolStack.getItem()).getRepairIngredient(toolStack);
			for(ItemStack stack: list) {
				if(!in.test(stack))return ItemStack.EMPTY;
			}
			
			if(!list.isEmpty() && list.size() <= Math.ceil(toolStack.getDamage() * 4F / toolStack.getMaxDamage())) {
				for (int i = 0; i < list.size(); i++) {
					int j = Math.min(toolStack.getDamage(), (int)Math.ceil(toolStack.getMaxDamage() / 4F));
					toolStack.setDamage(toolStack.getDamage() - j);
				}
				NafisTool.setBroken(toolStack, false);
				if(!NafisTool.isBroken(toolStack) && ConfigModules.<ToolLevelingModule>getCachedModuleByName(TOOL_LEVEL_MODULE, false).levelingEnabled.get()) {
					((NafisTool)toolStack.getItem()).incrementAmountRepaired(toolStack, list.size());
				}
				return toolStack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeRegistry.TOOL_REPAIR_SERIALIZER;
	}

}

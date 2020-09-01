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
package de.schneider_oliver.nafis.block.entity;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipe;
import de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipeRegistry;
import de.schneider_oliver.nafis.screen.handler.BasicForgeScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;

public class BasicForgeBlockEntity extends AbstractForgeBlockEntity{

	public BasicForgeBlockEntity() {
		super(BlockEntityRegistry.BASIC_FORGE_BLOCK_ENTITY, 3);
	}

	@Override
	void checkCrafting() {
		ForgeRecipe recipe = ForgeRecipeRegistry.findMatch(ident("basic_forge"), this);
		if(recipe != null) {
			recipe.craft(this);
		}
		this.recipe = recipe;
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new BasicForgeScreenHandler(syncId, inv, this);
	}

}

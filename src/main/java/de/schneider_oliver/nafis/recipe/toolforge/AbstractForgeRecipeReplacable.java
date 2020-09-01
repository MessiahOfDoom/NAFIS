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

import static de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipeRegistry.ABSTRACT_FORGE;

public abstract class AbstractForgeRecipeReplacable extends AbstractForgeRecipe {

	public AbstractForgeRecipeReplacable(Object... ingredients) {
		super(ingredients);
		for(Object o: ingredients) {
			ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new ReplacePartRecipe(this.getCraftedStackNoNBT().getItem(), o));
		}
	}
	
}

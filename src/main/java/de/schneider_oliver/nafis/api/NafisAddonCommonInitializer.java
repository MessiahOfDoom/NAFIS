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
package de.schneider_oliver.nafis.api;

import de.schneider_oliver.nafis.item.PartSetRegistry;
import de.schneider_oliver.nafis.item.item.NafisTool;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import de.schneider_oliver.nafis.recipe.toolforge.AbstractForgeRecipe;

public interface NafisAddonCommonInitializer {

	/**
	 * This is the first initializer that gets called.
	 * Optimally, this should only contain calls to {@link PartSetRegistry#addFactory},
	 * although no checks are run against that, so if needed, other stuff can be initialized here too.
	 */
	public void initializeToolParts();
	
	/**
	 * This is the second initializer that gets called.
	 * Optimally, this should only contain calls to {@link PartSetRegistry#registerPartSet},
	 * although no checks are run against that, so if needed, other stuff can be initialized here too.
	 */
	public void initializeToolMaterials();
	
	/**
	 * This is the third initializer that gets called.
	 * Optimally, this should only register Items that are {@link NafisTool}s,
	 * although no checks are run against that, so if needed, other stuff can be initialized here too.
	 */
	public void initializeToolTypes();
	
	/**
	 * This is the fourth and final initializer that gets called.
	 * This is meant for stuff like {@link AbstractForgeRecipe}s, {@link Modifier}s, as well as anything else you might want to register.
	 */
	public void initializeOther();
}

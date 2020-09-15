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

import net.minecraft.item.ItemStack;

public interface ModifierApplier {

	/**
	 * Tests whether it should apply it's modifier based on the stack, then applies it if needed.
	 * Only meant for modifiers that aren't applied by tool parts, because they require special logic.
	 * @param stack The NafisTool stack
	 */
	public void apply(ItemStack stack);
	
}

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
package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemStack;

public class NafisSword extends AbstractNafisTool{

	public NafisSword(Settings settings) {
		super(settings);
	}

	@Override
	public int damageAfterMine(ItemStack stack) {
		return 2;
	}

	@Override
	public int damageAfterHit(ItemStack stack) {
		return 1;
	}

	@Override
	public boolean canLevelMining(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canLevelAttack(ItemStack stack) {
		return true;
	}

}

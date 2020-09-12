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
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class NafisShovel extends AbstractNafisTool {

	public NafisShovel(Settings settings) {
		super(settings);
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		if(NafisTool.isBroken(context.getStack()))return ActionResult.PASS;
		ActionResult result = Items.DIAMOND_SHOVEL.useOnBlock(context);
		if(context.getStack().getDamage() + 1 >= context.getStack().getMaxDamage()) {
			NafisTool.setBroken(context.getStack(), true);
		}
		return result;
	}

	@Override
	public int damageAfterMine(ItemStack stack) {
		return 1;
	}

	@Override
	public int damageAfterHit(ItemStack stack) {
		return 2;
	}

	@Override
	public boolean canLevelMining(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canLevelAttack(ItemStack stack) {
		return false;
	}
	
}

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
package de.schneider_oliver.nafis.modifiers.modifiers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class AbstractModifier implements Modifier{

	private Identifier identifier;
	
	public AbstractModifier(Identifier id) {
		identifier = id;
	}
	
	@Override
	public Identifier getIdentifier() {
		return identifier;
	}
	
	@Override
	public float getMiningSpeedMod(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return 0;
	}

	@Override
	public float getMiningSpeedMult(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return 1;
	}

	@Override
	public int getMiningLevelMod(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return 0;
	}

	@Override
	public int getMaxDamageMod(ItemStack stack) {
		return 0;
	}

	@Override
	public float getMaxDamageMult(ItemStack stack) {
		return 1;
	}

	@Override
	public float getAttackMod(ItemStack stack) {
		return 0;
	}

	@Override
	public float getAttackMult(ItemStack stack) {
		return 1;
	}

	@Override
	public float getAttackSpeedMod(ItemStack stack) {
		return 0;
	}

	@Override
	public float getAttackSpeedMult(ItemStack stack) {
		return 1;
	}

	@Override
	public int damageAfterMineMod(ItemStack stack, BlockState state) {
		return 0;
	}

	@Override
	public int damageAfterHitMod(ItemStack stack, LivingEntity target) {
		return 0;
	}

	@Override
	public int repairMatNeededMod(ItemStack stack) {
		return 0;
	}
}

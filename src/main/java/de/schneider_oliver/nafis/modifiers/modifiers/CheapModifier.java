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

import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_MODIFIER_CHEAP;

import de.schneider_oliver.doomedfabric.utils.TextColorUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class CheapModifier extends AbstractModifier{

	public CheapModifier(Identifier id) {
		super(id);
	}

	@Override
	public MutableText getTooltip(ItemStack stack) {
		return new TranslatableText(TRANSLATION_KEY_MODIFIER_CHEAP, getLevelString(stack)).styled(TextColorUtils.of(0xBB4400));
	}

	@Override
	public int repairMatNeededMod(ItemStack stack) {
		return -1 * getLevel(stack);
	}
	
}

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

import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_MODIFIER_SHINY;

import java.util.Map;

import de.schneider_oliver.doomedfabric.utils.TextColorUtils;
import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ShinyModifier extends AbstractModifier{

	public ShinyModifier(Identifier id) {
		super(id);
	}

	@Override
	public MutableText getTooltip(ItemStack stack) {
		return new TranslatableText(TRANSLATION_KEY_MODIFIER_SHINY, getLevelString(stack)).styled(TextColorUtils.of(0x88FFFF));
	}

	@Override
	public ItemStack apply(ItemStack stack, int level) {
		stack = super.apply(stack, level);
		if(stack.getItem() instanceof NafisTool) {
			NafisTool tool = (NafisTool)stack.getItem();	
			Map<Enchantment, Integer> map = EnchantmentHelper.get(stack);
			if(tool.canLevelAttack(stack)) map.put(Enchantments.LOOTING, level);
			if(tool.canLevelMining(stack)) map.put(Enchantments.FORTUNE, level);
			EnchantmentHelper.set(map, stack);
		}
		
		return stack;
	}
	
}

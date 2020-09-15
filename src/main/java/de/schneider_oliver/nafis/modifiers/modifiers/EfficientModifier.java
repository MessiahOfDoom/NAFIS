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

import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_MODIFIER_EFFICIENT;

import java.util.ArrayList;
import java.util.HashMap;

import de.schneider_oliver.doomedfabric.utils.TextColorUtils;
import de.schneider_oliver.nafis.api.ModifierApplier;
import de.schneider_oliver.nafis.item.item.AbstractToolComponent;
import de.schneider_oliver.nafis.item.item.NafisTool;
import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class EfficientModifier extends AbstractModifier{

	public EfficientModifier(Identifier id) {
		super(id);
	}
	
	@Override
	public MutableText getTooltip(ItemStack stack) {
		return new TranslatableText(TRANSLATION_KEY_MODIFIER_EFFICIENT, getLevelString(stack)).styled(TextColorUtils.of(0x44FF00));
	}
	
	@Override
	public float getMiningSpeedMult(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return (float) Math.pow(1.25F, getLevel(stack));
	}
	
	public static class Applier implements ModifierApplier {

		private Identifier id;
		
		public Applier(Identifier modID) {
			id = modID;
		}
		
		@Override
		public void apply(ItemStack stack) {
			if(stack.getItem() instanceof NafisTool) {
				ArrayList<ItemStack> stacks = ((NafisTool)stack.getItem()).getComponents(stack);
				HashMap<Identifier, Integer> amounts = new HashMap<>();
				for(ItemStack s: stacks) {
					if(s.getItem() instanceof AbstractToolComponent) {
						amounts.compute(((AbstractToolComponent)s.getItem()).getMaterialIdentifier(), (k,v) -> {
							if(v == null) v = 0;
							v += 1;
							return v;
						});
					}
				}
				int level = amounts.values().stream().sorted((a, b) -> b.compareTo(a)).findFirst().orElseGet(() -> 0) - 1;
				if(level > 0) {
					ModifierRegistry.getModifier(id).apply(stack, level);
				}
			}
		}
		
	}
}

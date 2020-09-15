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

import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_MODIFIER_HEAVY;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.schneider_oliver.doomedfabric.utils.TextColorUtils;
import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class HeavyModifier extends AbstractModifier{

	private static final UUID KNOCKBACK_UU = UUID.fromString("01ebd0f8-253a-4f0b-8bff-c55c7eeb8727");
	
	public HeavyModifier(Identifier id) {
		super(id);
	}

	@Override
	public MutableText getTooltip(ItemStack stack) {
		return new TranslatableText(TRANSLATION_KEY_MODIFIER_HEAVY, getLevelString(stack)).styled(TextColorUtils.of(0x4D4D4D));
	}

	@Override
	public float getAttackSpeedMod(ItemStack stack) {
		return -0.5F * getLevel(stack);
	}
	
	@Override
	public float getMiningSpeedMod(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return -0.5F * getLevel(stack);
	}
	
	@Override
	public int getMiningLevelMod(ItemStack stack, BlockState state, LivingEntity breakingEntity) {
		return 1;
	}
	
	@Override
	public boolean hasAttributeModifiers(ItemStack stack) {
		if(stack.getItem() instanceof NafisTool) {
			NafisTool tool = (NafisTool)stack.getItem();
			return tool.canLevelAttack(stack);
		}
		return false;
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack) {
		Multimap<EntityAttribute, EntityAttributeModifier> out = HashMultimap.create(1,1);
		out.put(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, new EntityAttributeModifier(KNOCKBACK_UU, "heavy_modifier", getLevel(stack) * 1.5F, Operation.ADDITION));
		return out;
	}
}

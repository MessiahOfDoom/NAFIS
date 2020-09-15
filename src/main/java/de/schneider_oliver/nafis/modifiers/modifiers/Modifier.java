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

import static de.schneider_oliver.nafis.utils.NBTKeys.KEY_MODIFIER;

import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public interface Modifier {

	public Identifier getIdentifier();

	public float getMiningSpeedMod(ItemStack stack, BlockState state, LivingEntity breakingEntity);
	public float getMiningSpeedMult(ItemStack stack, BlockState state, LivingEntity breakingEntity);
	
	public int getMiningLevelMod(ItemStack stack, BlockState state, LivingEntity breakingEntity);
	
	public int getMaxDamageMod(ItemStack stack);
	public float getMaxDamageMult(ItemStack stack);

	public float getAttackMod(ItemStack stack);
	public float getAttackMult(ItemStack stack);

	public float getAttackSpeedMod(ItemStack stack);
	public float getAttackSpeedMult(ItemStack stack);
	
	public int damageAfterMineMod(ItemStack stack, BlockState state);
	
	public int damageAfterHitMod(ItemStack stack, LivingEntity target);
	
	public int repairMatNeededMod(ItemStack stack);
	
	public MutableText getTooltip(ItemStack stack);
	
	public default int getLevel(ItemStack stack) {
		return getLevel(stack.getOrCreateSubTag(KEY_MODIFIER));
	}
	
	public default int getLevel(CompoundTag tag) {
		return tag.getInt(getNBTKey());
	}
	
	public default String getNBTKey() {
		return getIdentifier().toString();
	}
	
	public default boolean isStacking() {
		return true;
	}
	
	public default CompoundTag apply(CompoundTag tag, int level) {
		tag.putInt(getNBTKey(), level);
		return tag;
	}
	
	public default ItemStack apply(ItemStack stack, int level) {
		CompoundTag tag = stack.getOrCreateSubTag(KEY_MODIFIER);
		tag = apply(tag, level);
		stack.putSubTag(KEY_MODIFIER, tag);
		return stack;
	}
	
	public default String getLevelString(ItemStack stack) {
		return getLevel(stack) > 1 ? " " + getLevel(stack) : "";
	}
	
	public default boolean _equals(Modifier mod) {
		return getIdentifier().equals(mod.getIdentifier());
	}
	
	public default boolean hasAttributeModifiers(ItemStack stack) {
		return false;
	}
	
	public default Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack){
		return null;
	}
}

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
package de.schneider_oliver.nafis.modifiers;

import static de.schneider_oliver.nafis.utils.IdentUtils.IDENT_UNKNOWN;
import static de.schneider_oliver.nafis.utils.Strings.TRANSLATION_KEY_BUGGED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.schneider_oliver.nafis.api.ModifierApplier;
import de.schneider_oliver.nafis.modifiers.modifiers.AbstractModifier;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ModifierRegistry {

	private static ArrayList<ModifierApplier> appliers = new ArrayList<>();
	private static HashMap<Identifier, Modifier> registeredModifiers = new HashMap<>();
	private static Multimap<Identifier, Identifier> registeredModifiersByMat = MultimapBuilder.treeKeys().hashSetValues().build();
	
	public static final Modifier EMPTY_MODIFIER = new AbstractModifier(IDENT_UNKNOWN) {
		@Override
		public MutableText getTooltip(ItemStack stack) {
			return new TranslatableText(TRANSLATION_KEY_BUGGED);
		}
	};
	
	public static void registerApplier(ModifierApplier applier) {
		if(!appliers.contains(applier))appliers.add(applier);
	}
	
	public static void registerModifier(Identifier identifier, Modifier mod) {
		registeredModifiers.put(identifier, mod);
	}
	
	public static Modifier getModifier(Identifier identifier) {
		return registeredModifiers.getOrDefault(identifier, EMPTY_MODIFIER);
	}
	
	public static void addModifierToMaterial(Identifier materialIdentifier, Identifier modifierIdentifier) {
		registeredModifiersByMat.put(materialIdentifier, modifierIdentifier);
	}
	
	public static Set<Modifier> getModifiersByMaterial(Identifier materialIdentifier){
		return registeredModifiersByMat.get(materialIdentifier).stream().map(a -> getModifier(a)).filter(a -> !a._equals(EMPTY_MODIFIER)).collect(Collectors.toSet());
	}
	
	public static void apply(ItemStack stack) {
		for(ModifierApplier a : appliers) {
			a.apply(stack);
		}
	}
	
}

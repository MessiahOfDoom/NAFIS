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

import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public interface ToolComponent extends ItemConvertible{

	public CompoundTag writeStatsToTag(CompoundTag tagIn);
	
	public CompoundTag writeOtherToTag(CompoundTag tagIn);
	
	public String translationKeyMaterial();
	
	public String translationKeyPart();
	
	public default Text getName() {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
	
	public default Text getName(ItemStack stack) {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
	
	public default ItemStack writeModifiersToStack(ItemStack stack) {
		for(Modifier m: ModifierRegistry.getModifiersByMaterial(getMaterialIdentifier())) {
			stack = m.apply(stack, m.isStacking() ? m.getLevel(stack) + 1 : m.getLevel(stack) > 1 ? m.getLevel(stack) : 1);
		}
		return stack;
	}
	
	public Identifier getMaterialIdentifier();
}

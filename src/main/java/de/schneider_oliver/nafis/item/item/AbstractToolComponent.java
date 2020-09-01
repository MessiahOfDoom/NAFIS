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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public abstract class AbstractToolComponent extends Item{

	public Identifier materialIdentifier;
	public Identifier partIdentifier;
	
	public AbstractToolComponent(Settings settings, Identifier partIdentifier, Identifier materialIdentifier) {
		super(settings);
		this.materialIdentifier = materialIdentifier;
		this.partIdentifier = partIdentifier;
	}

	public abstract CompoundTag writeStatsToTag(CompoundTag tagIn);
	
	public abstract CompoundTag writeOtherToTag(CompoundTag tagIn);
	
	public String translationKeyMaterial() {
		return "material." + materialIdentifier.getNamespace() + "." + materialIdentifier.getPath();
	}
	
	public String translationKeyPart() {
		return "part." + partIdentifier.getNamespace() + "." + partIdentifier.getPath();
	}
	
	
	@Override
	public Text getName() {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
	
	@Override
	public Text getName(ItemStack stack) {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
}

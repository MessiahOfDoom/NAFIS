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

import java.util.List;

import de.schneider_oliver.nafis.modifiers.ModifierRegistry;
import de.schneider_oliver.nafis.modifiers.modifiers.Modifier;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class AbstractToolComponent extends Item implements ToolComponent{

	public Identifier materialIdentifier;
	public Identifier partIdentifier;
	
	public AbstractToolComponent(Settings settings, Identifier partIdentifier, Identifier materialIdentifier) {
		super(settings);
		this.materialIdentifier = materialIdentifier;
		this.partIdentifier = partIdentifier;
	}
	
	public String translationKeyMaterial() {
		return "material." + materialIdentifier.getNamespace() + "." + materialIdentifier.getPath();
	}
	
	public String translationKeyPart() {
		return "part." + partIdentifier.getNamespace() + "." + partIdentifier.getPath();
	}
	
	@Override
	public Text getName() {
		return ToolComponent.super.getName();
	}
	
	@Override
	public Text getName(ItemStack stack) {
		return ToolComponent.super.getName(stack);
	}
	
	@Override
	public Identifier getMaterialIdentifier() {
		return materialIdentifier;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		for(Modifier m: ModifierRegistry.getModifiersByMaterial(getMaterialIdentifier())) {
			tooltip.add(m.getTooltip(stack));
		}
	}
}

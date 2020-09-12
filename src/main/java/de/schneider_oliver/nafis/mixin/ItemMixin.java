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
package de.schneider_oliver.nafis.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.Item;

@Mixin(Item.class)
public interface ItemMixin {

	@Accessor("ATTACK_DAMAGE_MODIFIER_ID")
	public static UUID getADMI() {throw new AssertionError();};
	
	@Accessor("ATTACK_SPEED_MODIFIER_ID")
	public static UUID getASMI() {throw new AssertionError();};
	
}

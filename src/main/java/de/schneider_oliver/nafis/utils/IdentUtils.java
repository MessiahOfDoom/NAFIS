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
package de.schneider_oliver.nafis.utils;

import net.minecraft.util.Identifier;

public class IdentUtils {

	public static final Identifier IDENT_UNKNOWN = ident("unknown");
	
	public static final Identifier TOOL_PART_BASIC_HANDLE = ident("basic_tool_handle");
	public static final Identifier TOOL_PART_PICKAXE_HEAD = ident("pickaxe_head");
	public static final Identifier TOOL_PART_AXE_HEAD = ident("axe_head");
	public static final Identifier TOOL_PART_SHOVEL_HEAD = ident("shovel_head");
	public static final Identifier TOOL_PART_HOE_HEAD = ident("hoe_head");
	
	public static final Identifier TOOL_PART_WEAPON_HANDLE = ident("basic_weapon_handle");
	public static final Identifier TOOL_PART_WIDE_GUARD = ident("wide_guard");
	public static final Identifier TOOL_PART_BROADSWORD_HEAD = ident("broadsword_head");
	
	public static Identifier ident(String name) {
		return new Identifier(Strings.MOD_ID, name);
	}
	
	public static Identifier ident(String modid, String name) {
		return new Identifier(modid, name);
	}
	
}

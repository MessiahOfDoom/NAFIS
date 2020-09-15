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

import static de.schneider_oliver.nafis.utils.Strings.MOD_ID;

public class NBTKeys {
	
	public static final String KEY_COMPONENTS = MOD_ID + ".components";
	public static final String KEY_TYPE = MOD_ID + ".type";
	public static final String KEY_PROPERTIES = MOD_ID + ".properties";
	public static final String KEY_OTHER = MOD_ID + ".other";
	public static final String KEY_LEVELING = MOD_ID + ".level";
	public static final String KEY_MODIFIER = MOD_ID + ".modifiers";
	
	
	public static final String SUBKEY_COMPONENT = "component_";
	public static final String SUBKEY_TOOLTYPE = "tooltype";
	public static final String SUBKEY_TOOLHANDLER = "handlermod";
	public static final String SUBKEY_MININGLEVEL = "mininglvl";
	public static final String SUBKEY_SPEEDMULT = "speedmult";
	public static final String SUBKEY_DURABILITY = "durability";
	public static final String SUBKEY_MATERIAL = "material";
	public static final String SUBKEY_ATTACKSPEED = "atkspeed";
	public static final String SUBKEY_ATTACKDAMAGE = "atkdamage";
	public static final String SUBKEY_REPAIRINGREDIENT = "repair";
	public static final String SUBKEY_BROKEN = "broken";
	
	public static final String SUBKEY_REPAIREDCOUNT = "repairedfraction";
	public static final String SUBKEY_REPAIREDLEVEL = "repairedlevel";
	public static final String SUBKEY_HARDNESSBROKEN = "hardnessbroken";
	public static final String SUBKEY_SPEEDLEVEL = "speedlevel";
	public static final String SUBKEY_DAMAGEDEALT = "damagedealt";
	public static final String SUBKEY_DAMAGELEVEL = "damagelevel";
	public static final String SUBKEY_ATTACKSDONE = "attacksdone";
	public static final String SUBKEY_ATTACKSPEEDLEVEL = "attackspeedlevel";
}

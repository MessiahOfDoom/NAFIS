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
package de.schneider_oliver.nafis.config;

import static de.schneider_oliver.nafis.utils.Strings.MODULE_DIR;
import static de.schneider_oliver.nafis.utils.Strings.TOOL_LEVEL_MODULE;

import de.schneider_oliver.doomedfabric.config.module.BaseModule;
import de.schneider_oliver.doomedfabric.config.values.BooleanConfigValue;
import de.schneider_oliver.doomedfabric.config.values.NumberConfigValue;
import de.schneider_oliver.doomedfabric.config.values.NumberConfigValue.NumberType;

public class ToolLevelingModule extends BaseModule{

	public BooleanConfigValue levelingEnabled = addConfigValue(new BooleanConfigValue("level-system-enabled", true));
	public NumberConfigValue durabilityRepairsNeeded = addConfigValue(new NumberConfigValue("durability-level-repairs", 20, NumberType.INT));
	public NumberConfigValue durabilityBoost = addConfigValue(new NumberConfigValue("durability-level-boost", 0.15, NumberType.FLOAT));
	public NumberConfigValue speedHardnessNeeded = addConfigValue(new NumberConfigValue("speed-level-hardness", 1500, NumberType.FLOAT));
	public NumberConfigValue speedBoost = addConfigValue(new NumberConfigValue("speed-level-boost", 0.1, NumberType.FLOAT));
	public NumberConfigValue attackDamageNeeded = addConfigValue(new NumberConfigValue("attack-level-damage", 300, NumberType.FLOAT));
	public NumberConfigValue attackDamageBoost = addConfigValue(new NumberConfigValue("attack-level-boost", 1, NumberType.FLOAT));
	public NumberConfigValue attackSpeedAttacksNeeded = addConfigValue(new NumberConfigValue("attack-speed-level-attacks", 200, NumberType.FLOAT));
	public NumberConfigValue attackSpeedBoost = addConfigValue(new NumberConfigValue("attack-speed-level-boost", 0.05, NumberType.FLOAT));
	
	
	
	@Override
	public String getModuleName() {
		return TOOL_LEVEL_MODULE;
	}

	@Override
	public String getModuleDirName() {
		return MODULE_DIR;
	}

}

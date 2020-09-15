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
	public NumberConfigValue durabilityRepairsNeeded = addConfigValue(new NumberConfigValue("durability-level-repairs", 5, NumberType.INT));
	public NumberConfigValue durabilityBoost = addConfigValue(new NumberConfigValue("durability-level-boost", 0.35, NumberType.FLOAT));
	public NumberConfigValue speedHardnessNeeded = addConfigValue(new NumberConfigValue("speed-level-hardness", 1500, NumberType.FLOAT));
	public NumberConfigValue speedBoost = addConfigValue(new NumberConfigValue("speed-level-boost", 0.1, NumberType.FLOAT));
	public NumberConfigValue attackDamageNeeded = addConfigValue(new NumberConfigValue("attack-level-damage", 350, NumberType.FLOAT));
	public NumberConfigValue attackDamageBoost = addConfigValue(new NumberConfigValue("attack-level-boost", 0.75, NumberType.FLOAT));
	public NumberConfigValue attackSpeedAttacksNeeded = addConfigValue(new NumberConfigValue("attack-speed-level-attacks", 300, NumberType.FLOAT));
	public NumberConfigValue attackSpeedBoost = addConfigValue(new NumberConfigValue("attack-speed-level-boost", 0.05, NumberType.FLOAT));
	
	
	
	@Override
	public String getModuleName() {
		return TOOL_LEVEL_MODULE;
	}

	@Override
	public String getModuleDirName() {
		return MODULE_DIR;
	}

	public int repairsNeededForLevel(int level) {
		return durabilityRepairsNeeded.get().intValue() * level;
	}
	
	public float hardnessNeededForLevel(int level) {
		return speedHardnessNeeded.get().floatValue() * level;
	}
	
	public float attackDamageNeededForLevel(int level) {
		return attackDamageNeeded.get().floatValue() * level;
	}
	
	public int attacksNeededForLevel(int level) {
		return attackSpeedAttacksNeeded.get().intValue() * level;
	}
	
	public float durabilityBoostForLevel(int level) {
		if(level == 0)return 0;
		return (float)Math.pow(durabilityBoost.get().floatValue(), (level / 2) + 1) + durabilityBoostForLevel(level - 1);
	}
	
	public float speedBoostForLevel(int level) {
		return speedBoost.get().floatValue() * level;
	}
	
	public float attackDamageBoostForLevel(int level) {
		return attackDamageBoost.get().floatValue() * level;
	}
	
	public float attackSpeedBoostForLevel(int level) {
		return attackSpeedBoost.get().floatValue() * level;
	}
}

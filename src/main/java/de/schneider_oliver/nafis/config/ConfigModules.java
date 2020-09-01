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

import static de.schneider_oliver.doomedfabric.config.Config.addModule;

import de.schneider_oliver.doomedfabric.config.Config;
import de.schneider_oliver.doomedfabric.config.module.BaseModule;

public class ConfigModules {

	public static void registerModules() {
		addModule(new ToolLevelingModule());
	}
	
	public static <T extends BaseModule> T getCachedModuleByName(String moduleName, boolean forceRecache) {
		return Config.getCachedModuleByName(moduleName, forceRecache);
	}
	
}

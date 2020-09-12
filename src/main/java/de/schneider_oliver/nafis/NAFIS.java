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
package de.schneider_oliver.nafis;

import de.schneider_oliver.nafis.api.NafisAddonCommonInitializer;
import de.schneider_oliver.nafis.block.BlockRegistry;
import de.schneider_oliver.nafis.block.entity.BlockEntityRegistry;
import de.schneider_oliver.nafis.config.ConfigModules;
import de.schneider_oliver.nafis.item.ItemRegistry;
import de.schneider_oliver.nafis.screen.ScreenHandlingRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class NAFIS implements ModInitializer{

	@Override
	public void onInitialize() {
		ConfigModules.registerModules();
		BlockRegistry.register();
		BlockEntityRegistry.register();
		ItemRegistry.register();
		ScreenHandlingRegistry.register();
		
		FabricLoader.getInstance().getEntrypoints("nafis:common", NafisAddonCommonInitializer.class).forEach(a -> {
			a.initializeToolParts();
		});
		FabricLoader.getInstance().getEntrypoints("nafis:common", NafisAddonCommonInitializer.class).forEach(a -> {
			a.initializeToolMaterials();
		});
		FabricLoader.getInstance().getEntrypoints("nafis:common", NafisAddonCommonInitializer.class).forEach(a -> {
			a.initializeToolTypes();
		});
		FabricLoader.getInstance().getEntrypoints("nafis:common", NafisAddonCommonInitializer.class).forEach(a -> {
			a.initializeOther();
		});
	}

}

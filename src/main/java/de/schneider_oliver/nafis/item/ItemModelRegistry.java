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
package de.schneider_oliver.nafis.item;

import de.schneider_oliver.nafis.render.CustomToolRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.rendering.BuiltinItemRendererRegistryImpl;

@Environment(EnvType.CLIENT)
public class ItemModelRegistry {

	public static void register() {
		BuiltinItemRendererRegistryImpl.INSTANCE.register(ItemRegistry.NAFIS_PICKAXE, new CustomToolRenderer());
		BuiltinItemRendererRegistryImpl.INSTANCE.register(ItemRegistry.NAFIS_AXE, new CustomToolRenderer());
		BuiltinItemRendererRegistryImpl.INSTANCE.register(ItemRegistry.NAFIS_SHOVEL, new CustomToolRenderer());
		BuiltinItemRendererRegistryImpl.INSTANCE.register(ItemRegistry.NAFIS_HOE, new CustomToolRenderer());
		BuiltinItemRendererRegistryImpl.INSTANCE.register(ItemRegistry.NAFIS_BROADSWORD, new CustomToolRenderer());
	}
	
}

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
package de.schneider_oliver.nafis.api;

import de.schneider_oliver.nafis.render.CustomToolRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;

public interface NafisAddonClientInitializer {

	/**
	 * This is the only client initializer that gets called.
	 * Optimally, this should only register {@link BuiltinItemRenderer} instances for your NAFIS-Tools,
	 * although no checks are run against that, so if needed, other stuff can be initialized here too.
	 * If you wish to use the default NAFIS renderer an Instance of that can be obtained through {@link CustomToolRenderer#getInstance}
	 */
	public void registerNafisToolRenderer();
	
}

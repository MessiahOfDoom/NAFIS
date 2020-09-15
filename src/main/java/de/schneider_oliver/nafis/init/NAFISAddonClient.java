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
package de.schneider_oliver.nafis.init;

import de.schneider_oliver.nafis.api.NafisAddonClientInitializer;

public class NAFISAddonClient implements NafisAddonClientInitializer{

	@Override
	public void registerNafisToolRenderer() {
		ItemModelRegistry.register();
	}

}

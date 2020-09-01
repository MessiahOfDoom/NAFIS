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
package de.schneider_oliver.nafis.screen;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.screen.handler.BasicForgeScreenHandler;
import de.schneider_oliver.nafis.screen.handler.ImprovedForgeScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlingRegistry {

	public static ScreenHandlerType<BasicForgeScreenHandler> BASIC_FORGE_SCREEN_HANDLER;
	public static ScreenHandlerType<ImprovedForgeScreenHandler> IMPROVED_FORGE_SCREEN_HANDLER;
	
	public static void register() {
		BASIC_FORGE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(ident("basic_forge_screen_handler"), BasicForgeScreenHandler::new);
		IMPROVED_FORGE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(ident("improved_forge_screen_handler"), ImprovedForgeScreenHandler::new);
	}
	
}

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

package de.schneider_oliver.nafis.screen;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.screen.handler.BasicForgeScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlingRegistry {

	public static ScreenHandlerType<BasicForgeScreenHandler> BASIC_FORGE_SCREEN_HANDLER;
	
	
	public static void register() {
		BASIC_FORGE_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(ident("basic_forge_screen_handler"), BasicForgeScreenHandler::new);
	}
	
}

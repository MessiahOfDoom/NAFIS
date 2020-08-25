package de.schneider_oliver.nafis;

import de.schneider_oliver.nafis.item.ItemModelRegistry;
import de.schneider_oliver.nafis.screen.screen.BasicForgeScreen;
import de.schneider_oliver.nafis.screen.screen.ImprovedForgeScreen;
import net.fabricmc.api.ClientModInitializer;

public class NAFISClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		ItemModelRegistry.register();
		BasicForgeScreen.register();
		ImprovedForgeScreen.register();
	}

}

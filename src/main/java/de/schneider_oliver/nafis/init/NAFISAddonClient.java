package de.schneider_oliver.nafis.init;

import de.schneider_oliver.nafis.api.NafisAddonClientInitializer;

public class NAFISAddonClient implements NafisAddonClientInitializer{

	@Override
	public void registerNafisToolRenderer() {
		ItemModelRegistry.register();
	}

}

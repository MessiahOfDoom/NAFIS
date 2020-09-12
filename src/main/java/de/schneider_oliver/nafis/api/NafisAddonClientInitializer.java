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

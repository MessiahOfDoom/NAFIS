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

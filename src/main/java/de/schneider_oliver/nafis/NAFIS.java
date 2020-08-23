package de.schneider_oliver.nafis;

import de.schneider_oliver.nafis.block.BlockRegistry;
import de.schneider_oliver.nafis.block.entity.BlockEntityRegistry;
import de.schneider_oliver.nafis.item.ItemRegistry;
import de.schneider_oliver.nafis.recipe.RecipeRegistry;
import de.schneider_oliver.nafis.screen.ScreenHandlingRegistry;
import net.fabricmc.api.ModInitializer;

public class NAFIS implements ModInitializer{

	@Override
	public void onInitialize() {
		BlockRegistry.register();
		BlockEntityRegistry.register();
		ItemRegistry.register();
		ScreenHandlingRegistry.register();
		RecipeRegistry.register();
	}

}

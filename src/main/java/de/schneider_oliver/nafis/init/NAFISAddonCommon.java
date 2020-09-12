package de.schneider_oliver.nafis.init;

import de.schneider_oliver.nafis.api.NafisAddonCommonInitializer;

public class NAFISAddonCommon implements NafisAddonCommonInitializer{

	@Override
	public void initializeToolParts() {
		PartTypeRegistry.register();
	}

	@Override
	public void initializeToolMaterials() {
		ToolMaterialRegistry.register();
	}

	@Override
	public void initializeToolTypes() {
		ToolTypeRegistry.register();
	}

	@Override
	public void initializeOther() {
		RecipeRegistry.register();
	}


}

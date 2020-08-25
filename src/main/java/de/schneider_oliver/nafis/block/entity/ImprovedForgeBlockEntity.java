package de.schneider_oliver.nafis.block.entity;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import de.schneider_oliver.nafis.recipe.ForgeRecipe;
import de.schneider_oliver.nafis.recipe.ForgeRecipeRegistry;
import de.schneider_oliver.nafis.screen.handler.ImprovedForgeScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;

public class ImprovedForgeBlockEntity extends AbstractForgeBlockEntity{

	public ImprovedForgeBlockEntity() {
		super(BlockEntityRegistry.IMPROVED_FORGE_BLOCK_ENTITY, 5);
	}

	@Override
	void checkCrafting() {
		ForgeRecipe recipe = ForgeRecipeRegistry.findMatch(ident("improved_forge"), this);
		if(recipe != null) {
			recipe.craft(this);
		}
		this.recipe = recipe;
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new ImprovedForgeScreenHandler(syncId, inv, this);
	}

}

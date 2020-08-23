package de.schneider_oliver.nafis.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface ForgeRecipe {

	
	public boolean matches(Inventory inv) throws Exception;
	
	public void craft(Inventory inv);
	
	public void consume(Inventory inv);
	
	default boolean doStacksMatch(ItemStack left, ItemStack right) {
		return areStacksEqualIgnoreCount(left, right) && left.getCount() <= right.getCount();
	}
	
	default boolean areStacksEqualIgnoreCount(ItemStack left, ItemStack right) {
		return ItemStack.areItemsEqual(left, right) && ItemStack.areTagsEqual(left, right);
	}
}

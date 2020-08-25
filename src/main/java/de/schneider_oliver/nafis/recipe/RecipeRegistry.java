package de.schneider_oliver.nafis.recipe;

import static de.schneider_oliver.nafis.recipe.ForgeRecipeRegistry.ABSTRACT_FORGE;
import static de.schneider_oliver.nafis.utils.IdentUtils.*;

import de.schneider_oliver.nafis.item.ItemRegistry;
import de.schneider_oliver.nafis.recipe.tags.NafisSets;
import net.minecraft.item.ItemStack;

public class RecipeRegistry {




	public static void register() {
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipe(NafisSets.setByID(TOOL_PART_PICKAXE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ItemRegistry.NAFIS_PICKAXE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipe(NafisSets.setByID(TOOL_PART_AXE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ItemRegistry.NAFIS_AXE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipe(NafisSets.setByID(TOOL_PART_SHOVEL_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ItemRegistry.NAFIS_SHOVEL);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipe(NafisSets.setByID(TOOL_PART_HOE_HEAD), NafisSets.setByID(TOOL_PART_BASIC_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ItemRegistry.NAFIS_HOE);
			}
		});
		ForgeRecipeRegistry.addRecipe(ABSTRACT_FORGE, new AbstractForgeRecipe(NafisSets.setByID(TOOL_PART_BROADSWORD_HEAD), NafisSets.setByID(TOOL_PART_WIDE_GUARD), NafisSets.setByID(TOOL_PART_WEAPON_HANDLE)) {

			@Override
			public ItemStack getCraftedStackNoNBT() {
				return new ItemStack(ItemRegistry.NAFIS_BROADSWORD);
			}
		});
	}

}

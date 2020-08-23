package de.schneider_oliver.nafis.item;

import static de.schneider_oliver.nafis.utils.IdentUtils.ident;

import java.util.function.Supplier;

import de.schneider_oliver.nafis.block.BlockRegistry;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;

public enum NafisToolMaterials implements ToolMaterial {
	   REINFORCED_STONE(1, 500, 3.5F, 1.5F, 5, () -> {
	      return Ingredient.ofItems(BlockRegistry.REINFORCED_STONE);
	   }),
	   COPPER(2, 450, 5.0F, 1.5F, 14, () -> {
	      return Ingredient.ofItems(ItemRegistry.COPPER_INGOT);
	   });

	   private final int miningLevel;
	   private final int itemDurability;
	   private final float miningSpeed;
	   private final float attackDamage;
	   private final int enchantability;
	   private final Lazy<Ingredient> repairIngredient;

	   private NafisToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
	      this.miningLevel = miningLevel;
	      this.itemDurability = itemDurability;
	      this.miningSpeed = miningSpeed;
	      this.attackDamage = attackDamage;
	      this.enchantability = enchantability;
	      this.repairIngredient = new Lazy<Ingredient>(repairIngredient);
	   }

	   public int getDurability() {
	      return this.itemDurability;
	   }

	   public float getMiningSpeedMultiplier() {
	      return this.miningSpeed;
	   }

	   public float getAttackDamage() {
	      return this.attackDamage;
	   }

	   public int getMiningLevel() {
	      return this.miningLevel;
	   }

	   public int getEnchantability() {
	      return this.enchantability;
	   }

	   public Ingredient getRepairIngredient() {
	      return (Ingredient)this.repairIngredient.get();
	   }
	   
	   public Identifier getIdentifier() {
		   return ident(this.name().toLowerCase());
	   }
	   
	   public static void registerAllParts() {
		   for(NafisToolMaterials mat: values()) {
			   PartSetRegistry.registerPartSet(mat, mat.getIdentifier());
		   }
	   }
	}

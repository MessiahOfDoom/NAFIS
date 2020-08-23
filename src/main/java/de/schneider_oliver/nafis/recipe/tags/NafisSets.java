package de.schneider_oliver.nafis.recipe.tags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class NafisSets {

	
	private static HashMap<Identifier, Set<Item>> oredict = new HashMap<>();
	
	public static Set<Item> setByID(Identifier id) {
		return oredict.compute(id, (k, v) -> {
			return v == null ? new HashSet<>() : v;
		});
	}
	
	public static void registerItem(Item item, Identifier id) {
		setByID(id).add(item);
	}
	
	public static void unregisterItem(Item item, Identifier id) {
		setByID(id).remove(item);
	}
	
}

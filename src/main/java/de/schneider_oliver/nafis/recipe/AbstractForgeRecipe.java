package de.schneider_oliver.nafis.recipe;

import java.util.ArrayList;
import java.util.Set;

import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

public abstract class AbstractForgeRecipe implements ForgeRecipe{

	private final Object[] ingredients;
	
	public AbstractForgeRecipe(Object... ingredients) {
		this.ingredients = ingredients;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean matches(Inventory inv) throws Exception{
		int index = 0;
		for(Object o: ingredients) {
			if(o instanceof Tag) {
				if(!((Tag<Item>)o).contains(inv.getStack(index).getItem())) return false;
			}else if(o instanceof Set) {
				if(!((Set<Item>)o).contains(inv.getStack(index).getItem())) return false;
			}else if(o instanceof ItemStack) {
				if(!doStacksMatch(((ItemStack)o), inv.getStack(index))) return false;
			}else if(o instanceof Item) {
				if(!((Item) o).equals(inv.getStack(index).getItem())) return false;
			}else if(o instanceof Block) {
				if(!((Block) o).asItem().equals(inv.getStack(index).getItem())) return false;
			}else {
				throw new Exception();
			}
			index++;
		}
		while(index <= inv.size() - 2) {
			if(!inv.getStack(index).isEmpty())return false;
			index++;
		}
		return true;
	}
	
	
	@Override
	public void consume(Inventory inv) {
		int index = 0;
		for(Object o: ingredients) {
			if(o instanceof Tag) {
				inv.removeStack(index, 1);
			}else if(o instanceof Set) {
				inv.removeStack(index, 1);
			}else if(o instanceof ItemStack) {
				inv.removeStack(index, ((ItemStack)o).getCount());
			}else if(o instanceof Item) {
				inv.removeStack(index, 1);
			}else if(o instanceof Block) {
				inv.removeStack(index, 1);
			}
			index++;
		}
	}
	
	
	@Override
	public void craft(Inventory inv) {
		ItemStack out = getCraftedStackNoNBT();
		ArrayList<ItemStack> items = new ArrayList<>();
		for(int i = 0; i < inv.size() - 1; i++) {
			if(!inv.getStack(i).isEmpty())items.add(inv.getStack(i));
		}
		NafisTool.setComponents(out, items);
		inv.setStack(inv.size() - 1, out);
	}
	
	public abstract ItemStack getCraftedStackNoNBT();
	

	
}

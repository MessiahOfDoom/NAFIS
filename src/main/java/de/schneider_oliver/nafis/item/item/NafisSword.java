package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemStack;

public class NafisSword extends NafisTool{

	public NafisSword(Settings settings) {
		super(settings);
	}

	@Override
	public int damageAfterMine(ItemStack stack) {
		return 2;
	}

	@Override
	public int damageAfterHit(ItemStack stack) {
		return 1;
	}

}

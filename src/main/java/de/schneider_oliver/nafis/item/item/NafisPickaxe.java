package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemStack;

public class NafisPickaxe extends NafisTool {

	public NafisPickaxe(Settings settings) {
		super(settings);
	}

	@Override
	public int damageAfterMine(ItemStack stack) {
		return 1;
	}

	@Override
	public int damageAfterHit(ItemStack stack) {
		return 2;
	}

}

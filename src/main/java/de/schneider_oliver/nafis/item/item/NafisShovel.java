package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class NafisShovel extends NafisTool {

	public NafisShovel(Settings settings) {
		super(settings);
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		return Items.DIAMOND_SHOVEL.useOnBlock(context);
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
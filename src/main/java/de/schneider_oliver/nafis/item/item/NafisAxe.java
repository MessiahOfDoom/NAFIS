package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class NafisAxe extends NafisTool {

	public NafisAxe(Settings settings) {
		super(settings);
	}
	
	public ActionResult useOnBlock(ItemUsageContext context) {
		return Items.DIAMOND_AXE.useOnBlock(context);
	}

	@Override
	public int damageAfterMine(ItemStack stack) {
		return 1;
	}

	@Override
	public int damageAfterHit(ItemStack stack) {
		return 1;
	}

}

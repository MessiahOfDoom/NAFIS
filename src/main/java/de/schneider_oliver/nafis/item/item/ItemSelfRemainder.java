package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.Item;

public interface ItemSelfRemainder {

	public default Item getActualRemainder() {
		return (Item) this;
	}
	
}

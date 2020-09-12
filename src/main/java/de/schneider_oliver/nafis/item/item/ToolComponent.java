package de.schneider_oliver.nafis.item.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public interface ToolComponent extends ItemConvertible{

	public CompoundTag writeStatsToTag(CompoundTag tagIn);
	
	public CompoundTag writeOtherToTag(CompoundTag tagIn);
	
	public String translationKeyMaterial();
	
	public String translationKeyPart();
	
	public default Text getName() {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
	
	public default Text getName(ItemStack stack) {
		return new TranslatableText(translationKeyPart(), new TranslatableText(translationKeyMaterial()));
	}
	
}

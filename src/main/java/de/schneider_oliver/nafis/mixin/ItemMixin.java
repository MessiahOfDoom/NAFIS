package de.schneider_oliver.nafis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.nafis.item.item.ItemSelfRemainder;
import net.minecraft.item.Item;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(at = @At("HEAD"), method = "getRecipeRemainder()Lnet/minecraft/item/Item;", cancellable = true)
	public void getRecipeRemainder(CallbackInfoReturnable<Item> info) {
		if(this instanceof ItemSelfRemainder) {
			info.setReturnValue(((ItemSelfRemainder)(Object)this).getActualRemainder());
		}
	}
	
	@Inject(at = @At("HEAD"), method = "hasRecipeRemainder()Z", cancellable = true)
	public void hasRecipeRemainder(CallbackInfoReturnable<Boolean> info) {
		if(this instanceof ItemSelfRemainder) info.setReturnValue(true);
	}

}

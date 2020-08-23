package de.schneider_oliver.nafis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	public abstract Item getItem();
	@Shadow
	private boolean empty;
	@Shadow
	public abstract CompoundTag getTag();

	@Inject(at = @At("HEAD"), method = "getMaxDamage()I", cancellable = true)
	public void getMaxDamage(CallbackInfoReturnable<Integer> info) {
		if(getItem() instanceof NafisTool) {
			info.setReturnValue(((NafisTool)getItem()).getMaxDamage((ItemStack) (Object) this));
		}
	}

	@Inject(at = @At("HEAD"), method = "isDamageable()Z", cancellable = true)
	public void isDamageable(CallbackInfoReturnable<Boolean> info) {
		if (!this.empty && getItem() instanceof NafisTool && ((NafisTool)getItem()).getMaxDamage((ItemStack) (Object) this) > 0) {
			CompoundTag compoundTag = getTag();
			info.setReturnValue(compoundTag == null || !compoundTag.getBoolean("Unbreakable"));
		}
	}

}

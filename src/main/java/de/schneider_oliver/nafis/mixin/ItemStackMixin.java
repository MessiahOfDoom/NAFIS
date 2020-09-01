/*******************************************************************************
 * Copyright (c) 2020 Oliver Schneider
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU General Public License version 3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package de.schneider_oliver.nafis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

import de.schneider_oliver.nafis.item.item.NafisTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
	
	@Inject(at = @At("RETURN"), method = "getMiningSpeedMultiplier(Lnet/minecraft/block/BlockState;)F", cancellable = true)
	public void getMiningSpeedMultiplier(BlockState state, CallbackInfoReturnable<Float> info) {
		if(getItem() instanceof NafisTool && NafisTool.isBroken((ItemStack) (Object) this))info.setReturnValue(0.0F);
	}
	
	
	@ModifyVariable(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;"), method = "getAttributeModifiers")
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> multimap, EquipmentSlot slot) {
		if (getItem() instanceof NafisTool) {
			NafisTool tool = (NafisTool) getItem();
			multimap = tool.getModifiers(slot, (ItemStack) (Object) this);
		}
		return multimap;
	}

}

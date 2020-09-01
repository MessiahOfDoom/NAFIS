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
package de.schneider_oliver.nafis.block.entity;

import de.schneider_oliver.nafis.recipe.toolforge.ForgeRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public abstract class AbstractForgeBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory{

	public final int INV_SIZE;
	protected ForgeRecipe recipe;

	DefaultedList<ItemStack> stacks;

	public AbstractForgeBlockEntity(BlockEntityType<?> type, int size) {
		super(type);
		INV_SIZE = size;
		stacks = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);
	}


	@Override
	public void clear() {
		stacks = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);
	}

	@Override
	public int size() {
		return INV_SIZE;
	}

	@Override
	public boolean isEmpty() {
		return stacks.stream().allMatch(a -> a == ItemStack.EMPTY);
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack result = Inventories.splitStack(stacks, slot, amount);
		if (!result.isEmpty()) {
			checkConsume(slot);
			onInventoryChanged(slot);
		}
		return result;
	}

	@Override
	public ItemStack removeStack(int slot) {
		ItemStack out = Inventories.removeStack(stacks, slot);
		checkConsume(slot);
		onInventoryChanged(slot);
		return out;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		stacks.set(slot, stack);
		if(stack.equals(ItemStack.EMPTY) && slot == INV_SIZE - 1) {
			checkConsume(slot);
		}
		if (stack.getCount() > getMaxCountPerStack()) {
			stack.setCount(getMaxCountPerStack());
		}
		onInventoryChanged(slot);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}


	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, stacks);
		onInventoryChanged(-1);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		ItemStack stack = stacks.set(INV_SIZE - 1, ItemStack.EMPTY);
		Inventories.toTag(tag, stacks);
		stacks.set(INV_SIZE - 1, stack);
		return super.toTag(tag);
	}


	public abstract ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player);


	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}


	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {}

	public void onInventoryChanged(int slot) {
		if(slot != INV_SIZE - 1) {
			recipe = null;
			stacks.set(INV_SIZE - 1, ItemStack.EMPTY);
			checkCrafting();
		}
		markDirty();
		
	}

	public void checkConsume(int slot) {
		if(slot == INV_SIZE - 1 && recipe != null) {
			recipe.consume(this);
			recipe = null;
			checkCrafting();
		}
		markDirty();
	}

	abstract void checkCrafting();
}

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
package de.schneider_oliver.nafis.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public abstract class AbstractForgeScreenHandler extends ScreenHandler{

	protected final Inventory inventory;
	
	public AbstractForgeScreenHandler(ScreenHandlerType<? extends AbstractForgeScreenHandler> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
        init(syncId, playerInventory, inventory);
    }
	
	abstract void init(int syncId, PlayerInventory playerInventory, Inventory inventory);
	
	@Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
	
	@Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < inventory.size()) {
                if (!this.insertItem(originalStack, inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
 
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
 
        return newStack;
    }

}

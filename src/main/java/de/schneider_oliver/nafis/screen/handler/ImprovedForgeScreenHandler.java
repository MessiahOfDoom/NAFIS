package de.schneider_oliver.nafis.screen.handler;

import de.schneider_oliver.nafis.block.entity.AbstractForgeBlockEntity;
import de.schneider_oliver.nafis.block.entity.ImprovedForgeBlockEntity;
import de.schneider_oliver.nafis.screen.OutputSlot;
import de.schneider_oliver.nafis.screen.ScreenHandlingRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class ImprovedForgeScreenHandler extends AbstractForgeScreenHandler{

	public ImprovedForgeScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new ImprovedForgeBlockEntity());
	}

	public ImprovedForgeScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, new ImprovedForgeBlockEntity());
	}

	public ImprovedForgeScreenHandler(int syncId, PlayerInventory playerInventory, AbstractForgeBlockEntity forgeBlockEntity) {
		super(ScreenHandlingRegistry.IMPROVED_FORGE_SCREEN_HANDLER, syncId, playerInventory, forgeBlockEntity);
	}

	@Override
	void init(int syncId, PlayerInventory playerInventory, Inventory inventory) {

		this.addSlot(new Slot(inventory, 0, 44, 15));
		this.addSlot(new Slot(inventory, 1, 44, 52));
		this.addSlot(new Slot(inventory, 2, 23, 44));
		this.addSlot(new Slot(inventory, 3, 23, 23));
		this.addSlot(new OutputSlot(inventory, 4, 103, 33));


		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

}

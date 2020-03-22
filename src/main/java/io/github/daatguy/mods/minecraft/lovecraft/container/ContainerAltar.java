package io.github.daatguy.mods.minecraft.lovecraft.container;

import io.github.daatguy.mods.minecraft.lovecraft.tileentity.TileEntityAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAltar extends Container {

	InventoryPlayer inventoryPlayer;
	TileEntityAltar tileEntity;
	
	public ContainerAltar(InventoryPlayer inventoryPlayer,
			TileEntityAltar tileEntity) {
		
		this.tileEntity = tileEntity;

		if (tileEntity.hasCapability(
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {

			IItemHandler inventory = tileEntity.getCapability(
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			// Book
			addSlotToContainer(new SlotBook(inventory, 0, 16, 125));
			// Circle
			addSlotToContainer(new SlotAltar(inventory, 1, 80, 20, this.tileEntity, 0));
			addSlotToContainer(new SlotAltar(inventory, 2, 106, 30, this.tileEntity, 1));
			addSlotToContainer(new SlotAltar(inventory, 3, 116, 56, this.tileEntity, 2));
			addSlotToContainer(new SlotAltar(inventory, 4, 106, 82, this.tileEntity, 3));
			addSlotToContainer(new SlotAltar(inventory, 5, 80, 92, this.tileEntity, 4));
			addSlotToContainer(new SlotAltar(inventory, 6, 54, 82, this.tileEntity, 5));
			addSlotToContainer(new SlotAltar(inventory, 7, 44, 56, this.tileEntity, 6));
			addSlotToContainer(new SlotAltar(inventory, 8, 54, 30, this.tileEntity, 7));
			addSlotToContainer(new SlotAltar(inventory, 9, 80, 56, this.tileEntity, 8));

			// Main player inventory
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 9; x++) {
					addSlotToContainer(new Slot(inventoryPlayer, x + (y * 9)
							+ 9, 8 + x * 18, 161 + 18 * y));
				}
			}

			// Player hotbar inventory
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 219));
			}

		}

	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();

			int containerSlots = inventorySlots.size()
					- playerIn.inventory.mainInventory.size();

			if (index < containerSlots) {

				if (!this.mergeItemStack(stackInSlot, containerSlots,
						inventorySlots.size(), true)) {

					return ItemStack.EMPTY;
				}

			} else if (!this.mergeItemStack(stackInSlot, 0, containerSlots,
					false)) {

				return ItemStack.EMPTY;

			}

			if (stackInSlot.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			slot.onTake(playerIn, stackInSlot);

		}
		return stack;
	}

}

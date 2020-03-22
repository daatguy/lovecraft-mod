package io.github.daatguy.mods.minecraft.lovecraft.container;

import java.util.ArrayList;

import io.github.daatguy.mods.minecraft.lovecraft.book.DeskRecipe;
import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerDesk extends Container {

	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 1);
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	private final World world;
	/** Position of the workbench */
	private final BlockPos pos;
	private final EntityPlayer player;
	public DeskRecipe recipe;
	
	public ContainerDesk(InventoryPlayer playerInventory, BlockPos pos) {

		this.world = playerInventory.player.world;
		this.pos = pos;
		this.player = playerInventory.player;
		this.recipe = null;

		playerInventory.markDirty();
		
		// Book
		int index = 0;
		this.addSlotToContainer(new SlotDeskCrafting(playerInventory.player,
				this.craftMatrix, this.craftResult, 0, 116, 35, this));
		this.addSlotToContainer(new Slot(this.craftMatrix, index++, 48, 25));
		this.addSlotToContainer(new Slot(this.craftMatrix, index++, 39, 43));
		this.addSlotToContainer(new Slot(this.craftMatrix, index++, 57, 43));
		// addSlotToContainer(outputSlot);

		// Main player inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(playerInventory, x + (y * 9) + 9,
						8 + x * 18, 84 + 18 * y));
			}
		}

		// Player hotbar inventory
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
		}

	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		if (!this.world.isRemote) {
			this.clearContainer(playerIn, this.world, this.craftMatrix);
		}
	}

	@Override
	protected void slotChangedCraftingGrid(World worldIn, EntityPlayer player,
			InventoryCrafting invCrafting, InventoryCraftResult invCraftResult) {

		if (!worldIn.isRemote) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) player;
			ItemStack itemstack = ItemStack.EMPTY;
			ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

			for (int i = 0; i < 3; i++) {
				stacks.add(invCrafting.getStackInSlot(i));
			}

			for (DeskRecipe recipe : LovecraftMain.deskHandler.deskRecipes) {
				if (recipe.canCraft(stacks)) {
					this.recipe = recipe;
					if (invCraftResult.getStackInSlot(0).isEmpty()) {
						invCraftResult.setInventorySlotContents(0,
								recipe.output.copy());
						entityplayermp.connection
								.sendPacket(new SPacketSetSlot(this.windowId,
										0, recipe.output.copy()));
					}
					
					break;
				} else if (!invCraftResult.getStackInSlot(0).isEmpty()) {
					this.recipe = null;
					invCraftResult.setInventorySlotContents(0, ItemStack.EMPTY);
					entityplayermp.connection.sendPacket(new SPacketSetSlot(
							this.windowId, 0, ItemStack.EMPTY));
				}
			}
		}
	}

	public void onCraftMatrixChanged(IInventory inventoryIn) {
		this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix,
				this.craftResult);
	}
	
	public void removeCraftingIngredients(EntityPlayerMP entityplayermp) {
		if(this.recipe==null) return;
		for (int i = 0; i < 3; i++) {
			ItemStack cItem = this.craftMatrix.getStackInSlot(i);
			if (!cItem.isEmpty()) {
				boolean cont = false;
				for (ItemStack rItem : this.recipe.remains) {
					if (ItemStack.areItemsEqual(rItem, cItem)
							&& (ItemStack.areItemStackTagsEqual(rItem,
									cItem) || !rItem.getItem()
									.getHasSubtypes())) {
						cont = true;
					}
				}
				if (cont) {
					continue;
				} else {
					ItemStack rStack = this.craftMatrix.getStackInSlot(i);
					int count = rStack.getCount();
					//ItemStack rStack = this.craftMatrix.decrStackSize(i, 1);
					rStack.setCount(count-1);
					entityplayermp.connection.sendPacket(new SPacketSetSlot(
							this.windowId, i+1, rStack));
					this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix,
							this.craftResult);
				}
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
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0) {
				itemstack1.getItem()
						.onCreated(itemstack1, this.world, playerIn);

				if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= 4 && index < 31) {
				if (!this.mergeItemStack(itemstack1, 1, 4, false)) {
					return ItemStack.EMPTY;

				} else if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 31 && index < 40) {
				if (!this.mergeItemStack(itemstack1, 1, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

			if (index == 0) {
				playerIn.dropItem(itemstack2, false);
			}
		}

		return itemstack;
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging
	 * (double-click) code. The stack passed in is null for the initial slot
	 * that was double-clicked.
	 */
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.craftResult
				&& super.canMergeSlot(stack, slotIn);
	}

}

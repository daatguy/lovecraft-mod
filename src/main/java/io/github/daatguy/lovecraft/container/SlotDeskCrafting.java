package io.github.daatguy.lovecraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotDeskCrafting extends SlotCrafting {

	/** The craft matrix inventory linked to this result slot. */
	private final InventoryCrafting craftMatrix;
	private final ContainerDesk container;

	public SlotDeskCrafting(EntityPlayer player,
			InventoryCrafting craftingInventory, IInventory inventoryIn,
			int slotIndex, int xPosition, int yPosition, ContainerDesk container) {
		super(player, craftingInventory, inventoryIn, slotIndex, xPosition,
				yPosition);
		this.craftMatrix = craftingInventory;
		this.container = container;
	}

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		super.onCrafting(stack);
		stack.onCrafting(thePlayer.getEntityWorld(), thePlayer,
				stack.getCount());
		if(thePlayer instanceof EntityPlayerMP) this.container.removeCraftingIngredients((EntityPlayerMP)thePlayer);
		
		/*for (int i = 0; i < 3; i++) {
			ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
			System.out.println(this.container.recipe);
			if (!itemstack.isEmpty() && this.container.recipe != null) {
				boolean cont = false;
				for (ItemStack rItem : this.container.recipe.remains) {
					if (ItemStack.areItemsEqual(rItem, itemstack)
							&& (ItemStack.areItemStackTagsEqual(rItem,
									itemstack) || !rItem.getItem()
									.getHasSubtypes())) {
						cont = true;
					}
				}
				if (cont) {
					continue;
				} else {
					this.craftMatrix.decrStackSize(i, 1);
				}
			}
		}*/
		return stack;
	}
}

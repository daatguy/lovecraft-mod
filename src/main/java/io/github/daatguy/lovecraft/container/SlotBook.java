package io.github.daatguy.lovecraft.container;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBook extends SlotItemHandler{

	public SlotBook(IItemHandler itemHandler, int index, int xPosition,
			int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem()==LovecraftMain.itemTome) {
			return true;
		}
		return false;
	}
	
}

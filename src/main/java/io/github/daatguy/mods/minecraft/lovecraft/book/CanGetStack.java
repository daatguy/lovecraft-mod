package io.github.daatguy.mods.minecraft.lovecraft.book;

import net.minecraft.item.ItemStack;

public interface CanGetStack {

	public default ItemStack getItemStack() {
		return ItemStack.EMPTY;
	}
	
	/**
	 * Returns an item stack of size 1
	 */
	public default ItemStack getItemStack(int size) {
		ItemStack item = getItemStack();
		item.setCount(size);
		return item;
	}
}

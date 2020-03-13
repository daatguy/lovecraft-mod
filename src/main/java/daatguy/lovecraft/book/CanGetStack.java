package daatguy.lovecraft.book;

import net.minecraft.item.ItemStack;

public interface CanGetStack {

	public default ItemStack getItemStack() {
		return ItemStack.EMPTY;
	}
	
	public default ItemStack getItemStack(int size) {
		ItemStack item = getItemStack();
		item.setCount(size);
		return item;
	}
}

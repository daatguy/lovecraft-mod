package daatguy.lovecraft.book;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.SubItemsHandler;

public class Book implements CanGetStack {

	public String name;
	public int language;
	public int color;
	public int genWeight;
	
	public Book() {
		this.name = "empty";
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = 0;
	}
	
	/**
	 * Name: a string
	 */
	public Book(String name) {
		this.name = name;
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = 0;
	}

	/**
	 * Name: a string
	 * Color: 0-19, corresponds to tome sprites
	 */
	public Book(String name, int color) {
		this.name = name;
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = color;
	}
	
	/**
	 * Name: a string
	 * Color: 0-19, corresponds to tome sprites
	 * GenWeight: weight for generation (might be depreciated)
	 */
	public Book(String name, int color, int genWeight) {
		this.name = name;
		this.genWeight = genWeight;
		this.language = SubItemsHandler.COMMON;
		this.color = color;
	}
	
	/**
	 * Name: a string
	 * Color: 0-19, corresponds to tome sprites
	 * GenWeight: weight for generation (might be depreciated)
	 * Language: int id for language (see {@link SubItemsHandler#getLanguageFromID(int)}, etc.)
	 */
	public Book(String name, int color, int genWeight, int language) {
		this.name = name;
		this.genWeight = genWeight;
		this.language = language;
		this.color = color;
	}
	
	/**
	 * Returns an item stack of size 1 of this book
	 */
	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(LovecraftMain.itemBook, 1, this.color);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Book", this.name);
		if(this.language!=SubItemsHandler.COMMON) nbt.setBoolean("Translated", false);
		item.setTagCompound(nbt);
		return item;
	}

}

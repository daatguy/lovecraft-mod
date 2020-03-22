package io.github.daatguy.mods.minecraft.lovecraft.book;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import io.github.daatguy.mods.minecraft.lovecraft.item.SubItemsHandler;

public class DictionaryBook extends Book {

	public DictionaryBook() {
		this.name = "empty";
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = 0;
	}
	
	/**
	 * Name: a string
	 */
	public DictionaryBook(String name) {
		super(name);
	}

	/**
	 * Name: a string
	 * Color: 0-15, corresponds to tome sprites
	 */
	public DictionaryBook(String name, int color) {
		super(name,color);
	}
	
	/**
	 * Name: a string
	 * Color: 0-15, corresponds to tome sprites
	 * GenWeight: weight for generation (might be depreciated)
	 */
	public DictionaryBook(String name, int color, int genWeight) {
		super(name,color,genWeight);
	}

}

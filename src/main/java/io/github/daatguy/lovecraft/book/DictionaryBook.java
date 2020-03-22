package io.github.daatguy.lovecraft.book;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import io.github.daatguy.lovecraft.item.SubItemsHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

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
	
	public Book(String name) {
		this.name = name;
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = 0;
	}
	
	public Book(String name, int color) {
		this.name = name;
		this.genWeight = 0;
		this.language = SubItemsHandler.COMMON;
		this.color = color;
	}
	
	public Book(String name, int color, int genWeight) {
		this.name = name;
		this.genWeight = genWeight;
		this.language = SubItemsHandler.COMMON;
		this.color = color;
	}
	
	public Book(String name, int color, int genWeight, int language) {
		this.name = name;
		this.genWeight = genWeight;
		this.language = language;
		this.color = color;
	}
	
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

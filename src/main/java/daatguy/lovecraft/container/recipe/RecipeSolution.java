package daatguy.lovecraft.container.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.ItemBeaker;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeSolution extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe{
	
	
	//Copied from stackoverflow
	//Hope it works...
	protected class ItemStackSorter implements Comparator<ItemStack> 
	{
	    @Override
	    public int compare(ItemStack o1, ItemStack o2) {
	    	return o1.getItem().getRegistryName().toString().compareTo((o2.getItem().getRegistryName().toString()));
	    }
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		//If there's nothing we return false
		if(inv.isEmpty()) return false;
		ArrayList<ItemStack> items = new ArrayList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(!inv.getStackInSlot(i).isEmpty()) {
				items.add(inv.getStackInSlot(i));
			}
		}
		
		boolean beakerValid = false;
		//For each item in the crafting grid
		if(items.size()<2) return false;
		for(ItemStack item : items) {
			boolean cont = false;
			//We check if it's a solution item, if it is,
			for(Item dItem : LovecraftMain.alchemyRecipes.SOLUTION_ITEMS) {
				if(item.getItem()==dItem) {
					cont = true;
				}
			}
			//We move on,
			if(cont) {
				continue;
			}
			//If it's a beaker, we do the same
			if(item.getItem()==LovecraftMain.itemBeaker && !beakerValid) {
				float totalPercent = 0.0f;
				if(item.hasTagCompound() && item.getTagCompound().hasKey("BeakerContents")) {
					NBTTagList listnbt = ((NBTTagList)(item.getTagCompound().getTag("BeakerContents")));
					for(int i = 0; i<listnbt.tagCount(); i++) {
						totalPercent += ((NBTTagCompound)listnbt.get(i)).getFloat("Percent");
					}
					totalPercent+=items.size()-1.0f;
					if(totalPercent>100.0f) return false;
				}
				beakerValid = true;
				continue;
			}
			//Otherwise, it doesn't belong and the recipe doesn't match
			return false;
		}
		//If it passes all these checks, we have a valid recipe.
		return beakerValid;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ArrayList<ItemStack> dItems = getDissolvableItems(inv);
		if(dItems.isEmpty()) {
			return null;
		} else {
			dItems.sort(new ItemStackSorter());
			ItemStack beaker = new ItemStack(LovecraftMain.itemBeaker);
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				if(inv.getStackInSlot(i).getItem()==LovecraftMain.itemBeaker) {
					beaker = inv.getStackInSlot(i).copy();
					beaker.setCount(1);
				}
			}
			for(ItemStack dItem : dItems) {
				ItemBeaker.addSuspension(beaker, dItem.getItem(), 1.0f);
			}
			return beaker;
		}
	}
	
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    public boolean isDynamic()
    {
        return true;
    }

	public static ArrayList<ItemStack> getDissolvableItems(InventoryCrafting inv) {
		ArrayList<ItemStack> items = new ArrayList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			for(Item dItem : LovecraftMain.alchemyRecipes.SOLUTION_ITEMS) {
				if(!inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem()==dItem) {
					items.add(inv.getStackInSlot(i));
					break;
				}
			}
		}
		return items;
	}
	
    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height)
    {
        return width >= 2 && height >= 2;
    }

	@Override
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

}

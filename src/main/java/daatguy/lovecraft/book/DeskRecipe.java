package daatguy.lovecraft.book;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DeskRecipe {

	public ItemStack[] recipe = new ItemStack[3];
	public ArrayList<ItemStack> remains = new ArrayList<ItemStack>();
	public ItemStack output;
	public static Item[] noteItems = new Item[3];
	
	public DeskRecipe() {
		InitNoteItems();
		this.recipe[0] = ItemStack.EMPTY;
		this.recipe[1] = ItemStack.EMPTY;
		this.recipe[2] = ItemStack.EMPTY;
		this.output = ItemStack.EMPTY;
	}

	public DeskRecipe(ItemStack[] recipe, ItemStack output) {
		InitNoteItems();
		this.recipe = recipe;
		this.output = output;
		//System.out.println(recipe);
	}
	
	public DeskRecipe(ItemStack[] recipe, ItemStack output, boolean[] remains) {
		InitNoteItems();
		this.recipe = recipe;
		for(int i = 0; i<recipe.length; i++) {
			if(remains[i]) {
				this.remains.add(recipe[i]);
			}
		}
		this.output = output;
		//System.out.println(recipe);
	}

	private void InitNoteItems() {
		this.noteItems[0] = Items.PAPER;
		this.noteItems[1] = Items.WRITABLE_BOOK;
		this.noteItems[2] = Items.STRING;
	}
	
	protected ArrayList<ItemStack> getRecipeInList() {
		ArrayList<ItemStack> rItems = new ArrayList<ItemStack>();
		for (int i = 0; i < this.recipe.length; i++) {
			rItems.add(this.recipe[i]);
		}
		return rItems;
	}

	public boolean isNotes() {
		for (Item nItem : this.noteItems) {
			if(isNotes(nItem)) return true;
		}
		return false;
	}
	
	public boolean isNoteItem(Item item) {
		for(Item nItem : this.noteItems) {
			if(nItem.equals(item)) return true;
		}
		return false;
	}
	
	public boolean isNotes(Item nItem) {
		ArrayList<ItemStack> rItems = this.getRecipeInList();
		for (ItemStack rItem : rItems) {
			if (rItem.getItem() == nItem)
				return true;
		}
		return false;
	}

	public Item getNeededNoteItem(ArrayList<ItemStack> inputItems) {
		if(canCraft(inputItems)) return null;
		for(Item nItem : this.noteItems) {
			ArrayList<ItemStack> cItems = (ArrayList<ItemStack>) inputItems.clone();
			ArrayList<ItemStack> rItems = this.getRecipeInList();
			//Here we modify the recipe's items so they don't contain 

			//System.out.println(nItem.getUnlocalizedName());
			for(int i = 0; i<rItems.size(); i++) {
				//System.out.println(nItem.getUnlocalizedName());
				if(rItems.get(i).getItem()==nItem) {
					rItems.set(i, ItemStack.EMPTY);
				}
			}
			//System.out.println(rItems);
			if(canCraft(cItems, rItems)) return nItem;
		}
		return null;
	}


	public boolean canCraft(ArrayList<ItemStack> inputItems) {
		ArrayList<ItemStack> rItems = this.getRecipeInList();
		return canCraft(inputItems,rItems);
	}
	
	public boolean canCraft(ArrayList<ItemStack> inputItems, ArrayList<ItemStack> rItems) {
		ArrayList<ItemStack> cItems = (ArrayList<ItemStack>) inputItems.clone();

		// System.out.println(rItems.toString());
		for (ItemStack rItem : rItems) {
			removeIfContained(cItems, rItem);
		}
		return (cItems.size() == 0);
	}

	private boolean removeIfContained(ArrayList<ItemStack> items,
			ItemStack rItem) {
		// ArrayList<ItemStack> items = (ArrayList<ItemStack>) iitems.clone();
		int size = items.size();
		for (ItemStack cItem : items) {
			if (rItem.isEmpty() && cItem.isEmpty()) {
				items.remove(cItem);
				return true;
			} else if (ItemStack.areItemsEqual(rItem, cItem)
					&& (ItemStack.areItemStackTagsEqual(rItem, cItem) || !rItem
							.getItem().getHasSubtypes())) {
				items.remove(cItem);
				return true;
			}
		}
		return false;
	}
}

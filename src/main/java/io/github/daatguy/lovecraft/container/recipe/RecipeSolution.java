package io.github.daatguy.lovecraft.container.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import io.github.daatguy.lovecraft.item.ItemBeaker;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeSolution extends
		net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe>
		implements IRecipe {

	// Copied from stackoverflow
	// Hope it works...
	protected class ItemStackSorter implements Comparator<ItemStack> {
		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			return o1.getItem().getRegistryName().toString()
					.compareTo((o2.getItem().getRegistryName().toString()));
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		// If there's nothing we return false
		if (inv.isEmpty())
			return false;
		ArrayList<ItemStack> items = new ArrayList();
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (!inv.getStackInSlot(i).isEmpty()) {
				items.add(inv.getStackInSlot(i));
			}
		}

		ArrayList<ItemStack> dItems = getDissolvableItems(inv);
		ArrayList<ItemStack> bItems = getBeakerItems(inv);
		float totalPercent = ((float) dItems.size()) / ((float) bItems.size());
		for (ItemStack bItem : bItems) {
			if (bItem.hasTagCompound()
					&& bItem.getTagCompound().hasKey("BeakerContents")) {
				for (NBTBase subnbt : (NBTTagList) bItem.getTagCompound()
						.getTag("BeakerContents")) {
					totalPercent += ((NBTTagCompound) subnbt)
							.getFloat("Percent") / ((float) bItems.size());
				}
			}
		}

		System.out.println(dItems.size());
		System.out.println(bItems.size());
		return ((dItems.size() + bItems.size()) == items.size()
				&& bItems.size() > 0 && items.size() > 1 && totalPercent <= 100.0f);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ArrayList<ItemStack> dItems = getDissolvableItems(inv);
		ArrayList<ItemStack> bItems = getBeakerItems(inv);
		dItems.sort(new ItemStackSorter());
		ItemStack beaker = new ItemStack(LovecraftMain.itemBeaker,
				bItems.size());
		for (ItemStack bItem : bItems) {
			if (bItem.hasTagCompound()
					&& bItem.getTagCompound().hasKey("BeakerContents")) {
				for (NBTBase subnbt : (NBTTagList) bItem.getTagCompound()
						.getTag("BeakerContents")) {
					ItemBeaker.addSuspension(beaker, Item
							.getByNameOrId(((NBTTagCompound) subnbt)
									.getString("Item")),
							((NBTTagCompound) subnbt).getFloat("Percent")
									/ ((float) bItems.size()));
				}
			}
		}
		for (ItemStack dItem : dItems) {
			ItemBeaker.addSuspension(beaker, dItem.getItem(),
					1.0f / ((float) bItems.size()));
		}
		return beaker;
	}

	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.<ItemStack> withSize(inv.getSizeInventory(),
				ItemStack.EMPTY);
	}

	public boolean isDynamic() {
		return true;
	}

	public static ArrayList<ItemStack> getDissolvableItems(InventoryCrafting inv) {
		ArrayList<ItemStack> items = new ArrayList();
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			for (Item dItem : LovecraftMain.alchemyRecipes.SOLUTION_ITEMS) {
				if (!inv.getStackInSlot(i).isEmpty()
						&& inv.getStackInSlot(i).getItem() == dItem) {
					items.add(inv.getStackInSlot(i));
					break;
				}
			}
		}
		return items;
	}

	public static ArrayList<ItemStack> getBeakerItems(InventoryCrafting inv) {
		ArrayList<ItemStack> items = new ArrayList();
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (!inv.getStackInSlot(i).isEmpty()
					&& inv.getStackInSlot(i).getItem() == LovecraftMain.itemBeaker) {
				items.add(inv.getStackInSlot(i));
			}
		}
		return items;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given
	 * width/height
	 */
	public boolean canFit(int width, int height) {
		return width >= 2 && height >= 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

}

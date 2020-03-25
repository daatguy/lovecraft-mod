package daatguy.lovecraft.book;

import java.util.ArrayList;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.SubItemsHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DeskRubbingTranslationRecipe extends DeskRecipe {

	public int language;

	public DeskRubbingTranslationRecipe(int language) {
		super();
		this.language = language;
	}

	@Override
	public ItemStack getNeededNoteItem(ArrayList<ItemStack> inputItems) {
		if (canCraft(inputItems))
			return null;
		ArrayList<ItemStack> iItems = (ArrayList<ItemStack>) inputItems.clone();
		for (ItemStack item : iItems) {
			if (item.getItem() == LovecraftMain.itemRubbing
					&& item.hasTagCompound()
					&& item.getTagCompound().hasKey("Carving")
					&& item.getTagCompound().hasKey("Language")
					&& item.getTagCompound().getInteger("Language") == this.language
					&& item.getTagCompound().hasKey("Translated")
					&& !item.getTagCompound().getBoolean("Translated")) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Book", DeskHandler.getDictName(this.language));
				ItemStack dictItem = new ItemStack(LovecraftMain.itemBook, 1,
						SubItemsHandler.books.get(DeskHandler
								.getDictName(this.language)).color);
				dictItem.setTagCompound(nbt);
				return dictItem;
			}
		}
		return null;
	}

	@Override
	public ItemStack getOutput(ArrayList<ItemStack> stacks) {
		ItemStack rubbing = null;
		for (ItemStack item : stacks) {
			if (item.getItem() == LovecraftMain.itemRubbing
					&& item.hasTagCompound()
					&& item.getTagCompound().hasKey("Carving")
					&& item.getTagCompound().hasKey("Language")
					&& item.getTagCompound().getInteger("Language") == this.language
					&& item.getTagCompound().hasKey("Translated")
					&& !item.getTagCompound().getBoolean("Translated")) {
				rubbing = item.copy();
				break;
			}
		}
		rubbing.getTagCompound().setBoolean("Translated", true);
		rubbing.setCount(1);
		return rubbing;
	}

	@Override
	public ArrayList<ItemStack> getRemains() {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Book", DeskHandler.getDictName(this.language));
		ItemStack item = new ItemStack(LovecraftMain.itemBook, 1,
				SubItemsHandler.books.get(DeskHandler
						.getDictName(this.language)).color);
		item.setTagCompound(nbt);
		items.add(item);
		return items;
	}

	/**
	 * Returns TRUE if inputItems matches the recipe in full
	 */
	@Override
	public boolean canCraft(ArrayList<ItemStack> inputItems) {
		ArrayList<ItemStack> iItems = (ArrayList<ItemStack>) inputItems.clone();
		ItemStack rubbing = null;
		for (ItemStack item : iItems) {
			if (item.getItem() == LovecraftMain.itemRubbing
					&& item.hasTagCompound()
					&& item.getTagCompound().hasKey("Carving")
					&& item.getTagCompound().hasKey("Language")
					&& item.getTagCompound().getInteger("Language") == this.language
					&& item.getTagCompound().hasKey("Translated")
					&& !item.getTagCompound().getBoolean("Translated")) {
				rubbing = item.copy();
				iItems.remove(item);
				break;
			}
		}
		boolean dict = false;
		ItemStack dictStack = this.getRemains().get(0);
		for (ItemStack item : iItems) {
			if (ItemStack.areItemsEqual(item, dictStack)
					&& ItemStack.areItemStackTagsEqual(item, dictStack)) {
				dict = true;
				iItems.remove(item);
				break;
			}
		}
		boolean empty = true;
		for (ItemStack item : iItems) {
			if (!item.isEmpty()) {
				empty = false;
				break;
			}
		}
		return (rubbing != null && dict && empty);
	}

	/**
	 * Returns TRUE if inputItems matches rItems
	 */
	@Override
	public boolean canCraft(ArrayList<ItemStack> inputItems,
			ArrayList<ItemStack> rItems) {
		return canCraft(inputItems);
	}
}

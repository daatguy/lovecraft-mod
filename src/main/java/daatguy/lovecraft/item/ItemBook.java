package daatguy.lovecraft.item;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.apache.logging.log4j.core.pattern.TextRenderer;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.book.DeskHandler;
import daatguy.lovecraft.book.DictionaryBook;
import daatguy.lovecraft.book.spell.SpellHandler;
import daatguy.lovecraft.core.LovecraftMain;

public class ItemBook extends ItemSimple {

	private static int loreLineThreshhold = 25;

	public ItemBook() {
		this.setHasSubtypes(true);
	}

	public static ItemStack getItemStack(String name, boolean translated) {
		// If this throws a NullPointer, make sure the name matches the book
		// described
		ItemStack newItem = new ItemStack(LovecraftMain.itemBook, 1,
				LovecraftMain.subItemsHandler.books.get(name).color);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Book", name);
		if (LovecraftMain.subItemsHandler.books.get(name).language != SubItemsHandler.COMMON) {
			nbt.setBoolean("Translated", translated);
		}
		newItem.setTagCompound(nbt);
		return newItem;
	}

	public static ItemStack getItemStack(String name) {
		return getItemStack(name, false);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		if (stack.hasTagCompound()) {
			return "book." + stack.getTagCompound().getString("Book");
		} else {
			return "book.empty";
		}

	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		for (int i = 0; i < 20; i++) {
			ModelResourceLocation mrl = new ModelResourceLocation(
					LovecraftMain.itemTome.getRegistryName() + "_"
							+ String.valueOf(i), "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}

	public void addTooltipWithLineThreshhold(ItemStack stack, List<String> tooltip, boolean translated) {
		String rawLore[];
		if(translated) {
			rawLore = I18n.format(
				"book." + stack.getTagCompound().getString("Book")
						+ ".description.translated").split("\\s+");
		} else {
			rawLore = I18n.format(
					"book." + stack.getTagCompound().getString("Book")
							+ ".description").split("\\s+");
		}
		String curLine = "";
		for (String word : rawLore) {
			curLine = curLine + word + " ";
			if (curLine.length() > loreLineThreshhold) {
				tooltip.add(curLine.trim());
				curLine = "";
			}
		}
		if (curLine != "") {
			tooltip.add(curLine.trim());
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		if (I18n.format(
				"book." + stack.getTagCompound().getString("Book")
						+ ".description").equals(
				"book." + stack.getTagCompound().getString("Book")
						+ ".description"))
			return;
		// super.addInformation(stack, worldIn, tooltip, flagIn);
		String author = I18n.format("book."
				+ stack.getTagCompound().getString("Book") + ".author");
		if (!author.equals("book." + stack.getTagCompound().getString("Book")
				+ ".author")) {
			tooltip.add("by " + author);
			tooltip.add("");
		} else {
			// MAYBE PUT "AUTHOR UNKNOWN" OR SOMETHING HERE.
		}
		int language = LovecraftMain.subItemsHandler.books.get(stack
				.getTagCompound().getString("Book")).language;
		if (language != SubItemsHandler.COMMON) {
			if (stack.getTagCompound().getBoolean("Translated")) {
				addTooltipWithLineThreshhold(stack, tooltip, true);
				// Translation stuff
				// IT IS TRANSLATED
				tooltip.add("");
				tooltip.add(TextFormatting.GREEN + I18n.format("book.translated_tooltip").replace(
						"*",
						SubItemsHandler
								.getLanguageFromID(language)));
			} else {
				addTooltipWithLineThreshhold(stack, tooltip, false);
				// Translation stuff
				// IT ISNT
				tooltip.add("");
				tooltip.add(TextFormatting.RED + I18n.format("book.untranslated_tooltip").replace(
						"*",
						SubItemsHandler
								.getLanguageFromID(language)));
			}
		} else {
			addTooltipWithLineThreshhold(stack, tooltip, false);
		}
		if (LovecraftMain.subItemsHandler.books.get(stack.getTagCompound()
				.getString("Book")) instanceof DictionaryBook) {
			tooltip.add("");
			tooltip.add(TextFormatting.GOLD
					+ I18n.format("book.dictionary_tooltip").replace(
							"*l",
							SubItemsHandler
									.getLanguageFromID(DeskHandler
											.getIDFromDict(stack
													.getTagCompound()
													.getString("Book")))));
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.getCreativeTab() == tab) {
			for (String key : LovecraftMain.subItemsHandler.books.keySet()) {
				ItemStack newItem = new ItemStack(this, 1,
						LovecraftMain.subItemsHandler.books.get(key).color);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Book", key);
				if (LovecraftMain.subItemsHandler.books.get(key).language != SubItemsHandler.COMMON) {
					nbt.setBoolean("Translated", false);
					newItem.setTagCompound(nbt);
					items.add(newItem);
					ItemStack newItem2 = new ItemStack(this, 1,
							LovecraftMain.subItemsHandler.books.get(key).color);
					NBTTagCompound nbt2 = new NBTTagCompound();
					nbt2.setString("Book", key);
					nbt2.setBoolean("Translated", true);
					newItem2.setTagCompound(nbt2);
					items.add(newItem2);
				} else {
					newItem.setTagCompound(nbt);
					items.add(newItem);
				}
			}
		}
	}
}

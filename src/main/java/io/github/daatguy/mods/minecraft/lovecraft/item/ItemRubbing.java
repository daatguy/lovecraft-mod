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

public class ItemRubbing extends ItemSimple {

	private static int loreLineThreshhold = 25;

	public ItemRubbing() {
	}

	public static ItemStack getItemStack(String carving, int language,
			boolean translated) {
		ItemStack newItem = new ItemStack(LovecraftMain.itemRubbing);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Carving", carving);
		if (language != SubItemsHandler.COMMON) {
			nbt.setInteger("Language", language);
			nbt.setBoolean("Translated", translated);
		}
		newItem.setTagCompound(nbt);
		return newItem;
	}

	public void addTooltipWithLineThreshhold(ItemStack stack, List<String> tooltip) {
		String carving = "";
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Carving")) {
			carving = stack.getTagCompound().getString("Carving");
		}
		
		String rawLore[] = I18n.format(carving).split("\\s+");
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
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Carving")) return;
		int language = SubItemsHandler.COMMON;
		if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("Language")) {
			language = stack.getTagCompound().getInteger("Language");
		}
		if (language != SubItemsHandler.COMMON) {
			if (stack.getTagCompound().getBoolean("Translated")) {
				addTooltipWithLineThreshhold(stack, tooltip);
				// Translation stuff
				// IT IS TRANSLATED
				tooltip.add("");
				tooltip.add(TextFormatting.GREEN
						+ I18n.format("book.translated_tooltip").replace("*",
								SubItemsHandler.getLanguageFromID(language)));
			} else {
				tooltip.add(TextFormatting.RED
						+ I18n.format("book.untranslated_tooltip").replace("*",
								SubItemsHandler.getLanguageFromID(language)));
			}
		} else {
			addTooltipWithLineThreshhold(stack, tooltip);
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

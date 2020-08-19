package daatguy.lovecraft.book;

import java.util.ArrayList;

import daatguy.lovecraft.book.spell.SpellHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.ItemBook;
import daatguy.lovecraft.item.ItemFossil;
import daatguy.lovecraft.item.SubItemsHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DeskHandler {

	public ArrayList<DeskRecipe> deskRecipes = new ArrayList<DeskRecipe>();

	public DeskHandler() {
	}

	/**
	 * Returns string version (for nbt data) of dictionary by given language ID
	 */
	public static String getDictName(int language) {
		switch(language) {
		case SubItemsHandler.LATIN:
			return "latin_dict";
		case SubItemsHandler.GREEK:
			return "greek_dict";
		case SubItemsHandler.SANSKRIT:
			return "sanskrit_dict";
		case SubItemsHandler.RUNIC:
			return "runic_dict";
		case SubItemsHandler.OLDSPEAK:
			///TODO IMPLEMENT THIS 
			return null;
		default:
			return null;
		}
			
	}
	
	/**
	 * Returns language ID by given string version (for nbt data) of dictionary
	 */
	public static int getIDFromDict(String language) {
		switch(language) {
		case "latin_dict":
			return SubItemsHandler.LATIN;
		case "greek_dict":
			return SubItemsHandler.GREEK;
		case "sanskrit_dict":
			return SubItemsHandler.SANSKRIT;
		case "runic_dict":
			return SubItemsHandler.RUNIC;
		//case SubItemsHandler.OLDSPEAK:
			///TODO IMPLEMENT THIS 
			//return null;
		default:
			return SubItemsHandler.COMMON;
		}
			
	}
	
	//Adds all the translation recipes to deskRecipes
	private void addTranslationRecipes() {
		//For rubbing translation
		deskRecipes.add(new DeskRubbingTranslationRecipe(SubItemsHandler.LATIN));
		deskRecipes.add(new DeskRubbingTranslationRecipe(SubItemsHandler.GREEK));
		deskRecipes.add(new DeskRubbingTranslationRecipe(SubItemsHandler.RUNIC));
		deskRecipes.add(new DeskRubbingTranslationRecipe(SubItemsHandler.SANSKRIT));
		//For book translation
		for(String bookKey : LovecraftMain.subItemsHandler.books.keySet()) {
			Book book = LovecraftMain.subItemsHandler.books.get(bookKey);
			if(book.language!=SubItemsHandler.COMMON) {
				deskRecipes.add(new DeskRecipe(new ItemStack[] {
						book.getItemStack(),
						ItemBook.getItemStack(getDictName(book.language)), ItemStack.EMPTY },
						ItemBook.getItemStack(book.name, true), new boolean[]{false, true, false}));
			}
		}
	}
	
	/**
	 * Initializes the writing desk crafting recipes
	 */
	public void init() {

		
		addTranslationRecipes();
		/*deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("latin_history"),
				ItemBook.getItemStack("latin_dict"), ItemStack.EMPTY },
				ItemBook.getItemStack("latin_history", true), new boolean[]{false, true, false}));*/
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemBlockCarvedObelisk),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("obelisk_carving"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemBlockBasRelief),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("bas_relief"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemFossil.getItemStack(0),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_plates"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemFossil.getItemStack(1),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_spines"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemFossil.getItemStack(2),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_tendril"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemFossil.getItemStack(3),
				new ItemStack(Items.PAPER), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_cavity"), new boolean[]{true, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("fossil_notes_spines"),
				ItemBook.getItemStack("fossil_notes_tendril"), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_limb"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("fossil_notes_plates"),
				ItemBook.getItemStack("fossil_notes_cavity"), ItemStack.EMPTY },
				ItemBook.getItemStack("fossil_notes_body"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("fossil_notes_limb"),
				ItemBook.getItemStack("fossil_notes_body"), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("anthology_fossil"), new boolean[]{false, false, false}));
		/*deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("bas_relief"),
				ItemBook.getItemStack("account_indus", true), ItemStack.EMPTY },
				ItemBook.getItemStack("vague_mythos"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("construction_size", true),
				ItemBook.getItemStack("account_indus", true), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("building_an_offering"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("patrician_letters", true),
				ItemBook.getItemStack("mystic_thesis", true), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("other_side"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("alchemy_dict", true),
				ItemBook.getItemStack("alchemy_aide", true), ItemStack.EMPTY },
				ItemBook.getItemStack("trans_excerpt"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("dissertation"),
				ItemBook.getItemStack("trans_excerpt"), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("new_energy"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("old_glyphs", true),
				ItemBook.getItemStack("obelisk_carving"), ItemStack.EMPTY },
				ItemBook.getItemStack("cruel_shapes"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("cruel_shapes"),
				ItemBook.getItemStack("new_energy"), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("profane_calculus"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("profane_calculus"),
				ItemBook.getItemStack("other_side"), new ItemStack(Items.STRING) },
				LovecraftMain.spellHandler.spells.get("charge").getItemStack(), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("profane_calculus"),
				ItemBook.getItemStack("anthology_fossil"), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("perfect_perogative"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("bureau_files"),
				ItemBook.getItemStack("cultist_journal"), new ItemStack(Items.STRING) },
				LovecraftMain.spellHandler.spells.get("disassemble").getItemStack(), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				ItemBook.getItemStack("cultist_journal"),
				ItemBook.getItemStack("other_side"), new ItemStack(Items.STRING) },
				ItemBook.getItemStack("visions"), new boolean[]{false, false, false}));*/
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemTokenBat), new ItemStack(Items.PAPER), ItemStack.EMPTY},
				ItemBook.getItemStack("token_notes_bat"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemTokenDread), new ItemStack(Items.PAPER), ItemStack.EMPTY},
				ItemBook.getItemStack("token_notes_dread"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemTokenAwake), new ItemStack(Items.PAPER), ItemStack.EMPTY},
				ItemBook.getItemStack("token_notes_awake"), new boolean[]{false, false, false}));
		deskRecipes.add(new DeskRecipe(new ItemStack[] {
				new ItemStack(LovecraftMain.itemTokenBrave), new ItemStack(Items.PAPER), ItemStack.EMPTY},
				ItemBook.getItemStack("token_notes_brave"), new boolean[]{false, false, false}));
	}
}

package daatguy.lovecraft.item;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.resources.I18n;
import daatguy.lovecraft.book.Book;
import daatguy.lovecraft.book.DictionaryBook;

public class SubItemsHandler {

	public static ArrayList<String> fossils = new ArrayList<String>();
	public static HashMap<String, Book> books = new HashMap<String, Book>();

	public static final int COMMON = -1;
	public static final int LATIN = 0;
	public static final int SANSKRIT = 1;
	public static final int GREEK = 2;
	public static final int RUNIC = 3;
	public static final int OLDSPEAK = 4;
	//TODO if we add this--we should update other switch functions, etc. See DeskHandler for other language handling
	//public static final int ARAMAIC = 5;

	public SubItemsHandler() {
		fossils.add("plates");
		fossils.add("spines");
		fossils.add("tendril");
		fossils.add("cavity");

		//registerBook(new Book("old_glyphs", 11, 6, RUNIC));
		//registerBook(new Book("dissertation", 3, 5));
		//registerBook(new Book("alchemy_dict", 15, 5, LATIN));
		//registerBook(new Book("alchemy_aide", 11, 5, LATIN));
		//registerBook(new Book("trans_excerpt", 10));
		//registerBook(new Book("new_energy", 14));
		//registerBook(new Book("cruel_shapes", 7));
		//registerBook(new Book("profane_calculus", 1));
		//registerBook(new Book("construction_size", 13, 8, GREEK));
		//registerBook(new Book("patrician_letters", 7, 7, LATIN));
		//registerBook(new Book("perfect_perogative", 10));
		//registerBook(new Book("account_indus", 15, 8, SANSKRIT));
		//registerBook(new Book("building_an_offering", 4));
		//registerBook(new Book("vague_mythos", 9));
		//registerBook(new Book("mystic_thesis", 0, 6, RUNIC));
		//registerBook(new Book("other_side", 6));
		//registerBook(new Book("cultist_journal", 0));
		//registerBook(new Book("bureau_files", 7));;
		//registerBook(new Book("visions", 5));
		
		registerBook(new DictionaryBook("latin_dict", 5, 10));
		registerBook(new DictionaryBook("greek_dict", 0, 10));
		registerBook(new DictionaryBook("sanskrit_dict", 2, 7));
		registerBook(new DictionaryBook("runic_dict", 4, 7));
		registerBook(new Book("latin_history", 3, 3, LATIN));
		registerBook(new Book("fossil_notes_plates", 8));
		registerBook(new Book("fossil_notes_spines", 9));
		registerBook(new Book("fossil_notes_tendril", 16));
		registerBook(new Book("fossil_notes_cavity", 10));
		registerBook(new Book("fossil_notes_body", 7));
		registerBook(new Book("fossil_notes_limb", 19));
		registerBook(new Book("anthology_fossil", 12));
		registerBook(new Book("obelisk_carving", 9));
		registerBook(new Book("bas_relief", 10));
		registerBook(new Book("token_notes_bat", 8));
		registerBook(new Book("token_notes_dread", 17));
		registerBook(new Book("token_notes_awake", 19));
		registerBook(new Book("token_notes_brave", 9));
	}

	public void registerBook(Book book) {
		books.put(book.name, book);
	}

	/**
	 * COMMON: -1
	 * LATIN: 0
	 * SANSKRIT: 1
	 * GREEK: 2
	 * RUNIC: 3
	 * OLDSPEAK: 4
	 */
	public static String getLanguageFromID(int id) {
		switch (id) {
		case LATIN:
			return I18n.format("book.lang.latin");
		case SANSKRIT:
			return I18n.format("book.lang.sanskrit");
		case GREEK:
			return I18n.format("book.lang.greek");
		case RUNIC:
			return I18n.format("book.lang.runic");
		case OLDSPEAK:
			return I18n.format("book.lang.oldspeak");
		default:
			return I18n.format("book.lang.translated");
		}
	}
}

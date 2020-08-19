package daatguy.lovecraft.book.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import daatguy.lovecraft.book.Book;
import daatguy.lovecraft.book.CanGetStack;
import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.item.ItemStack;

public class SpellHandler {

	public static HashMap<String, Spell> spells = new HashMap<String, Spell>();

	public SpellHandler() {
	}
	
	public void init() {
		registerSpell(new SpellCharge());
		registerSpell(new SpellCap());
		registerSpell(new SpellDisassemble());
		registerSpell(new SpellEnterRoom());
		//registerSpell(new SpellDrug());
	}

	public void registerSpell(Spell spell) {
		spells.put(spell.name, spell);
	}

	
	public static ItemStack getWeightedRandomSpell(Random random) {
		// Add spell weights/rarity?
		ArrayList<Object> wSpells = new ArrayList<Object>();
		for (String key : spells.keySet()) {
			Spell s = spells.get(key);
			for (int i = 0; i < s.genWeight; i++) {
				wSpells.add(s);
			}
		}
		for (String key : LovecraftMain.subItemsHandler.books.keySet()) {
			Book s = LovecraftMain.subItemsHandler.books.get(key);
			for (int i = 0; i < s.genWeight; i++) {
				wSpells.add(s);
			}
		}
		return ((CanGetStack)wSpells.get(random.nextInt(wSpells.size()))).getItemStack();
	}
}

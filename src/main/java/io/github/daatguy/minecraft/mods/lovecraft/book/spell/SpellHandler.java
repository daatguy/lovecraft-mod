package io.github.daatguy.minecraft.mods.lovecraft.book.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.github.daatguy.minecraft.mods.lovecraft.book.Book;
import io.github.daatguy.minecraft.mods.lovecraft.book.CanGetStack;
import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import net.minecraft.item.ItemStack;

public class SpellHandler {

	public static HashMap<String, Spell> spells = new HashMap<String, Spell>();

	public SpellHandler() {
		// spells.put("default", new Spell());
		// spells.put("default2", new Spell());
		// spells.get("default2").obeliskLevel = 3;
		// spells.put("test", new SpellTest());
		registerSpell(new SpellCharge());
		registerSpell(new SpellCap());
		registerSpell(new SpellDisassemble());
		registerSpell(new SpellEnterRoom());
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
		System.out.println(wSpells);
		return ((CanGetStack)wSpells.get(random.nextInt(wSpells.size()))).getItemStack();
	}
}

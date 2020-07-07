package daatguy.lovecraft.item.interfaces;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public interface ItemEldritchWeapon {

	public static int loreLineThreshhold = 25;

	public default void addEldritchWeaponTooltip(ItemStack stack,
			World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("");
		String rawLore[] = I18n.format("tooltip.eldritch_weapon").split("\\s+");
		String curLine = TextFormatting.DARK_AQUA+"";
		for (String word : rawLore) {
			curLine = curLine + word + " ";
			if (curLine.length() > loreLineThreshhold) {
				tooltip.add(curLine.trim());
				curLine = TextFormatting.DARK_AQUA+"";
			}
		}
		if (curLine != "") {
			tooltip.add(curLine.trim());
		}
	}
}

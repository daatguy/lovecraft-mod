package daatguy.lovecraft.item.interfaces;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ItemDescription {

	public static int loreLineThreshhold = 25;

	public default void addDescriptionTooltip(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		//super.addInformation(stack, worldIn, tooltip, flagIn);
		if (!I18n.format(((Item)this).getUnlocalizedName() + ".description").equals(((Item)this).getUnlocalizedName() + ".description")) {
			String rawLore[] = I18n.format(((Item)this).getUnlocalizedName() + ".description").split(
					"\\s+");
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
	}
}

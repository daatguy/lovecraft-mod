package daatguy.lovecraft.item;

import java.util.List;

import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFossilDust extends ItemFood {

	private static int loreLineThreshhold = 25;

	@Override
	public void addInformation(ItemStack stack, World worldIn,
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
	
	public ItemFossilDust() {
		super(1, 0.2f, false);
		this.setAlwaysEdible();
		this.setCreativeTab(LovecraftMain.lovecraftTab);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 60;
	}
	
	
}

package daatguy.lovecraft.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSimpleBlock extends ItemBlock{
	
	private static int loreLineThreshhold = 25;

	public ItemSimpleBlock(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		//super.addInformation(stack, worldIn, tooltip, flagIn);
		if (!I18n.format(getUnlocalizedName() + ".description").equals(getUnlocalizedName() + ".description")) {
			String rawLore[] = I18n.format(getUnlocalizedName() + ".description").split(
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

package daatguy.lovecraft.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import daatguy.lovecraft.item.interfaces.ItemDescription;

public class ItemSimple extends Item implements ItemDescription{
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		addDescriptionTooltip(stack, worldIn, tooltip, flagIn);
	}

}
package daatguy.lovecraft.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import daatguy.lovecraft.item.interfaces.ItemDescription;
import daatguy.lovecraft.item.interfaces.ItemEldritchWeapon;

public class ItemFossilKnife extends ItemSword implements ItemEldritchWeapon, ItemDescription {
	
	public ItemFossilKnife(ToolMaterial material) {
		super(material);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		addDescriptionTooltip(stack, worldIn, tooltip, flagIn);
		addEldritchWeaponTooltip(stack, worldIn, tooltip, flagIn);
		//To add the damage tooltip
		super.addInformation(stack, worldIn, tooltip, flagIn);
		//Get rid of a line break
		tooltip.remove(tooltip.size()-1);
	}

}

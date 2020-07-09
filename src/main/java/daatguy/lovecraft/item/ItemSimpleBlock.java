package daatguy.lovecraft.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import daatguy.lovecraft.item.interfaces.ItemDescription;

public class ItemSimpleBlock extends ItemBlock implements ItemDescription{

	public EnumRarity rarity;
	
	public ItemSimpleBlock(Block block) {
		super(block);
		this.rarity = EnumRarity.COMMON;
	}
	
	public ItemSimpleBlock(Block block, EnumRarity rarity) {
		super(block);
		this.rarity = rarity;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		addDescriptionTooltip(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
    {
        return rarity;
    }
	
}

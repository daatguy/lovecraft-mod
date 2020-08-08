package daatguy.lovecraft.item;

import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTokenDread extends ItemDecay{

	public ItemTokenDread(EnumRarity rarity, int maxTicks, boolean isMemory) {
		super(rarity, maxTicks, isMemory);
	}

	@Override
	public ItemStack getDecaysInto(ItemStack stack, World worldIn,
			Entity entityIn, int itemSlot, boolean isSelected) {
		if(worldIn.getBlockState(new BlockPos(entityIn.posX,entityIn.posY-1,entityIn.posZ))==LovecraftMain.blockWeirdedBrick.getDefaultState()) {
			return new ItemStack(LovecraftMain.itemTokenBrave);
		}
		return super.getDecaysInto(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
}

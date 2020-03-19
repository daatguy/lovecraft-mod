package daatguy.lovecraft.block;

import javax.annotation.Nullable;

import daatguy.lovecraft.tileentity.TileEntityCarving;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.SoundType;

public class BlockCarvedBlock extends BlockSimple {

	public BlockCarvedBlock(Material material) {
		super(material);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCarving();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		ItemStack heldItem = playerIn.getHeldItem(hand);
		TileEntityCarving carving = (TileEntityCarving) world.getTileEntity(pos);
		if (heldItem.getItem() == Items.PAPER) {
			//TODO: create untranslated rubbing
			return false;
		}// else if (heldItem.getItem() == )
		return false;
	}
}

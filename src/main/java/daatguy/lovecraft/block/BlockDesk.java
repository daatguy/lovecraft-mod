package daatguy.lovecraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import daatguy.lovecraft.client.gui.GuiHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;

public class BlockDesk extends BlockSimple {

	public BlockDesk(Material material) {
		super(material);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		//Open gui when clicked
		
		//TileEntityDesk te = (TileEntityDesk) worldIn.getTileEntity(pos);

		if (!worldIn.isRemote) {
			playerIn.openGui(LovecraftMain.instance, GuiHandler.GUI_DESK,
					worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}
}

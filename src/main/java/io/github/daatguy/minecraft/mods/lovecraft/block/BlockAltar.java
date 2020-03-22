package io.github.daatguy.minecraft.mods.lovecraft.block;

import java.util.List;

import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import net.minecraft.block.ITileEntityProvider;
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
import io.github.daatguy.minecraft.mods.lovecraft.client.gui.GuiHandler;
import io.github.daatguy.minecraft.mods.lovecraft.tileentity.TileEntityAltar;

public class BlockAltar extends BlockSimple implements ITileEntityProvider {

	public static final AxisAlignedBB boundingBox = new AxisAlignedBB(0f, 0f,
			0f, 1f, 0.75f, 1f);

	public BlockAltar(Material material) {
		super(material);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		//TileEntityAltar.setToObelisk(pos, worldIn, LovecraftMain.blockChargedObelisk, LovecraftMain.blockCarvedObelisk, TileEntityAltar.checkForObeliskHeight(worldIn, pos));
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return boundingBox;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn,
			boolean isActualState) {
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAltar();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityAltar te = (TileEntityAltar) worldIn.getTileEntity(pos);
		//Drops all the shit inside the tileEntity
		if (te != null
				&& te.hasCapability(
						CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			IItemHandler inventory = te.getCapability(
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (inventory != null) {
				for (int i = 0; i < inventory.getSlots(); i++) {
					if (inventory.getStackInSlot(i) != ItemStack.EMPTY && i < 10) {
						EntityItem item = new EntityItem(worldIn,
								pos.getX() + 0.5, pos.getY() + 0.5,
								pos.getZ() + 0.5, inventory.getStackInSlot(i));
						
						float multiplier = 0.4f;
						float motionX = worldIn.rand.nextFloat() - 0.5f;
						float motionY = worldIn.rand.nextFloat() - 0.5f;
						float motionZ = worldIn.rand.nextFloat() - 0.5f;
						
						item.motionX = motionX*multiplier;
						item.motionY = motionY*multiplier;
						item.motionZ = motionZ*multiplier;
						
						worldIn.spawnEntity(item);
					}
				}
			}
		}
		super.breakBlock(worldIn, pos, state);

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntityAltar te = (TileEntityAltar) worldIn.getTileEntity(pos);

		if (!worldIn.isRemote && te != null
				&& te.hasCapability(
						CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			playerIn.openGui(LovecraftMain.instance, GuiHandler.GUI_ALTAR,
					worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}
}

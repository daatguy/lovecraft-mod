package io.github.daatguy.mods.minecraft.lovecraft.block;

import javax.annotation.Nullable;

import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import io.github.daatguy.mods.minecraft.lovecraft.tileentity.TileEntityCarving;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.SoundType;

public class BlockCarvedBlock extends BlockSimple {
	
	static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockCarvedBlock(Material material) {
		super(material);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		 return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn,
											BlockPos pos,
											EnumFacing facing,
											float hitX, float hitY, float hitZ,
											int meta,
											EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, facing); //TODO: face the player
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

	@Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
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
			IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		if (side != world.getBlockState(pos).getValue(FACING)) {
			return false;
		}
		ItemStack heldItem = player.getHeldItem(hand);
		//Maybe just check entire inventory for charcoal? Probably not
		ItemStack offhand = player.inventory.offHandInventory.get(0); //seems a bit dangerous?
		TileEntityCarving carving = (TileEntityCarving) world.getTileEntity(pos);
		if (heldItem.getItem() == Items.PAPER
			&& offhand.getItem() == Items.COAL
			&& offhand.getMetadata() == 1) {
			heldItem.shrink(1); //TODO: don't consume paper if no space in inventory for rubbing
			player.inventory.addItemStackToInventory(new ItemStack(LovecraftMain.itemRubbing));
			return true;
		}// else if (heldItem.getItem() == ) //dictionary?
		return false;
	}
}

package io.github.daatguy.lovecraft.block;

import java.util.List;
import java.util.Random;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockResolvedObeliskCap extends BlockObeliskCap {

	public BlockResolvedObeliskCap(Material material) {
		super(material);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return LovecraftMain.itemBlockObeliskCap;
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state,
			EntityPlayer player) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn,
			BlockPos pos, Random rand) {
		for (int i = 0; i < 20; i++) {
			super.randomDisplayTick(stateIn, worldIn, pos, rand);
			double x = (double) pos.getX() + 0.45D + rand.nextFloat() * 0.1f;
			double y = (double) pos.getY() + 0.7D + rand.nextFloat() * 0.15f;
			double z = (double) pos.getZ() + 0.45D + rand.nextFloat() * 0.1f;
			worldIn.spawnParticle(EnumParticleTypes.END_ROD, x, y, z,
					0.0D, 0.0D, 0.0D);
		}
	}

}

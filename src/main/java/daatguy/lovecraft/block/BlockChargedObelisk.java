package daatguy.lovecraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;
import daatguy.lovecraft.tileentity.TileEntityChargedObelisk;

public class BlockChargedObelisk extends BlockObelisk implements ITileEntityProvider {

	public BlockChargedObelisk(Material material) {
		super(material);
	}
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if(!player.isCreative()) {
			TileEntityChargedObelisk.explode(worldIn, pos);
		}
		
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state,
			EntityPlayer player) {
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos,
			Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		//Blow up if neighbor is changed
		((TileEntityChargedObelisk)worldIn.getTileEntity(pos)).onBlockUpdate();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChargedObelisk();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		//Drop carved obelisk blocks instead of charged ones
		return LovecraftMain.itemBlockCarvedObelisk;
	}

	@Override
	public int quantityDropped(Random rand) {

		return 1;

	}

	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn,
			BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if (rand.nextFloat() > 0.9) {
			double x = (double) pos.getX() + 0.5D;
			double y = (double) pos.getY() + rand.nextFloat();
			double z = (double) pos.getZ() + 0.5D;
			if (rand.nextBoolean()) {
				z += 0.3f * (rand.nextFloat() - 0.5D);
				x += 0.6f * ((float) rand.nextInt(2) * 2.0f - 1.0f);
			} else {
				x += 0.3f * (rand.nextFloat() - 0.5D);
				z += 0.6f * ((float) rand.nextInt(2) * 2.0f - 1.0f);
			}

			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y, z,
					0.0D, 0.0D, 0.0D);
		}
	}*/
}

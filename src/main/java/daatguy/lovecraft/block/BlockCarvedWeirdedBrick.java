package daatguy.lovecraft.block;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCarvedWeirdedBrick extends BlockCarvedBlock {

	public BlockCarvedWeirdedBrick(Material material) {
		super(material);
	}

	public BlockCarvedWeirdedBrick(Material material, SoundType soundType) {

		super(material);
		this.setSoundType(soundType);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}	

	@Override
	public boolean removedByPlayer(IBlockState state, World world,
			BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if(player.capabilities.isCreativeMode) {
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		} else {
			BlockWeirdedBrick.shatter(state, world, pos, player);
			return false;
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		super.onEntityWalk(worldIn, pos, entityIn);
		BlockWeirdedBrick.fearWalk(worldIn, pos, entityIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn,
			BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		BlockWeirdedBrick.smoke(worldIn, pos, rand);
	}
	
}

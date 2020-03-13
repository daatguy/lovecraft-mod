package daatguy.lovecraft.block;

import java.util.Random;

import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWeirdedBrick extends Block {

	public BlockWeirdedBrick(Material material) {

		super(material);
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
			spawnAsEntity(world, pos, new ItemStack(LovecraftMain.itemWeirdDust));
			return false;
		}
	}
	

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	public BlockWeirdedBrick(Material material, SoundType soundType) {

		super(material);
		this.setSoundType(soundType);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		super.onEntityWalk(worldIn, pos, entityIn);
		if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			if (worldIn.rand.nextFloat() > 0.999) {
				((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(
						LovecraftMain.potionDread, 20*60*5, 0, false, false));
				((EntityPlayer) entityIn).sendMessage(new TextComponentString(TextFormatting.BLUE + I18n.format("potion.dread.message"+String.valueOf(worldIn.rand.nextInt(4)))));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn,
			BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if (rand.nextFloat() > 0.4) {
			double x = (double) pos.getX() + rand.nextFloat();
			double y = (double) pos.getY() + 0.5D;
			double z = (double) pos.getZ() + rand.nextFloat();

			if (!worldIn.getBlockState(pos.up()).isFullCube()) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z,
						0.0D, 0.0D, 0.0D);
			}
		}
	}

}
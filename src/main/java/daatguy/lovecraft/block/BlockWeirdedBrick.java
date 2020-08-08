package daatguy.lovecraft.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import daatguy.lovecraft.client.sound.SoundEventHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.world.RoomTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
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
			this.shatter(state, world, pos, player);
			return false;
		}
	}
	
	public static void shatter(IBlockState state, World world,
			BlockPos pos, EntityPlayer player) {
		spawnAsEntity(world, pos, new ItemStack(LovecraftMain.itemWeirdShards));
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEventHandler.SEPULCHRAL_BREAK, SoundCategory.BLOCKS,
				1.0F, 1.3f+world.rand.nextFloat()*0.5f, false);
		for(int i = 0; i < 60; i++) {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()+world.rand.nextDouble(), pos.getY()+world.rand.nextDouble(), pos.getZ()+world.rand.nextDouble(),
					world.rand.nextDouble()*0.2D-0.1D, 0D, world.rand.nextDouble()*0.2D-0.1D);
		}
		for(int i = 0; i < 30; i++) {
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX()+world.rand.nextDouble(), pos.getY()+world.rand.nextDouble(), pos.getZ()+world.rand.nextDouble(),
					world.rand.nextDouble()*0.2D-0.1D, 0D, world.rand.nextDouble()*0.2D-0.1D);
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
		this.fearWalk(worldIn, pos, entityIn);
	}

	public static void fearWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			float chance = 0.001f;
			for(ItemStack itemstack :((EntityPlayer)entityIn).inventory.mainInventory) {
				if(itemstack.getItem()==LovecraftMain.itemTokenDread) {
					chance /= 2.0f;
				}
			}
			if (worldIn.rand.nextFloat() > 1.0f-chance) {
				//((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(
				//		LovecraftMain.potionDread, 20*60*5, 0, false, false));
				((EntityPlayer)entityIn).addItemStackToInventory(new ItemStack(LovecraftMain.itemTokenDread));
				((EntityPlayer) entityIn).sendMessage(new TextComponentString(TextFormatting.BLUE + I18n.format("potion.dread.message"+String.valueOf(worldIn.rand.nextInt(4)))));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn,
			BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		this.smoke(worldIn, pos, rand);
	}
	
	public static void smoke(World worldIn,
			BlockPos pos, Random rand) {
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
	
	@Override
	public PathNodeType getAiPathNodeType(IBlockState state,
										  IBlockAccess world,
										  BlockPos pos,
										  @Nullable EntityLiving entity) {
		return PathNodeType.DAMAGE_OTHER;
	}
	

}
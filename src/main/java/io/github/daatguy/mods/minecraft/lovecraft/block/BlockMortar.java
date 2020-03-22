package io.github.daatguy.mods.minecraft.lovecraft.block;

import java.util.List;

import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.github.daatguy.mods.minecraft.lovecraft.client.sound.SoundEventHandler;
import io.github.daatguy.mods.minecraft.lovecraft.container.AlchemyRecipes;

public class BlockMortar extends BlockSimple {

	public static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.25f,
			0f, 0.25f, 0.75f, 0.5f, 0.75f);
	
	public BlockMortar(Material material) {
		super(material);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		for(AlchemyRecipes.Recipe rec : LovecraftMain.alchemyRecipes.MORTAR_RECIPES) {
			if(rec.TryCraft(playerIn, hand)) {
				worldIn.playSound( pos.getX()+0.5f, pos.getY()+0.25f, pos.getZ()+0.5f, SoundEventHandler.MORTAR, SoundCategory.BLOCKS, 0.7f, 1.0f, false);
				worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX()+0.5f, pos.getY()+0.25f, pos.getZ()+0.5f, 0, 0, 0, 1);
				break;
			}
		}
		return true;
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

}

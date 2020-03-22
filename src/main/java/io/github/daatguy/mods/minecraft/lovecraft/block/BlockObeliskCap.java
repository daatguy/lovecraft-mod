package io.github.daatguy.mods.minecraft.lovecraft.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockObeliskCap extends BlockSimple {

	public static final AxisAlignedBB boundingBox = new AxisAlignedBB(0f, 0f,
			0f, 1f, 1f, 1f);
	public static final AxisAlignedBB boundingBox0 = new AxisAlignedBB(0f, 0f,
			0f, 1f, 0.0625f * 4, 1f);
	public static final AxisAlignedBB boundingBox1 = new AxisAlignedBB(
			0.0625f * 1, 0.0625f * 4, 0.0625f * 1, 0.0625f * 14, 0.0625f * 6,
			0.0625f * 14);
	public static final AxisAlignedBB boundingBox2 = new AxisAlignedBB(
			0.0625f * 2, 0.0625f * 6, 0.0625f * 2, 0.0625f * 12, 0.0625f * 8,
			0.0625f * 12);
	public static final AxisAlignedBB boundingBox3 = new AxisAlignedBB(
			0.0625f * 3, 0.0625f * 8, 0.0625f * 3, 0.0625f * 10, 0.0625f * 10,
			0.0625f * 10);
	public static final AxisAlignedBB boundingBox4 = new AxisAlignedBB(
			0.0625f * 4, 0.0625f * 10, 0.0625f * 4, 0.0625f * 8, 0.0625f * 12,
			0.0625f * 8);
	public static final AxisAlignedBB boundingBox5 = new AxisAlignedBB(
			0.0625f * 5, 0.0625f * 12, 0.0625f * 5, 0.0625f * 6, 0.0625f * 14,
			0.0625f * 6);
	public static final AxisAlignedBB boundingBox6 = new AxisAlignedBB(
			0.0625f * 6, 0.0625f * 14, 0.0625f * 6, 0.0625f * 4, 0.0625f * 16,
			0.0625f * 4);

	public BlockObeliskCap(Material material) {
		super(material);
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
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox0);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox1);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox2);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox3);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox4);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox5);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, boundingBox6);
	}
}

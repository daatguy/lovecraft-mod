package daatguy.lovecraft.generator.components;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import daatguy.lovecraft.block.BlockCarvedBlock;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityCarving;

public class TombStructureComponent extends StructureComponent {
	/** The size of the bounding box for this feature in the X axis */
	protected int width;
	/** The size of the bounding box for this feature in the Y axis */
	protected int height;
	/** The size of the bounding box for this feature in the Z axis */
	protected int depth;
	protected int horizontalPos = -1;

	public TombStructureComponent(Random rand, int x, int y, int z, int sizeX,
			int sizeY, int sizeZ) {
		super();
		this.width = sizeX;
		this.height = sizeY;
		this.depth = sizeZ;
		this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

		if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
			this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeX - 1,
					y + sizeY - 1, z + sizeZ - 1);
		} else {
			this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeZ - 1,
					y + sizeY - 1, z + sizeX - 1);
		}
	}

	/**
	 * (abstract) Helper method to write subclass data to NBT
	 */
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("Width", this.width);
		tagCompound.setInteger("Height", this.height);
		tagCompound.setInteger("Depth", this.depth);
		tagCompound.setInteger("HPos", this.horizontalPos);
	}

	/**
	 * (abstract) Helper method to read subclass data from NBT
	 */
	protected void readStructureFromNBT(NBTTagCompound tagCompound,
			TemplateManager p_143011_2_) {
		this.width = tagCompound.getInteger("Width");
		this.height = tagCompound.getInteger("Height");
		this.depth = tagCompound.getInteger("Depth");
		this.horizontalPos = tagCompound.getInteger("HPos");
	}

	/**
	 * Calculates and offsets this structure boundingbox to average ground level
	 */
	protected boolean offsetToAverageGroundLevel(World worldIn,
			StructureBoundingBox structurebb, int yOffset) {
		if (this.horizontalPos >= 0) {
			return true;
		} else {
			int i = 0;
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
				for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l) {
					blockpos$mutableblockpos.setPos(l, 64, k);

					if (structurebb.isVecInside(blockpos$mutableblockpos)) {
						i += Math.max(
								worldIn.getTopSolidOrLiquidBlock(
										blockpos$mutableblockpos).getY(),
								worldIn.provider.getAverageGroundLevel());
						++j;
					}
				}
			}

			if (j == 0) {
				return false;
			} else {
				this.horizontalPos = i / j;
				this.boundingBox.offset(0, this.horizontalPos
						- this.boundingBox.minY + yOffset, 0);
				return true;
			}
		}
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn,
			StructureBoundingBox structBB) {
		IBlockState blockStonebrick = Blocks.STONEBRICK.getDefaultState();
		/*
		 * if (structBB == null) {
		 * System.out.print("why the fuck is the structBB null at ");
		 * System.out.println(this.boundingBox.toString()); return false; } else {
		 * System.out.println("got a non-null structBB:");
		 * System.out.println(structBB.toString()); }
		 */
		this.fillWithBlocks(worldIn, structBB, 0, 0, 0, 5, 9, 9,
				Blocks.AIR.getDefaultState(), blockStonebrick, false);
		int x = 3;
		int y = 1;
		int z = 8;
		this.generateChest(worldIn, structBB, randomIn, x, y, z,
				new ResourceLocation("lovecraft:chests/tomb"));
		z = 7;
		this.generateChest(worldIn, structBB, randomIn, this.getXWithOffset(x,
				z), this.getYWithOffset(y), this.getZWithOffset(x, z),
				new ResourceLocation("lovecraft:chests/tomb"));
		this.placeCarving(worldIn, 3, 0, 5, EnumFacing.UP, "carving.tomb"
				+ String.valueOf(randomIn.nextInt(5)), randomIn.nextInt(4));
		return true;
	}

	public void placeCarving(World worldIn, int x, int y, int z,
			EnumFacing facing, String carving, int language) {
		BlockPos pos = new BlockPos(this.getXWithOffset(x, z),
									this.getYWithOffset(y),
									this.getZWithOffset(x, z));
		worldIn.setBlockState(pos,
				LovecraftMain.blockCarvedStonebrick.getDefaultState()
						.withProperty(BlockCarvedBlock.FACING, facing), 2);
		TileEntityCarving tile = (TileEntityCarving) worldIn.getTileEntity(pos);
		if(tile!=null) {
			tile.carving = carving;
			tile.language = language;
		}
	}
	
}
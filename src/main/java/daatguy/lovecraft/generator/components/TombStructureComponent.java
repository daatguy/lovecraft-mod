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
		System.out.println(this.horizontalPos);
		if (this.horizontalPos >= 0) {
			return true;
		} else {
			int i = 0;
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
			for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k) {
				for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l) {
					blockpos$mutableblockpos.setPos(l, 64, k);
					i += Math.max(
							worldIn.getTopSolidOrLiquidBlock(
									blockpos$mutableblockpos).getY(),
							worldIn.provider.getAverageGroundLevel());
					++j;
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

	public class TombBlockSelector extends StructureComponent.BlockSelector {

		@Override
		public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
			if (y > 6 && rand.nextInt(10) == 0) {
				this.blockstate = Blocks.WEB.getDefaultState();
				return;
			} else if (!wall) {
				this.blockstate = Blocks.AIR.getDefaultState();
				return;
			} else {
				if (rand.nextBoolean()) {
					this.blockstate = Blocks.STONEBRICK.getStateFromMeta(0);
					return;
				} else {
					if (rand.nextBoolean()) {
						this.blockstate = Blocks.STONEBRICK.getStateFromMeta(1);
						return;
					} else {
						this.blockstate = Blocks.STONEBRICK.getStateFromMeta(2);
						return;
					}
				}
			}
		}

	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn,
			StructureBoundingBox structBB) {
		this.offsetToAverageGroundLevel(worldIn, this.boundingBox, -11);
		IBlockState solidCheck = this.getBlockStateFromPos(worldIn, 2, 0, 4,this.boundingBox);
		if(!solidCheck.isFullBlock()) return true;
		IBlockState blockStonebrick = Blocks.STONEBRICK.getDefaultState();
		IBlockState blockPillar = Blocks.COBBLESTONE_WALL.getDefaultState();
		if (this.boundingBox.minY < 10)
			return false;
		System.out.println(String.valueOf(this.getXWithOffset(0, 0)) + " "
				+ String.valueOf(this.getYWithOffset(0)) + " "
				+ String.valueOf(this.getZWithOffset(0, 0)));
		this.fillWithRandomizedBlocks(worldIn, this.boundingBox, 0, 0, 0, 4, 8,
				8, false, randomIn, new TombBlockSelector());
		this.fillWithBlocks(worldIn, this.boundingBox, 1, 1, 3, 1, 7, 3,
				blockPillar, blockPillar, false);
		this.fillWithBlocks(worldIn, this.boundingBox, 3, 1, 3, 3, 7, 3,
				blockPillar, blockPillar, false);
		this.fillWithBlocks(worldIn, this.boundingBox, 1, 1, 6, 1, 7, 6,
				blockPillar, blockPillar, false);
		this.fillWithBlocks(worldIn, this.boundingBox, 3, 1, 6, 3, 7, 6,
				blockPillar, blockPillar, false);
		this.generateChest(worldIn, this.boundingBox, randomIn, 2, 1, 6,
				new ResourceLocation("lovecraft:chests/tomb"));
		this.generateChest(worldIn, this.boundingBox, randomIn, 2, 1, 7,
				new ResourceLocation("lovecraft:chests/tomb"));
		// Using "Allah" in a runic sentence is a bit weird -- add logic?
		this.placeCarving(worldIn, 2, 0, 4, EnumFacing.UP, "carving.tomb"
				+ String.valueOf(randomIn.nextInt(4)), randomIn.nextInt(3));
		// Roof
		for (int x = 0; x < 5; x++) {
			for (int z = 0; z < 9; z++) {
				IBlockState bs = this.getBlockStateFromPos(worldIn, x, 9, z,
						this.boundingBox);
				if (randomIn.nextInt(5) == 0 && bs.getBlock() != Blocks.LOG
						&& bs.getBlock() != Blocks.LOG2
						&& bs.getBlock() != Blocks.AIR) {
					this.setBlockState(worldIn, blockStonebrick, x, 9, z,
							this.boundingBox);
				}
				bs = this.getBlockStateFromPos(worldIn, x, 10, z,
						this.boundingBox);
				if (randomIn.nextInt(10) == 0 && bs.getBlock() != Blocks.LOG
						&& bs.getBlock() != Blocks.LOG2
						&& this.getBlockStateFromPos(worldIn, x, 9, z,
								this.boundingBox).getBlock() != Blocks.AIR) {
					this.setBlockState(worldIn, blockStonebrick, x, 10, z,
							this.boundingBox);
				}
			}
		}
		return true;
	}

	public void placeCarving(World worldIn, int x, int y, int z,
			EnumFacing facing, String carving, int language) {
		BlockPos pos = new BlockPos(this.getXWithOffset(x, z),
				this.getYWithOffset(y), this.getZWithOffset(x, z));
		worldIn.setBlockState(pos, LovecraftMain.blockCarvedStonebrick
				.getDefaultState()
				.withProperty(BlockCarvedBlock.FACING, facing), 2);
		TileEntityCarving tile = (TileEntityCarving) worldIn.getTileEntity(pos);
		if (tile != null) {
			tile.carving = carving;
			tile.language = language;
		}
	}

}
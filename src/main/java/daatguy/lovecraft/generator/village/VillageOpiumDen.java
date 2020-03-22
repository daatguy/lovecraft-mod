package daatguy.lovecraft.generator.village;

import java.util.List;
import java.util.Random;

import daatguy.lovecraft.block.BlockHookah;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.event.ProfessionHandler;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillageOpiumDen extends StructureVillagePieces.Village {
	
	/**
	 * Instantiates a new village opium den.
	 */
	public VillageOpiumDen() {
	}

	/**
	 * Instantiates a new village opium den.
	 *
	 * @param parStart
	 *            the par start
	 * @param parType
	 *            the par type
	 * @param parRand
	 *            the par rand
	 * @param parStructBB
	 *            the par struct BB
	 * @param parFacing
	 *            the par facing
	 */
	public VillageOpiumDen(StructureVillagePieces.Start parStart, int parType,
			Random parRand, StructureBoundingBox parStructBB,
			EnumFacing parFacing) {
		super(parStart, parType);
		setCoordBaseMode(parFacing);
		boundingBox = parStructBB;
	}

	public static VillageOpiumDen createPiece(
			StructureVillagePieces.Start start,
			List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_,
			int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox
				.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_,
						p_175850_5_, 0, 0, 0, 9, 8, 6, facing);
		return canVillageGoDeeper(structureboundingbox)
				&& StructureComponent.findIntersecting(p_175850_1_,
						structureboundingbox) == null ? new VillageOpiumDen(
				start, p_175850_7_, rand, structureboundingbox, facing) : null;
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs,
	 * Mob Spawners, it closes Mineshafts at the end, it adds Fences...
	 *
	 * @param parWorld
	 *            the par world
	 * @param parRand
	 *            the par rand
	 * @param parStructBB
	 *            the par struct BB
	 * @return true, if successful
	 */
	@Override
	public boolean addComponentParts(World worldIn, Random randomIn,
			StructureBoundingBox structureBoundingBoxIn) {
		if (this.averageGroundLvl < 0) {
			this.averageGroundLvl = this.getAverageGroundLevel(worldIn,
					structureBoundingBoxIn);

			if (this.averageGroundLvl < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.averageGroundLvl
					- this.boundingBox.maxY + 9 - 3, 0);
		}

		IBlockState blockCobble = this
				.getBiomeSpecificBlockState(Blocks.COBBLESTONE
						.getDefaultState());
		IBlockState blockOakStairsN = this
				.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState()
						.withProperty(BlockStairs.FACING, EnumFacing.NORTH));
		IBlockState blockOakStairsS = this
				.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState()
						.withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
		IBlockState blockOakStairsE = this
				.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState()
						.withProperty(BlockStairs.FACING, EnumFacing.EAST));
		IBlockState blockPlanks = this.getBiomeSpecificBlockState(Blocks.PLANKS
				.getDefaultState());
		IBlockState blockStoneStairsN = this
				.getBiomeSpecificBlockState(Blocks.STONE_STAIRS
						.getDefaultState().withProperty(BlockStairs.FACING,
								EnumFacing.NORTH));
		IBlockState blockStoneStairsS = this
				.getBiomeSpecificBlockState(Blocks.STONE_STAIRS
						.getDefaultState().withProperty(BlockStairs.FACING,
								EnumFacing.SOUTH));
		IBlockState blockOakFence = this
				.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 5, 4,
				Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(),
				false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -1, 0, 8, -1,
				5, blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 8, 4, 5,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 1, 8, 5, 4,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 2, 8, 6, 3,
				blockCobble, blockCobble, false);

		for (int i = -1; i <= 2; ++i) {
			for (int j = 0; j <= 8; ++j) {
				this.setBlockState(worldIn, blockOakStairsN, j, 5 + i, i,
						structureBoundingBoxIn);
				this.setBlockState(worldIn, blockOakStairsS, j, 5 + i, 5 - i,
						structureBoundingBoxIn);
			}
		}

		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 5,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 8, 0, 5,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 0, 4,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 0, 7, 0, 0,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 5, 0, 3, 5,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 3, 5,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 3, 0,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 4,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 7, 3, 5,
				blockPlanks, blockPlanks, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 8, 3, 4,
				blockCobble, blockCobble, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 7, 3, 0,
				blockPlanks, blockPlanks, false);
		/*
		 * this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4,
		 * 1, 0, structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 5, 1, 0,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 6, 1, 0,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 0, 1, 2,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 0, 1, 3,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 8, 1, 2,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 8, 1, 3,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 2, 1, 5,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 3, 1, 5,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 5, 1, 5,
		 * structureBoundingBoxIn); this.setBlockState(worldIn,
		 * Blocks.GLASS_PANE.getDefaultState(), 6, 1, 5,
		 * structureBoundingBoxIn);
		 */
		// Roof reams
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 1, 7, 2, 1,
				blockPlanks, blockPlanks, false);
		//this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 4, 7, 2, 4,
		//		blockPlanks, blockPlanks, false);
		this.setBlockState(worldIn, blockPlanks, 1, 2, 4, structureBoundingBoxIn);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 5, 2, 2, 5,
				Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.setBlockState(worldIn, blockPlanks, 3, 2, 4, structureBoundingBoxIn);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 2, 5,
				Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.setBlockState(worldIn, blockPlanks, 5, 2, 4, structureBoundingBoxIn);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 5, 6, 2, 5,
				Blocks.GLASS_PANE.getDefaultState(), Blocks.GLASS_PANE.getDefaultState(), false);
		this.setBlockState(worldIn, blockPlanks, 7, 2, 4, structureBoundingBoxIn);

		// Place hookah and carpet
		this.setBlockState(worldIn, LovecraftMain.blockHookah.getDefaultState()
				.withProperty(BlockHookah.FACING, EnumFacing.EAST), 2, 0, 1,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(12), 3, 0,
				1, structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(14), 4, 0,
				1, structureBoundingBoxIn);
		this.setBlockState(worldIn, LovecraftMain.blockHookah.getDefaultState()
				.withProperty(BlockHookah.FACING, EnumFacing.EAST), 5, 0, 1,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(14), 6, 0,
				1, structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(12), 7, 0,
				1, structureBoundingBoxIn);
		this.setBlockState(worldIn, LovecraftMain.blockHookah.getDefaultState()
				.withProperty(BlockHookah.FACING, EnumFacing.NORTH), 1, 0, 4,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(12), 2, 0,
				3, structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(14), 2, 0,
				4, structureBoundingBoxIn);
		this.setBlockState(worldIn, LovecraftMain.blockHookah.getDefaultState()
				.withProperty(BlockHookah.FACING, EnumFacing.NORTH), 3, 0, 4,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(14), 4, 0,
				3, structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.CARPET.getStateFromMeta(12), 4, 0,
				4, structureBoundingBoxIn);

		// Counter
		this.setBlockState(worldIn, blockCobble, 6, 0, 3,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, blockCobble, 6, 0, 4,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.REDSTONE_TORCH.getDefaultState(), 6,
				1, 3, structureBoundingBoxIn);

		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0,
				structureBoundingBoxIn);
		this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1,
				0, EnumFacing.NORTH);

		// Inside steps
		this.setBlockState(worldIn, blockCobble, 1, 0, 0,
				structureBoundingBoxIn);
		this.setBlockState(worldIn, blockStoneStairsS, 1, 0, 1,
				structureBoundingBoxIn);

		// Banners
		this.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState()
				.withProperty(BlockBanner.FACING, EnumFacing.NORTH), 2, 2, 2,
				structureBoundingBoxIn);
		setBannerColor(worldIn, 2, 2, 2, 3);
		this.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState()
				.withProperty(BlockBanner.FACING, EnumFacing.NORTH), 3, 2, 2,
				structureBoundingBoxIn);
		setBannerColor(worldIn, 3, 2, 2, 2);
		this.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState()
				.withProperty(BlockBanner.FACING, EnumFacing.NORTH), 5, 2, 2,
				structureBoundingBoxIn);
		setBannerColor(worldIn, 5, 2, 2, 1);
		this.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState()
				.withProperty(BlockBanner.FACING, EnumFacing.NORTH), 6, 2, 2,
				structureBoundingBoxIn);
		setBannerColor(worldIn, 6, 2, 2, 3);
		//Other side banners
		this.setBlockState(worldIn, Blocks.WALL_BANNER.getDefaultState()
				.withProperty(BlockBanner.FACING, EnumFacing.SOUTH), 3, 2, 3,
				structureBoundingBoxIn);
		setBannerColor(worldIn, 3, 2, 3, 1);
		
		//Crossbeams
		
		
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 1, 1, 3, 4,
				blockPlanks, blockPlanks, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 1, 3, 3, 4,
				blockPlanks, blockPlanks, false);
		this.setBlockState(worldIn, Blocks.SKULL.getDefaultState().withProperty(BlockSkull.NODROP, false)
				.withProperty(BlockSkull.FACING, EnumFacing.UP), 4, 3, 1,
				structureBoundingBoxIn);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 1, 5, 3, 4,
				blockPlanks, blockPlanks, false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 1, 7, 3, 4,
				blockPlanks, blockPlanks, false);

		if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn)
				.getMaterial() == Material.AIR
				&& this.getBlockStateFromPos(worldIn, 1, -1, -1,
						structureBoundingBoxIn).getMaterial() != Material.AIR) {
			this.setBlockState(worldIn, blockStoneStairsN, 1, 0, -1,
					structureBoundingBoxIn);

			if (this.getBlockStateFromPos(worldIn, 1, -1, -1,
					structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH) {
				this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1,
						-1, -1, structureBoundingBoxIn);
			}
		}

		for (int l = 0; l < 6; ++l) {
			for (int k = 0; k < 9; ++k) {
				this.clearCurrentPositionBlocksUpwards(worldIn, k, 8, l,
						structureBoundingBoxIn);
				this.replaceAirAndLiquidDownwards(worldIn, blockCobble, k, -2,
						l, structureBoundingBoxIn);
			}
		}

		this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
		return true;
	}

	protected void setBannerColor(World world, int x, int y, int z, int color) {
		BlockPos pos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
		TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityBanner) {
            ((TileEntityBanner)tileentity).setItemValues(new ItemStack(Items.BANNER, 1, color),false);
        }
	}
	
	@Override
	protected net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
    {
        return ProfessionHandler.professionOpium;
    }

}

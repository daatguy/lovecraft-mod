package io.github.daatguy.minecraft.mods.lovecraft.generator;

import java.util.Random;

import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class OreGenerator implements IWorldGenerator {

	//Generates ores
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.getDimension()) {

		case -1:
			this.generateNether(world, random, chunkX, chunkZ);
			break;
		case 0:
			this.generateOverworld(world, random, chunkX, chunkZ);
			break;
		case 1:
			this.generateEnd(world, random, chunkX, chunkZ);
			break;

		}

	}

	public void generateNether(World world, Random random, int chunkX,
			int chunkZ) {
	}

	public void generateOverworld(World world, Random random, int chunkX,
			int chunkZ) {
		generateOre(LovecraftMain.blockFossil.getDefaultState(), world, random,
				chunkX, chunkZ, 1, 1, 9, 2, 24);
		// generateOre(LovecraftMain.blockAetherOre.getDefaultState(), world,
		// random, chunkX, chunkZ, 1, 15, 4, 2, 255);

	}

	public void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}

	public void generateOre(IBlockState block, World world, Random random,
			int chunkX, int chunkZ, int minVeinSize, int maxVeinSize,
			int chance, int minY, int maxY) {

		int veinSize;
		if (maxVeinSize - minVeinSize > 0) {
			veinSize = minVeinSize + random.nextInt(maxVeinSize - minVeinSize);
		} else {
			veinSize = minVeinSize;
		}
		int heightRange = maxY - minY;
		if (veinSize == 1) {
			for (int i = 0; i < chance; i++) {
				int x = chunkX * 16 + random.nextInt(16)+1;
				int y = minY + random.nextInt(heightRange);
				int z = chunkZ * 16 + random.nextInt(16)+1;
				if (isStone(world.getBlockState(new BlockPos(x, y, z)))) {
					world.setBlockState(new BlockPos(x, y, z), block, 2);
				}
			}
		} else {
			WorldGenMinable gen = new WorldGenMinable(block, veinSize);
			for (int i = 0; i < chance; i++) {
				int x = chunkX * 16 + random.nextInt(16)+1;
				int y = minY + random.nextInt(heightRange);
				int z = chunkZ * 16 + random.nextInt(16)+1;
				gen.generate(world, random, new BlockPos(x, y, z));
			}
		}
	}

	private boolean isStone(IBlockState blockState) {
		if (blockState != null && blockState.getBlock() == Blocks.STONE) {
			BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType) blockState
					.getValue(BlockStone.VARIANT);
			return blockstone$enumtype.isNatural();
		} else {
			return false;
		}
	}
}

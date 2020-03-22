package io.github.daatguy.lovecraft.generator;

import java.util.List;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.block.state.IBlockState;

public class ChunkGeneratorRoom implements IChunkGenerator {
	
	World world;
	ChunkPrimer primer = new ChunkPrimer();
	
	public ChunkGeneratorRoom(World worldIn) {
		world = worldIn;
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		Chunk c = new Chunk(world, primer, x, z); //just generate it empty lol
		byte[] bbytes = c.getBiomeArray();
		for (int i = 0; i < bbytes.length; i++) {
			bbytes[i] = 9; //end
		}
		return c;
	}

	@Override
	public void populate(int chunkX, int chunkZ) {
		if (chunkX != 0 || chunkZ != 0) {
			return;
		}
		
		final int SIDE_LENGTH = 9;
		final int ELEVATION = 100;
		final IBlockState BEDROCK = LovecraftMain.blockUnderstructure.getDefaultState();
		
		
		for (int x = 0; x < SIDE_LENGTH; x++) {
			for (int z = 0; z < SIDE_LENGTH; z++) {
				world.setBlockState(new BlockPos(x, ELEVATION, z), BEDROCK);
			}
		}
		
		for (int y = 1; y < SIDE_LENGTH - 1; y++) {
			for (int x = 0; x < SIDE_LENGTH; x++) {
				world.setBlockState(new BlockPos(x, y + ELEVATION, 0), BEDROCK);
				world.setBlockState(new BlockPos(x, y + ELEVATION, SIDE_LENGTH - 1), BEDROCK);
				for (int z = 1; z < SIDE_LENGTH - 1; z++) {
					world.setBlockState(new BlockPos(0, y + ELEVATION, z), BEDROCK);
					world.setBlockState(new BlockPos(SIDE_LENGTH - 1, y + ELEVATION, z), BEDROCK);
				}
			}
		}
		
		for (int x = 0; x < SIDE_LENGTH; x++) {
			for (int z = 0; z < SIDE_LENGTH; z++) {
				world.setBlockState(new BlockPos(x, ELEVATION + SIDE_LENGTH - 1, z), BEDROCK);
			}
		}
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}

}

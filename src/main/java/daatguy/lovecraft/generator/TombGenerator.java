package daatguy.lovecraft.generator;

import java.util.Random;

import daatguy.lovecraft.generator.components.TombStructureComponent;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TombGenerator implements IWorldGenerator {

	/**A tomb every {@link tombRarity} chunks*/
	public final int tombRarity = 64;
	
	public boolean isBiomeOcean(Biome biome) {
		return biome == Biomes.DEEP_OCEAN || biome == Biomes.FROZEN_OCEAN
				|| biome == Biomes.OCEAN;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		int x = chunkX * 16 + random.nextInt(16) + 8;
		int y = -1;
		int z = chunkZ * 16 + random.nextInt(16) + 8;

		switch (world.provider.getDimension()) {

		case -1:
			break;
		case 0:
			if (random.nextInt(tombRarity) == 0
					&& !isBiomeOcean(world.provider.getBiomeProvider()
							.getBiome(new BlockPos(x, y, z)))) {
				TombStructureComponent tomb = new TombStructureComponent(
						random, x, y, z, 5, 12, 9);
				tomb.addComponentParts(world, random, null);
			}

			break;
		case 1:
			break;

		}

	}

}

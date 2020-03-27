package daatguy.lovecraft.generator;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import daatguy.lovecraft.core.LovecraftMain;

public class DecorationGenerator implements IWorldGenerator {

	//FLOWERS
	
	
	private FlowerGenerator drugFlowerGen = new FlowerGenerator(LovecraftMain.blockFlowerDrug);
	
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
		if(world.getBiomeForCoordsBody(new BlockPos(chunkX*16,70,chunkZ*16)) instanceof BiomeJungle) {
			populate(drugFlowerGen, world, random, chunkX, chunkZ, 0.2f);
		}
	}

	public void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}
	
	public void populate(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, float amount) {
		if(amount<1) {
			if(random.nextFloat()<=amount) {
				int x = chunkX*16+random.nextInt(16);
				int z = chunkZ*16+random.nextInt(16);
				int y = world.getChunkFromChunkCoords(x >> 4, z >> 4).getHeight(new BlockPos(x & 15, 0, z & 15)) - 1;
				generator.generate(world, random, new BlockPos(x,y,z));
			}
		} else {		
			for(int i = 0; i < (int)amount; i++) {
				int x = chunkX*16+random.nextInt(16);
				int z = chunkZ*16+random.nextInt(16);
				int y = world.getChunkFromChunkCoords(x >> 4, z >> 4).getHeight(new BlockPos(x & 15, 0, z & 15)) - 1;
				generator.generate(world, random, new BlockPos(x,y,z));
			}
		}
	}
}

package daatguy.lovecraft.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ShittyPyramid implements IWorldGenerator {
	/**
     * Generate some world
     *
     * @param random the chunk specific {@link Random}.
     * @param chunkX the chunk X coordinate of this chunk.
     * @param chunkZ the chunk Z coordinate of this chunk.
     * @param world : additionalData[0] The minecraft {@link World} we're generating for.
     * @param chunkGenerator : additionalData[1] The {@link IChunkProvider} that is generating.
     * @param chunkProvider : additionalData[2] {@link IChunkProvider} that is requesting the world generation.
     *
     */
    public void generate(Random random,
    			  		 int chunkX, int chunkZ, World world,
    			         IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    	if (world.provider.getDimension() != 0) {
    		return;
    	}
    	if (random.nextInt(100) != 0) {
    		return;
    	}
    	int x = chunkX * 16 + random.nextInt(16) + 1;
		int y = 3;
		int z = chunkZ * 16 + random.nextInt(16) + 1;
		int height = random.nextInt(20) + 20;
		for (int h = 0; h < height; h++) {
			for (int X = x - h; X < x + h; X++) {
				for (int Z = z - h; Z < z + h; Z++) {
					world.setBlockState(new BlockPos(X, y + h, Z),
							            Blocks.STONE.getDefaultState());
				}
			}
		}
    }
}

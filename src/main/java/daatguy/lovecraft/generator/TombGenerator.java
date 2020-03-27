package daatguy.lovecraft.generator;

import java.util.Random;

import daatguy.lovecraft.generator.components.TombStructureComponent;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.IWorldGenerator;

public class TombGenerator implements IWorldGenerator {
	
	//Generates tombs
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
	
		switch (world.provider.getDimension()) {

		case -1:
			break;
		case 0:
			if(random.nextInt(64)==0) {
				int x = chunkX*16+random.nextInt(16);
				int y = 100+random.nextInt(8);
				int z = chunkZ*16+random.nextInt(16);
				TombStructureComponent tomb = new TombStructureComponent(random, x, y, z, 5, 9, 9);
				tomb.addComponentParts(world, random, tomb.getBoundingBox());
			}
			
			break;
		case 1:
			break;

		}
		
	}
	
}

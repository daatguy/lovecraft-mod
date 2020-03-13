package daatguy.lovecraft.generator;

import java.util.Random;

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
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
	
		switch (world.provider.getDimension()) {

		case -1:
			break;
		case 0:
			/*
			int x = chunkX * 16 + random.nextInt(16) + 1;
			int y = 90;
			int z = chunkZ * 16 + random.nextInt(16) + 1;
			world.setBlockState(new BlockPos(x, y, z),
					Blocks.CHEST.getDefaultState());
			TileEntityChest chest = (TileEntityChest) world
					.getTileEntity(new BlockPos(x, y, z));
			if (chest != null) {
				chest.setLootTable(new ResourceLocation(
						"lovecraft:chests/tomb"), random.nextLong());
			
			}*/
			
			break;
		case 1:
			break;

		}
		
	}
	
}

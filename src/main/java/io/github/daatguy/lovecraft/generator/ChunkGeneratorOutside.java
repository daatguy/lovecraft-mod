package io.github.daatguy.lovecraft.generator;

import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorFlat;

public class ChunkGeneratorOutside extends ChunkGeneratorFlat{

	public ChunkGeneratorOutside(World worldIn, long seed) {
		super(worldIn, seed, false, "3;100*lovecraft:understructure;1;");
	}

}

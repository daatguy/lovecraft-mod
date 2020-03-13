package daatguy.lovecraft.generator.village;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class VillageOpiumDen extends StructureVillagePieces.Village {

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn,
			StructureBoundingBox structureBoundingBoxIn) {
        this.setBlockState(worldIn, Blocks.END_STONE.getDefaultState(), 0, 0, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.END_STONE.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.END_STONE.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.END_STONE.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.END_STONE.getDefaultState(), 4, 4, 4, structureBoundingBoxIn);
        return true;
	}

}

package io.github.daatguy.mods.minecraft.lovecraft.generator;

import java.util.Random;

import io.github.daatguy.mods.minecraft.lovecraft.block.BlockFlowerDrug;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FlowerGenerator extends WorldGenerator
{
    
	//NOT REALLY A GENERATOR, CALLED BY DecorationGenerator
	
    private BlockBush flower;
    private IBlockState state;

    public FlowerGenerator(BlockBush blockFlowerDrug)
    {
    	this.flower = blockFlowerDrug;
    	this.state = blockFlowerDrug.getDefaultState();
    }

    public void setGeneratedBlock(BlockFlowerDrug flowerIn, BlockFlower.EnumFlowerType typeIn)
    {
        this.flower = flowerIn;
        this.state = flowerIn.getDefaultState();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state))
            {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }

        return true;
    }
}

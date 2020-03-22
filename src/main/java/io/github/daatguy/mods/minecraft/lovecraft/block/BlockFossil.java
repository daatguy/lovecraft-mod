package io.github.daatguy.mods.minecraft.lovecraft.block;

import java.util.List;
import java.util.Random;

import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFossil extends Block {

	private Item item;
	private int amount;
	
	public BlockFossil(Material material, Item item, int amount) {
		
		super(material);
		this.item = item;
		this.amount = amount;
		
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess iworld, BlockPos pos, IBlockState state, int fortune)
    {
		World world;
		if(iworld instanceof World) {
			world = ((World)iworld);
		} else {
			world = null;
		}
        Random rand = iworld instanceof World ? ((World)iworld).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
            	ItemStack fossil = new ItemStack(LovecraftMain.itemFossil);
            	fossil.getItem().onCreated(fossil, world, null);
                drops.add(fossil);
            }
        }
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.item;
    }
	
	@Override
	public int quantityDropped(Random rand) {
		
		if(this.amount==1) return 1;
		
		return 1 + rand.nextInt(this.amount-1);

	}

}

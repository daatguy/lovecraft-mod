package io.github.daatguy.minecraft.mods.lovecraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockSimpleDrop extends Block {

	private Item item;
	private int amount;
	
	public BlockSimpleDrop(Material material, Item item, int amount) {
		
		super(material);
		this.item = item;
		this.amount = amount;
		
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

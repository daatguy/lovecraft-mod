package io.github.daatguy.mods.minecraft.lovecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;

public class BlockUnderstructure extends BlockSimple{

	public BlockUnderstructure(Material material) {
		super(material);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0F);
        this.disableStats();
	}
	
	@Override
	public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
		return false;
	}
	
	@Override
	public boolean canSpawnInBlock() {
		return false;
	}
	
	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return null;
	}

}

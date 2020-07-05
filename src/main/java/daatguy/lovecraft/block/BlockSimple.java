package daatguy.lovecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSimple extends Block {

	public BlockSimple(Material material) {
		
		super(material);
		
	}
	
	
	public BlockSimple(Material material, SoundType soundType) {
		
		super(material);
		this.setSoundType(soundType);
	}


}
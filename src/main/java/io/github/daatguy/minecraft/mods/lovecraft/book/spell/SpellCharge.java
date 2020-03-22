package io.github.daatguy.minecraft.mods.lovecraft.book.spell;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import io.github.daatguy.minecraft.mods.lovecraft.tileentity.TileEntityAltar;

public class SpellCharge extends Spell {

	public SpellCharge() {
		this.obeliskLevel = TileEntityAltar.CARVED_OBELISK;
		this.recipe = new ItemStack[9];
		this.recipe[0] = new ItemStack(Items.ARROW);
		this.recipe[1] = new ItemStack(Items.BEEF);
		this.recipe[2] = new ItemStack(Items.ARROW);
		this.recipe[4] = new ItemStack(Items.ARROW);
		this.recipe[5] = new ItemStack(Items.BEEF);
		this.recipe[6] = new ItemStack(Items.ARROW);
		this.recipe[8] = new ItemStack(Items.APPLE);
		this.name = "charge";
		this.color = 3;
	}

	@Override
	public boolean canCast(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar)world.getTileEntity(pos);
		if (te==null || te.getObeliskType() != TileEntityAltar.CARVED_OBELISK) {
			return false;
		} else {
			return super.canCast(world ,pos);
		}
	}
	
	

	@Override
	public boolean castSpell(World world, BlockPos pos) {
		TileEntityAltar.setToObelisk(pos, world,
				LovecraftMain.blockCarvedObelisk,
				LovecraftMain.blockChargedObelisk, TileEntityAltar.checkForObeliskHeight(world, pos));
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.NEUTRAL,
				1.0F, 1.2F, false);
		return true;
	}
}

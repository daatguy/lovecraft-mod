package io.github.daatguy.minecraft.mods.lovecraft.book.spell;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import io.github.daatguy.minecraft.mods.lovecraft.client.sound.SoundEventHandler;
import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import io.github.daatguy.minecraft.mods.lovecraft.tileentity.TileEntityAltar;

public class SpellCap extends Spell {

	public SpellCap() {
		this.obeliskLevel = TileEntityAltar.CHARGED_OBELISK;
		this.recipe = new ItemStack[9];
		this.recipe[0] = new ItemStack(Items.ARROW);
		this.recipe[1] = new ItemStack(Items.BRICK);
		this.recipe[2] = new ItemStack(Items.ARROW);
		this.recipe[4] = new ItemStack(Items.ARROW);
		this.recipe[5] = new ItemStack(Items.BRICK);
		this.recipe[6] = new ItemStack(Items.ARROW);
		this.recipe[8] = new ItemStack(Items.APPLE);
		this.timer = 26;
		this.color = 2;
		this.name = "cap";
	}

	@Override
	public boolean startCast(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
		if(te==null) {
			return false;
		}
		if (canStart(world, pos)) {
			super.startCast(world, pos);
			world.playSound(pos.getX(), pos.getY(),
					pos.getZ(), SoundEventHandler.SPELL_CAP,
					SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
			return true;
		}
		return false;
	}

	@Override
	public boolean canStart(World world, BlockPos pos) {
		int height = TileEntityAltar.checkForObeliskHeight(world, pos) - 1;
		return (super.canStart(world, pos) && TileEntityAltar.checkForObeliskLayer(world, LovecraftMain.blockObeliskCap, pos.up(height)));
	}
	
	@Override
	public void tickSpell(World world, BlockPos pos, int tick) {
		float tickRatio = (float) tick / (float) this.timer;
		int height = TileEntityAltar.checkForObeliskHeight(world, pos) - 1;
		world.spawnParticle(EnumParticleTypes.END_ROD, pos.getX()+0.5D, pos.getY()
				+ height * particleCurve(tickRatio), pos.getZ()+0.5D, 0.0D, 0.0D,
				0.0D);
	}

	private float particleCurve(float input) {
 		return (float) (0 + 5f * input - 4f * Math.pow(input, 2));
	}

	@Override
	public boolean canCast(World world, BlockPos pos) {
		return TileEntityAltar.checkForObeliskLayer(
				world,
				LovecraftMain.blockObeliskCap,
				new BlockPos(pos.getX(), pos.getY() - 1
						+ TileEntityAltar.checkForObeliskHeight(world, pos),
						pos.getZ()));
	}

	private void obeliskCapParticle(World world, BlockPos pos) {
		for (int i = 0; i < 32; i++) {
			double x = (double) pos.getX() + 0.5D;
			double y = (double) pos.getY() - 1.0D;
			double z = (double) pos.getZ() + 0.5D;
			if (Math.round(Math.random())==1) {
				z += 0.5f * (Math.round(Math.random()) - 0.5D);
				x += 0.6f * ((float) Math.rint(2) * 2.0f - 1.0f);
			} else {
				x += 0.5f * (Math.round(Math.random()) - 0.5D);
				z += 0.6f * ((float) Math.round(Math.random()) * 2.0f - 1.0f);
			}

			world.spawnParticle(EnumParticleTypes.END_ROD, x, y, z,
					0.0D, 0.0D, 0.0D);
		}
	}
	
	private void obeliskCapParticles(World world, BlockPos pos) {
		// The X's and Z's
		for (int j = -1; j < 2; j = j + 2) {
			BlockPos bPos = new BlockPos(pos.getX() + j * 3, pos.getY(),
					pos.getZ());
			obeliskCapParticle(world, bPos);
		}
		for (int j = -1; j < 2; j = j + 2) {
			BlockPos bPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + j
					* 3);
			obeliskCapParticle(world, bPos);
		}
		// The Corner Blocks
		for (int j = -1; j < 2; j = j + 2) {
			for (int k = -1; k < 2; k = k + 2) {
				BlockPos bPos = new BlockPos(pos.getX() + j * 2, pos.getY(),
						pos.getZ() + k * 2);
				obeliskCapParticle(world, bPos);
			}
		}
	}

	@Override
	public boolean castSpell(World world, BlockPos pos) {
		if (this.canCast(world, pos)) {
			TileEntityAltar.setToObelisk(pos, world,
					LovecraftMain.blockObeliskCap,
					LovecraftMain.blockResolvedObeliskCap, TileEntityAltar.checkForObeliskHeight(world, pos));
			int height = TileEntityAltar.checkForObeliskHeight(world, pos);
			for (int i = 0; i < 20; i++) {
				world.spawnParticle(EnumParticleTypes.END_ROD,
						pos.getX()+0.5D, pos.getY() + height, pos.getZ()+0.5D,
						0.1D*Math.random()-0.05D, 0.1D*Math.random()-0.05D, 0.1D*Math.random()-0.05D);
			}
			obeliskCapParticles(world, pos.up(height));
			return true;
		} else {
			return false;
		}
	}
}

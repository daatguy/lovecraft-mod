package io.github.daatguy.minecraft.mods.lovecraft.entity;

import io.github.daatguy.minecraft.mods.lovecraft.book.spell.Spell;
import io.github.daatguy.minecraft.mods.lovecraft.book.spell.SpellHandler;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import io.github.daatguy.minecraft.mods.lovecraft.tileentity.TileEntityAltar;

public class EntitySpell extends Entity {

	public Spell spell;
	public TileEntityAltar tile;
	private boolean finishedCasting = false;

	/**
	 * Entity placeholder for drawing spell-related effects, doing spell things
	 */
	public EntitySpell(World worldIn) {
		super(worldIn);
		this.spell = null;
		this.setInvisible(true);
		this.setNoGravity(true);
	}

	public EntitySpell(World worldIn, Spell spell, TileEntityAltar tile) {
		super(worldIn);
		this.spell = spell;
		this.setPositionAndUpdate(tile.getPos().getX(), tile.getPos().getY(),
				tile.getPos().getZ());
		this.spell.startCast(this.getEntityWorld(), this.getPosition());
		this.setInvisible(true);
		this.setNoGravity(true);
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	public boolean getIsInvulnerable() {
		return true;
	}

	@Override
	public boolean isInvisible() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onEntityUpdate();
		// System.out.println(this.ticksExisted);
		if (finishedCasting) {
			setDead();
		} else if (this.ticksExisted >= this.spell.timer) {
			this.spell.castSpell(this.getEntityWorld(), this.getPosition());
			finishedCasting = true;
		} else {
			this.spell.tickSpell(this.getEntityWorld(), this.getPosition(),
					this.ticksExisted);
		}
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setString("Spell", this.spell.name);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.spell = SpellHandler.spells.get(compound.getString("Spell"));
	}

	@Override
	protected void entityInit() {
	}
}

package io.github.daatguy.minecraft.mods.lovecraft.book.spell;

import io.github.daatguy.minecraft.mods.lovecraft.book.CanGetStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import io.github.daatguy.minecraft.mods.lovecraft.tileentity.TileEntityAltar;

public class Spell implements CanGetStack {

	public int obeliskLevel;
	public ItemStack[] recipe;
	public String description;
	public boolean closesAltar;
	public int timer;
	public String name;
	public int color;
	public int genWeight;

	public Spell() {
		this.obeliskLevel = 0;
		this.recipe = new ItemStack[9];
		this.closesAltar = true;
		this.timer = 0;
		this.name = "";
		this.color = 0;
		this.genWeight = 0;
	}

	/**
	 * Called when the spell timer starts.
	**/
	public boolean startCast(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
		if(te==null) {
			return false;
		}
		if (canStart(world, pos)) {
			for (int i = 0; i < 9; i++) {
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
						null).extractItem(i + 1, 1, false);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Called every tick that the spell is being cast
	**/
	public void tickSpell(World world, BlockPos pos, int tick) {
		
	}
	
	/**
	 * Returns whether or not the spell can start to be casted with the given parameters.
	**/
	public boolean canStart(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
		if(te==null) {
			return false;
		}
		// By default, canCast uses only the obelisk level and the recipe as a
		// requirement
		// If the obelisk doesn't have the required level
		if (te.getObeliskType() < this.obeliskLevel) {
			return false;
		}
		for (int i = 0; i < 9; i++) {
			
			// If the recipe item is null, but the altar slot isn't empty,
			// return false
			if (this.recipe[i] == null
					&& !te.getCapability(
							CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
							.getStackInSlot(i + 1).isEmpty()) {
				return false;
				// If the recipe item and the altar slot item don't match up
			} else if (this.recipe[i] != null
					&& te.getCapability(
							CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
							.getStackInSlot(i + 1).isEmpty()) {
				return false;
				// If the recipe item and the altar slot item don't match up
			} else if (!te
					.getCapability(
							CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
					.getStackInSlot(i + 1).isEmpty()
					&& (!ItemStack
							.areItemStackTagsEqual(
									te.getCapability(
											CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
											null).getStackInSlot(i + 1),
									recipe[i]) || te
							.getCapability(
									CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
									null).getStackInSlot(i + 1).getItem() != recipe[i]
							.getItem())) {
				return false;
			}
		}

		// If all else failed at failing this function, we can assume the altar
		// is filled correctly.
		return true;
	}

	/**
	 * Returns whether or not the spell can be cast with the given parameters.
	**/
	public boolean canCast(World world, BlockPos pos) {
		return true;
	}
	
	// As this is the base class, all this function does is remove the used
	// items from the altar slots.
	/**
	 * Returns whether or not the spell was successful.
	 */
	public boolean castSpell(World world, BlockPos pos) {
		return true;
	}
	
	/**
	 * Returns an itemStack of 'lovecraft:tome' with the associated spell
	 */
	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(LovecraftMain.itemTome, 1, this.color);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Spell", this.name);
		item.setTagCompound(nbt);
		return item;
	}

}

package daatguy.lovecraft.container;

import daatguy.lovecraft.book.spell.Spell;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotAltar extends SlotItemHandler {

	public TileEntityAltar tile;
	public int id;

	public SlotAltar(IItemHandler itemHandler, int index, int xPosition,
			int yPosition, TileEntityAltar tile, int id) {
		super(itemHandler, index, xPosition, yPosition);
		this.tile = tile;
		this.id = id;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		
		IItemHandler inventory = this.tile.getCapability(
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (inventory.getStackInSlot(0).isEmpty()) {
			return false;
		} else {
			Spell spell = LovecraftMain.spellHandler.spells.get(inventory
					.getStackInSlot(0).getTagCompound().getString("Spell"));
			if (spell.recipe[this.id]!=null && stack.getItem() == spell.recipe[this.id].getItem()) {
				return true;
			} else {
				return false;
			}
		}
	}

}

package daatguy.lovecraft.book.spell;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;

public class SpellDisassemble extends Spell {

	public SpellDisassemble() {
		this.obeliskLevel = TileEntityAltar.SIMPLE_OBELISK;
		this.recipe = new ItemStack[9];
		this.recipe[8] = new ItemStack(Items.STRING);
		this.name = "disassemble";
		this.closesAltar = false;
		this.color = 14;
		//Make this only gotten from a villager trade
	}

	@Override
	public boolean canStart(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
		if (te == null || te.checkForObeliskHeight(world, pos) > 64) {
			return false;
		} else {
			return super.canCast(world, pos);
		}
	}

	private void setItems(TileEntityAltar altar, Item itemObelisk, int height) {
		if (altar
				.getCapability(
						CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
				.getStackInSlot(9).isEmpty()) {
			altar.getCapability(
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
					.insertItem(
							9,
							new ItemStack(LovecraftMain.blockObeliskCap, 8),
							false);
		}
		for (int i = 8; i > 0; i--) {
			if (altar
					.getCapability(
							CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
					.getStackInSlot(i).isEmpty()) {
				altar.getCapability(
						CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
						.insertItem(
								i,
								new ItemStack(itemObelisk, height - 1),
								false);
			}
		}
	}

	@Override
	public boolean startCast(World world, BlockPos pos) {
		super.startCast(world, pos);
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvents.ENTITY_SHULKER_CLOSE, SoundCategory.NEUTRAL, 1.0F,
				0.8F, false);
		if(world.isRemote) return false;
		//System.out.println("casted");
		TileEntityAltar altar = ((TileEntityAltar) world.getTileEntity(pos));
		if (altar == null)
			return false;
		int type = altar.getObeliskType();
		int height = altar.checkForObeliskHeight(world, pos);
		if (type >= TileEntityAltar.CHARGED_OBELISK) {
			TileEntityAltar.setToObelisk(pos, world,
					LovecraftMain.blockChargedObelisk, Blocks.AIR, height);
			setItems(altar, LovecraftMain.itemBlockCarvedObelisk, height);
		} else if (type >= TileEntityAltar.CARVED_OBELISK) {
			TileEntityAltar.setToObelisk(pos, world,
					LovecraftMain.blockCarvedObelisk, Blocks.AIR, height);
			setItems(altar, LovecraftMain.itemBlockCarvedObelisk, height);
		} else if (type == TileEntityAltar.SIMPLE_OBELISK) {
			TileEntityAltar.setToObelisk(pos, world,
					LovecraftMain.blockObelisk, Blocks.AIR, height);
			setItems(altar, LovecraftMain.itemBlockObelisk, height);
		}
		TileEntityAltar.setToObelisk(pos, world, LovecraftMain.blockObeliskCap,
				Blocks.AIR, height);
		TileEntityAltar.setToObelisk(pos, world,
				LovecraftMain.blockResolvedObeliskCap, Blocks.AIR, height);
		return true;
	}
}

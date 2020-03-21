package daatguy.lovecraft.book.spell;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;
import daatguy.lovecraft.worlds.RoomTeleporter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class SpellEnterRoom extends Spell {
	
	private ITeleporter teleporter = new RoomTeleporter();
	
	public SpellEnterRoom() {
		this.obeliskLevel = TileEntityAltar.CHARGED_OBELISK;
		this.recipe = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			this.recipe[i] = new ItemStack(Blocks.GOLD_ORE);
		}
		this.name = "enter_room";
		this.color = 6;
		this.timer = 2;
	}
	
	@Override
	public boolean canCast(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar)world.getTileEntity(pos);
		if (te==null || te.getObeliskType() != TileEntityAltar.CHARGED_OBELISK) {
			return false;
		} else {
			return super.canCast(world, pos);
		}
	}
	
	@Override
	public boolean castSpell(World world, BlockPos pos) {
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvents.ENTITY_GUARDIAN_AMBIENT, SoundCategory.NEUTRAL,
				1.0F, 1.2F, false);
		for (EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class,
				                                              new AxisAlignedBB(pos.getX() - 2,
				                                            		            pos.getY() - 2,
				                                            		            pos.getZ() - 2,
				                                            		            pos.getX() + 2,
				                                            		            pos.getY() + 2,
				                                            		            pos.getZ() + 2))) {
			if (!world.isRemote && !e.isRiding() && !e.isBeingRidden() && e.isNonBoss()) {
				e.changeDimension(LovecraftMain.ROOM_DIM_ID, teleporter);
			}
		}
		return true;
	}
	
}

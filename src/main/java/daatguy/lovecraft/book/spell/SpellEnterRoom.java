package daatguy.lovecraft.book.spell;

import java.util.List;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.tileentity.TileEntityAltar;
import daatguy.lovecraft.world.RoomTeleporter;
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
		this.closesAltar = true;
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
		startCast(world, pos);
		if (world.isRemote) { //doesn't fix crash but smart anyways
			return false;
		}
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvents.ENTITY_GUARDIAN_AMBIENT, SoundCategory.NEUTRAL,
				1.0F, 1.2F, false);
		List<EntityLivingBase> entitiesInRange = world.getEntitiesWithinAABB(EntityLivingBase.class,
                															 new AxisAlignedBB(pos.getX() - 2,
                														                       pos.getY() - 2,
                														                       pos.getZ() - 2,
                														                       pos.getX() + 2,
                														                       pos.getY() + 2,
                														                       pos.getZ() + 2));
		for (EntityLivingBase e : entitiesInRange) {
			if (!e.isDead && !e.isRiding() && !e.isBeingRidden() && e.isNonBoss()) {
				e.changeDimension(LovecraftMain.ROOM_DIM_ID, teleporter);
			}
		}
		return true;
	}
	
}

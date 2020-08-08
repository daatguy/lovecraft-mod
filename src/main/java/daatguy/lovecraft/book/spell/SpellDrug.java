package daatguy.lovecraft.book.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.ItemBeaker;
import daatguy.lovecraft.tileentity.TileEntityAltar;

public class SpellDrug extends Spell {

	public SpellDrug() {
		this.obeliskLevel = TileEntityAltar.CARVED_OBELISK;
		this.recipe = new ItemStack[9];
		this.recipe[0] = new ItemStack(LovecraftMain.itemDriedFlower);
		this.recipe[1] = new ItemStack(LovecraftMain.itemMummyDust);
		this.recipe[2] = new ItemStack(LovecraftMain.itemDriedFlower);
		this.recipe[4] = new ItemStack(LovecraftMain.itemDriedFlower);
		this.recipe[5] = new ItemStack(LovecraftMain.itemMummyDust);
		this.recipe[6] = new ItemStack(LovecraftMain.itemDriedFlower);
		this.recipe[8] = new ItemStack(Items.BOWL);
		this.name = "drug";
		this.color = 10;
	}

	@Override
	public boolean canCast(World world, BlockPos pos) {
		TileEntityAltar te = (TileEntityAltar) world.getTileEntity(pos);
		if (te == null
				|| te.getObeliskType() != TileEntityAltar.SIMPLE_OBELISK
				|| !(world.canSeeSky(pos)
						&& world.getCurrentMoonPhaseFactor() == 1.0f
						&& world.getWorldTime() > 13000 && world.getWorldTime() < 23000)) {
			return false;
		} else {
			return super.canCast(world, pos);
		}
	}

	@Override
	public boolean castSpell(World world, BlockPos pos) {
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvents.ENTITY_ENDERMEN_STARE, SoundCategory.NEUTRAL,
				1.0F, 0.2F, false);
		for(EntityPlayer player : world.playerEntities) {
			if(player.getDistance(pos.getX(), pos.getY(), pos.getZ())<10f) {
				//player.addPotionEffect(new PotionEffect(LovecraftMain.potionDread, 6000, 1, false, false));
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 1));
			}
		}
		return true;
	}
}

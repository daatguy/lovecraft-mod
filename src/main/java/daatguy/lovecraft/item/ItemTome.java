package daatguy.lovecraft.item;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.book.spell.SpellHandler;
import daatguy.lovecraft.core.LovecraftMain;

public class ItemTome extends ItemSimple {

	private static int loreLineThreshhold = 25;
	
	public ItemTome() {
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		if (stack.hasTagCompound()) {
			return "spell." + stack.getTagCompound().getString("Spell");
		} else {
			return "spell.empty";
		}

	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for(int i=0; i<20; i++) {
			ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName()+"_"+String.valueOf(i), "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		//super.addInformation(stack, worldIn, tooltip, flagIn);
		String rawLore[] = I18n.format("spell."+stack.getTagCompound()
				.getString("Spell")+".description").split("\\s+");
		String curLine = "";
		for(String word : rawLore) {
			curLine = curLine + word + " ";
			if(curLine.length()>loreLineThreshhold) {
				tooltip.add(curLine.trim());
				curLine = "";
			}
		}
		if(curLine!="") {
			tooltip.add(curLine.trim());
		}
		tooltip.add("");
		tooltip.add(TextFormatting.LIGHT_PURPLE + I18n.format("spell.altar_ready"));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.getCreativeTab() == tab) {
			for (String key : LovecraftMain.spellHandler.spells.keySet()) {
				items.add(LovecraftMain.spellHandler.spells.get(key).getItemStack());
			}
		}
	}

	// @SideOnly(Side.CLIENT)

}

package daatguy.lovecraft.container;

import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class LovecraftTab extends CreativeTabs{

	public LovecraftTab() {
		super("tabLovecraft");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(LovecraftMain.itemBlockAltar);
	}

}

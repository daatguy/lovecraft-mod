package daatguy.lovecraft.item;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.interfaces.ItemDescription;

public class ItemFossil extends ItemSimple implements ItemDescription {

	public ItemFossil() {
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		if (stack.hasTagCompound()) {
			return "fossil." + stack.getTagCompound().getString("Fossil");
		} else {
			return "fossil.empty";
		}

	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for(int i=0; i<LovecraftMain.subItemsHandler.fossils.size(); i++) {
			ModelResourceLocation mrl = new ModelResourceLocation(getRegistryName()+"_"+String.valueOf(i), "inventory");
			ModelLoader.setCustomModelResourceLocation(this, i, mrl);
		}
	}
	
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		int index = worldIn.rand.nextInt(LovecraftMain.subItemsHandler.fossils
				.size());
		String name = LovecraftMain.subItemsHandler.fossils.get(index);
		stack.setItemDamage(index);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Fossil", name);
		stack.setTagCompound(nbt);
	}	
	
	public static ItemStack getItemStack(int index) {
		ItemStack item = new ItemStack(LovecraftMain.itemFossil, 1, index);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Fossil", SubItemsHandler.fossils.get(index));
		item.setTagCompound(nbt);
		return item;
	}
	
	public ItemStack getItemStack(int index, int size) {
		ItemStack item = getItemStack(index);
		item.setCount(size);
		return item;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		addDescriptionTooltip(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.getCreativeTab() == tab) {
			int i = 0;
			for (String name : LovecraftMain.subItemsHandler.fossils) {
				ItemStack newItem = new ItemStack(this, 1, i);
				i++;
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("Fossil", name);
				newItem.setTagCompound(nbt);
				items.add(newItem);
			}
		}
	}

	// @SideOnly(Side.CLIENT)

}

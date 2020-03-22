package io.github.daatguy.mods.minecraft.lovecraft.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBeaker extends ItemSimple {
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BeakerContents")) {
			NBTTagList listnbt = (NBTTagList) stack.getTagCompound().getTag(
					"BeakerContents");
			if(listnbt.tagCount()<2) {
				String dissolvedItem = ((NBTTagCompound)listnbt.get(0)).getString("Item");
				String dissolvedName = new ItemStack(Item.getByNameOrId(dissolvedItem)).getDisplayName();
				return I18n.format("item.beaker.suspension_filled").replace("*i", dissolvedName);
			} else {
				return I18n.format("item.beaker.suspension_mixed");
			}
		} else {
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn,
			List<String> tooltip, ITooltipFlag flagIn) {
		// super.addInformation(stack, worldIn, tooltip, flagIn);
			
		//System.out.println(stack.getTagCompound().toString());
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BeakerContents")) {
			NBTTagList listnbt = (NBTTagList) stack.getTagCompound().getTag(
					"BeakerContents");
			tooltip.add(TextFormatting.AQUA+I18n.format("item.beaker.suspension_tooltip_a"));
			for (int i = 0; i < listnbt.tagCount(); i++) {
				NBTTagCompound subnbt = ((NBTTagCompound) listnbt.get(i));
				tooltip.add(TextFormatting.DARK_AQUA+(I18n.format("item.beaker.suspension_tooltip_b")
						.replace("*p", Float.toString(Math.round(subnbt
								.getFloat("Percent") * 10) / 10.0f))).replace(
						"*i", Item.getByNameOrId(subnbt.getString("Item")).getItemStackDisplayName(new ItemStack(Item.getByNameOrId(subnbt.getString("Item"))))));
				
			}
		} else {
			if (!I18n
					.format(((Item) this).getUnlocalizedName() + ".description")
					.equals(((Item) this).getUnlocalizedName() + ".description")) {
				String rawLore[] = I18n.format(
						((Item) this).getUnlocalizedName() + ".description")
						.split("\\s+");
				String curLine = "";
				for (String word : rawLore) {
					curLine = curLine + word + " ";
					if (curLine.length() > loreLineThreshhold) {
						tooltip.add(curLine.trim());
						curLine = "";
					}
				}
				if (curLine != "") {
					tooltip.add(curLine.trim());
				}
			}
		}
	}

	/**
	 * Returns an beaker itemStack after dissolving 'item' into it at 'percent'
	 */
	public static ItemStack addSuspension(ItemStack beakerStack, Item item,
			float percent) {
		// {BeakerContents:[{name:"stuff",amount:2},{name:"rob",amount:3]}
		if (beakerStack.hasTagCompound() && beakerStack.getTagCompound().hasKey("BeakerContents")
				&& beakerStack.getTagCompound().getTag("BeakerContents") instanceof NBTTagList) {
			NBTTagList listnbt = (NBTTagList) beakerStack.getTagCompound()
					.getTag("BeakerContents");
			NBTTagCompound subnbt = NBTContentsContains(listnbt,
					item.getRegistryName().toString());
			// If this item doesn't have an associated nbt compound in our list
			// of items
			if (subnbt == null) {
				subnbt = new NBTTagCompound();
				subnbt.setString("Item", item.getRegistryName().toString());
				subnbt.setFloat("Percent", percent);
				listnbt.appendTag(subnbt);
			} else {
				// If it does have an associated nbt compound in our list of
				// items
				if (subnbt.hasKey("Percent")) {
					// Sanity check for the key 'Percent'
					subnbt.setFloat("Percent",
							percent + subnbt.getFloat("Percent"));
				} else {
					subnbt.setFloat("Percent", percent);
				}
			}
		} else {
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagList listnbt = new NBTTagList();
			NBTTagCompound subnbt = new NBTTagCompound();

			subnbt.setString("Item", item.getRegistryName().toString());
			subnbt.setFloat("Percent", percent);

			listnbt.appendTag(subnbt);
			nbt.setTag("BeakerContents", listnbt);
			beakerStack.setTagCompound(nbt);
		}
		return beakerStack;
	}

	/**
	 * Searches an NBTTagList for a given compound with key 'Item' matching the
	 * provided name
	 * 
	 * @param nbt
	 *            The NBTTagList compound to be searched
	 * @param name
	 *            The unlocalized name of the item to search for
	 * @return the index of the matching NBTTagCompound
	 */
	private static NBTTagCompound NBTContentsContains(NBTTagList nbt,
			String name) {
		for (int i = 0; i < nbt.tagCount(); i++) {
			if (((NBTTagCompound) nbt.get(i)).getString("Item").equals(name)) {
				return (NBTTagCompound) nbt.get(i);
			}
		}
		return null;
	}
}

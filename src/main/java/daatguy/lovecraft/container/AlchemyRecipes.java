package daatguy.lovecraft.container;

import java.util.ArrayList;

import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class AlchemyRecipes {

	public class Recipe {
		
		public Item input;
		public ItemStack[] output;
		
		public Recipe(Item input, ItemStack[] output) {
			this.input = input;
			this.output = output;
		}
		
		public Recipe(Item input, ItemStack output) {
			this.input = input;
			ItemStack[] o = new ItemStack[1];
			o[0] = output;
			this.output = o;
		}
		
		public boolean TryCraft(EntityPlayer player, EnumHand hand) {
			if(player.getHeldItem(hand).getItem().equals(input)) {
				player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount()-1);
				for(ItemStack stack : output) {
					player.inventory.addItemStackToInventory(stack.copy());
				}
				return true;
			}
			return false;
		}
		
	}
	
	public ArrayList<Recipe> MORTAR_RECIPES = new ArrayList<Recipe>();
	
	public void InitRecipes() {
		MORTAR_RECIPES.add(new Recipe(Items.BONE, new ItemStack(Items.DYE, 4, 15)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemWeirdShards, new ItemStack(LovecraftMain.itemWeirdDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemFossil, new ItemStack(LovecraftMain.itemFossilDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemMummyChunk, new ItemStack(LovecraftMain.itemMummyDust, 2)));
		
	}
	
}

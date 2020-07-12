package daatguy.lovecraft.container;

import java.util.ArrayList;

import daatguy.lovecraft.container.recipe.RecipeSolution;
import daatguy.lovecraft.core.LovecraftMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class AlchemyRecipes {

	public int recipeIdStart = 4000;
	
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
	public ArrayList<Item> SOLUTION_ITEMS = new ArrayList<Item>();
	
	public void InitRecipes() {
		MORTAR_RECIPES.add(new Recipe(Items.BONE, new ItemStack(Items.DYE, 4, 15)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemWeirdShards, new ItemStack(LovecraftMain.itemWeirdDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemFossil, new ItemStack(LovecraftMain.itemFossilDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemMummyChunk, new ItemStack(LovecraftMain.itemMummyDust, 2)));
		//MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemFleshChunk, new ItemStack(LovecraftMain.itemFleshDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemSpoiledFleshChunk, new ItemStack(LovecraftMain.itemSpoiledFleshDust, 2)));
		MORTAR_RECIPES.add(new Recipe(LovecraftMain.itemPreservedFleshChunk, new ItemStack(LovecraftMain.itemPreservedFleshDust, 2)));
		
		SOLUTION_ITEMS.add(LovecraftMain.itemWeirdDust);
		SOLUTION_ITEMS.add(LovecraftMain.itemFossilDust);
		SOLUTION_ITEMS.add(LovecraftMain.itemMummyDust);
		ForgeRegistries.RECIPES.register(new RecipeSolution().setRegistryName("lovecraft", "beakersolution"));
		//CraftingManager.REGISTRY.register(recipeIdStart++, new ResourceLocation("lovecraft:beakersolution"), new RecipeSolution());
	}
	
	
}

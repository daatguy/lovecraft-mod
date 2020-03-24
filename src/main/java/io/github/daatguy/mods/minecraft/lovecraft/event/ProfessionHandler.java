package io.github.daatguy.mods.minecraft.lovecraft.event;

import java.util.Random;

import io.github.daatguy.mods.minecraft.lovecraft.book.spell.SpellHandler;
import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import io.github.daatguy.mods.minecraft.lovecraft.item.ItemBook;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

//@ObjectHolder("lovecraft")
public class ProfessionHandler {

	public static VillagerProfession professionOpiumPeddler;
	public static VillagerCareer careerOpiumPeddler;

	@EventBusSubscriber(modid = "lovecraft")
	public static class RegistrationHandler {
		/**
		 * Register this mod's {@link VillagerProfession}s.
		 *
		 * @param event
		 *            The event
		 */
		@SubscribeEvent
		public static void onEvent(
				RegistryEvent.Register<VillagerProfession> event) {
			professionOpiumPeddler = new VillagerProfession(
					"lovecraft:opium_profession",
					"lovecraft:textures/entity/villager/opium_peddler.png",
					"lovecraft:textures/entity/villager/zombie_opium_peddler.png");
			event.getRegistry().register(professionOpiumPeddler);
		}
	}

	/**
	 * Associate careers and trades.
	 */
	public static void associateCareersAndTrades() {
		careerOpiumPeddler = new VillagerCareer(professionOpiumPeddler,
				"opium_career");
		careerOpiumPeddler.addTrade(1, new TradeSell(new ItemStack(
				LovecraftMain.itemEmptyBeaker), new PriceInfo(16, 32)));
		careerOpiumPeddler.addTrade(1, new TradeBuy(new ItemStack(
				LovecraftMain.itemDriedFlower), new PriceInfo(4, 7)));
		careerOpiumPeddler.addTrade(2,
				new Trade(new ItemStack(LovecraftMain.itemCoin), new PriceInfo(
						1, 3), ItemBook.getItemStack("alchemy_dict"),
						new PriceInfo(1, 1)));
		careerOpiumPeddler.addTrade(3,
				new Trade(new ItemStack(LovecraftMain.itemCoin), new PriceInfo(
						4, 8), SpellHandler.spells.get("drug").getItemStack(),
						new PriceInfo(1, 1)));
	}

	public static class Trade implements ITradeList {

		public ItemStack stack;
		public ItemStack sellStack;
		public EntityVillager.PriceInfo priceInfo;
		public EntityVillager.PriceInfo sellPriceInfo;

		public Trade(ItemStack stack, PriceInfo priceInfo, ItemStack sellStack,
				PriceInfo sellPriceInfo) {
			this.stack = stack;
			this.priceInfo = priceInfo;
			this.sellStack = sellStack;
			this.sellPriceInfo = sellPriceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant,
				MerchantRecipeList recipeList, Random random) {
			int actualPrice = 1;
			if (priceInfo != null) {
				actualPrice = priceInfo.getPrice(random);
			}
			int actualSellPrice = 1;
			if (sellPriceInfo != null) {
				actualSellPrice = sellPriceInfo.getPrice(random);
			}
			ItemStack stackToPay = stack.copy();
			stackToPay.setCount(actualPrice);
			ItemStack stackToReceive = sellStack.copy();
			stackToReceive.setCount(actualSellPrice);
			recipeList.add(new MerchantRecipe(stackToPay, stackToReceive));
		}

	}

	public static class TradeBuy implements ITradeList {

		public ItemStack stack;
		public EntityVillager.PriceInfo priceInfo;

		public TradeBuy(ItemStack stack, PriceInfo priceInfo) {
			this.stack = stack;
			this.priceInfo = priceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant,
				MerchantRecipeList recipeList, Random random) {
			int actualPrice = 1;
			if (priceInfo != null) {
				actualPrice = priceInfo.getPrice(random);
			}
			ItemStack stackToPay = new ItemStack(Items.EMERALD, actualPrice, 0);
			recipeList.add(new MerchantRecipe(stackToPay, stack));
		}

	}

	public static class TradeSell implements ITradeList {

		public ItemStack stack;
		public EntityVillager.PriceInfo priceInfo;

		public TradeSell(ItemStack stack, PriceInfo priceInfo) {
			this.stack = stack;
			this.priceInfo = priceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant,
				MerchantRecipeList recipeList, Random random) {
			int actualPrice = 1;
			if (priceInfo != null) {
				actualPrice = priceInfo.getPrice(random);
			}
			ItemStack stackToPay = stack.copy();
			stackToPay.setCount(actualPrice);
			ItemStack stackToReceive = new ItemStack(Items.EMERALD, 1, 0);
			recipeList.add(new MerchantRecipe(stackToPay, stackToReceive));
		}

	}
}
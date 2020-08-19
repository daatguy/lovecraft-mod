package daatguy.lovecraft.event;

import java.util.Random;

import daatguy.lovecraft.book.spell.SpellHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.ItemBook;
import daatguy.lovecraft.item.SubItemsHandler;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

//@ObjectHolder("lovecraft")
public class ProfessionHandler {

	public static VillagerProfession professionOpiumPeddler;
	public static VillagerProfession professionMailman;
	public static VillagerCareer careerOpiumPeddler;
	public static VillagerCareer careerMailman;

	@EventBusSubscriber(modid = "lovecraft")
	public static class MailmanHandler {

		@SubscribeEvent
		public static void onVillagerInteract(
				PlayerInteractEvent.EntityInteractSpecific event) {
			if (event.getTarget() instanceof EntityVillager
					&& ((EntityVillager) event.getTarget())
							.getProfessionForge() == professionMailman) {
				event.getTarget().setFire(1);
			}
		}
	}

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
			professionMailman = new VillagerProfession(
					"lovecraft:mailman_profession",
					"lovecraft:textures/entity/villager/mailman.png",
					"lovecraft:textures/entity/villager/zombie_mailman.png");
			event.getRegistry().register(professionOpiumPeddler);
			event.getRegistry().register(professionMailman);
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
		careerMailman = new VillagerCareer(professionMailman, "mailman_career");
		// careerOpiumPeddler.addTrade(2,
		// new Trade(new ItemStack(LovecraftMain.itemCoin), new PriceInfo(
		// 1, 3), ItemBook.getItemStack("alchemy_dict"),
		// new PriceInfo(1, 1)));
		// careerOpiumPeddler.addTrade(3,
		// new Trade(new ItemStack(LovecraftMain.itemCoin), new PriceInfo(
		// 4, 8), SpellHandler.spells.get("drug").getItemStack(),
		// new PriceInfo(1, 1)));
		// Add librarian trades

		VillagerRegistry
				.getById(1)
				.getCareer(0)
				.addTrade(
						1,
						new Trade(new ItemStack(Items.EMERALD), new PriceInfo(
								4, 8), SubItemsHandler.books.get("latin_dict")
								.getItemStack(), new PriceInfo(1, 1)));
		VillagerRegistry
				.getById(1)
				.getCareer(0)
				.addTrade(
						2,
						new Trade(new ItemStack(Items.EMERALD), new PriceInfo(
								4, 8), SubItemsHandler.books.get("greek_dict")
								.getItemStack(), new PriceInfo(1, 1)));
		VillagerRegistry
				.getById(1)
				.getCareer(0)
				.addTrade(
						5,
						new Trade(new ItemStack(Items.EMERALD), new PriceInfo(
								4, 8), SubItemsHandler.books.get(
								"sanskrit_dict").getItemStack(), new PriceInfo(
								1, 1)));
		VillagerRegistry
				.getById(1)
				.getCareer(0)
				.addTrade(
						6,
						new Trade(new ItemStack(Items.EMERALD), new PriceInfo(
								4, 8), SubItemsHandler.books.get("runic_dict")
								.getItemStack(), new PriceInfo(1, 1)));
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
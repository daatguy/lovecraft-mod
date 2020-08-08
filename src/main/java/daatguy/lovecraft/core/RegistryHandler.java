package daatguy.lovecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import daatguy.lovecraft.item.ItemBook;
import daatguy.lovecraft.item.ItemFossil;
import daatguy.lovecraft.item.ItemTome;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
    public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
		//event.getRegistry().register(LovecraftMain.potionDread);
		//event.getRegistry().register(LovecraftMain.potionAwake);
		event.getRegistry().register(LovecraftMain.potionDrugged);
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(LovecraftMain.itemRubbingKit);
		event.getRegistry().register(LovecraftMain.itemEmptyBeaker);
		event.getRegistry().register(LovecraftMain.itemBeaker);
		event.getRegistry().register(LovecraftMain.itemCoin);
		event.getRegistry().register(LovecraftMain.itemWeirdShards);
		event.getRegistry().register(LovecraftMain.itemWeirdDust);
		event.getRegistry().register(LovecraftMain.itemFossilDust);
		event.getRegistry().register(LovecraftMain.itemMummyDust);
		event.getRegistry().register(LovecraftMain.itemFleshDust);
		event.getRegistry().register(LovecraftMain.itemMummyChunk);
		event.getRegistry().register(LovecraftMain.itemFleshChunk);
		event.getRegistry().register(LovecraftMain.itemSpoiledFleshChunk);
		event.getRegistry().register(LovecraftMain.itemSpoiledFleshDust);
		event.getRegistry().register(LovecraftMain.itemPreservedFleshChunk);
		event.getRegistry().register(LovecraftMain.itemPreservedFleshDust);
		event.getRegistry().register(LovecraftMain.itemMagnifyingGlass);
		event.getRegistry().register(LovecraftMain.itemTome);
		event.getRegistry().register(LovecraftMain.itemBook);
		event.getRegistry().register(LovecraftMain.itemFossil);
		event.getRegistry().register(LovecraftMain.itemFossilKnife);
		event.getRegistry().register(LovecraftMain.itemDriedFlower);
		event.getRegistry().register(LovecraftMain.itemRubbing);
		event.getRegistry().register(LovecraftMain.itemTokenBat);
		event.getRegistry().register(LovecraftMain.itemTokenDread);
		event.getRegistry().register(LovecraftMain.itemTokenAwake);
		event.getRegistry().register(LovecraftMain.itemTokenBrave);

		event.getRegistry().register(LovecraftMain.itemBlockUnderstructure);
		event.getRegistry().register(LovecraftMain.itemBlockFlowerDrug);
		event.getRegistry().register(LovecraftMain.itemBlockFossil);
		//event.getRegistry().register(LovecraftMain.itemBlockAetherOre);
		event.getRegistry().register(LovecraftMain.itemBlockWeirdedBrick);
		event.getRegistry().register(LovecraftMain.itemBlockBasRelief);
		event.getRegistry().register(LovecraftMain.itemBlockHookah);
		event.getRegistry().register(LovecraftMain.itemBlockAltar);
		event.getRegistry().register(LovecraftMain.itemBlockMortar);
		event.getRegistry().register(LovecraftMain.itemBlockDesk);
		event.getRegistry().register(LovecraftMain.itemBlockObelisk);
		event.getRegistry().register(LovecraftMain.itemBlockCarvedObelisk);
		event.getRegistry().register(LovecraftMain.itemBlockChargedObelisk);
		event.getRegistry().register(LovecraftMain.itemBlockObeliskCap);
		event.getRegistry().register(LovecraftMain.itemBlockResolvedObeliskCap);
		event.getRegistry().register(LovecraftMain.itemBlockCarvedStonebrick);
		event.getRegistry().register(LovecraftMain.itemBlockCarvedSandstone);
		event.getRegistry().register(LovecraftMain.itemBlockCarvedWeirdedBrick);
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {

		event.getRegistry().register(LovecraftMain.blockUnderstructure);
		event.getRegistry().register(LovecraftMain.blockFlowerDrug);
		event.getRegistry().register(LovecraftMain.blockFossil);
		//event.getRegistry().register(LovecraftMain.blockAetherOre);
		event.getRegistry().register(LovecraftMain.blockWeirdedBrick);
		event.getRegistry().register(LovecraftMain.blockBasRelief);
		event.getRegistry().register(LovecraftMain.blockHookah);
		event.getRegistry().register(LovecraftMain.blockAltar);
		event.getRegistry().register(LovecraftMain.blockMortar);
		event.getRegistry().register(LovecraftMain.blockDesk);
		event.getRegistry().register(LovecraftMain.blockObelisk);
		event.getRegistry().register(LovecraftMain.blockCarvedObelisk);
		event.getRegistry().register(LovecraftMain.blockChargedObelisk);
		event.getRegistry().register(LovecraftMain.blockObeliskCap);
		event.getRegistry().register(LovecraftMain.blockResolvedObeliskCap);
		event.getRegistry().register(LovecraftMain.blockCarvedStonebrick);
		event.getRegistry().register(LovecraftMain.blockCarvedSandstone);
		event.getRegistry().register(LovecraftMain.blockCarvedWeirdedBrick);
	}


	/**
	 * ModelRegistryEvent
	 * Here is where we register all the item models
	 */
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemDriedFlower, 0, new ModelResourceLocation(
						LovecraftMain.itemDriedFlower.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemFossilKnife, 0, new ModelResourceLocation(
						LovecraftMain.itemFossilKnife.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemRubbingKit, 0, new ModelResourceLocation(
						LovecraftMain.itemRubbingKit.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemEmptyBeaker, 0, new ModelResourceLocation(
						LovecraftMain.itemEmptyBeaker.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBeaker, 0, new ModelResourceLocation(
						LovecraftMain.itemBeaker.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemCoin, 0, new ModelResourceLocation(
						LovecraftMain.itemCoin.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemWeirdShards, 0, new ModelResourceLocation(
						LovecraftMain.itemWeirdShards.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemWeirdDust, 0, new ModelResourceLocation(
						LovecraftMain.itemWeirdDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemFossilDust, 0, new ModelResourceLocation(
						LovecraftMain.itemFossilDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemMummyDust, 0, new ModelResourceLocation(
						LovecraftMain.itemMummyDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemFleshDust, 0, new ModelResourceLocation(
						LovecraftMain.itemFleshDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemMummyChunk, 0, new ModelResourceLocation(
						LovecraftMain.itemMummyChunk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemFleshChunk, 0, new ModelResourceLocation(
						LovecraftMain.itemFleshChunk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemSpoiledFleshChunk, 0, new ModelResourceLocation(
						LovecraftMain.itemSpoiledFleshChunk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemSpoiledFleshDust, 0, new ModelResourceLocation(
						LovecraftMain.itemSpoiledFleshDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemPreservedFleshChunk, 0, new ModelResourceLocation(
						LovecraftMain.itemPreservedFleshChunk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemPreservedFleshDust, 0, new ModelResourceLocation(
						LovecraftMain.itemPreservedFleshDust.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemMagnifyingGlass, 0, new ModelResourceLocation(
						LovecraftMain.itemMagnifyingGlass.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemRubbing, 0, new ModelResourceLocation(
						LovecraftMain.itemRubbing.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemTokenBat, 0, new ModelResourceLocation(
						LovecraftMain.itemTokenBat.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemTokenDread, 0, new ModelResourceLocation(
						LovecraftMain.itemTokenDread.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemTokenAwake, 0, new ModelResourceLocation(
						LovecraftMain.itemTokenAwake.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemTokenBrave, 0, new ModelResourceLocation(
						LovecraftMain.itemTokenBrave.getRegistryName(),
						"inventory"));
		
		
		//might want to do some subitemhandler stuff in future?
		((ItemTome)LovecraftMain.itemTome).initModel();
		((ItemBook)LovecraftMain.itemBook).initModel();
		((ItemFossil)LovecraftMain.itemFossil).initModel();
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockUnderstructure, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockUnderstructure.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockFlowerDrug, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockFlowerDrug.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockFossil, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockFossil.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockBasRelief, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockBasRelief.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockHookah, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockHookah.getRegistryName(),
						"inventory"));
		//ModelLoader.setCustomModelResourceLocation(
		//		LovecraftMain.itemBlockAetherOre, 0, new ModelResourceLocation(
		//				LovecraftMain.itemBlockAetherOre.getRegistryName(),
		//				"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockWeirdedBrick, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockWeirdedBrick.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockAltar, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockAltar.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockMortar, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockMortar.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockDesk, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockDesk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockObelisk, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockObelisk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockCarvedObelisk, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockCarvedObelisk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockChargedObelisk, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockChargedObelisk.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockObeliskCap, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockObeliskCap.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockResolvedObeliskCap, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockResolvedObeliskCap.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockCarvedStonebrick, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockCarvedStonebrick.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockCarvedSandstone, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockCarvedSandstone.getRegistryName(),
						"inventory"));
		ModelLoader.setCustomModelResourceLocation(
				LovecraftMain.itemBlockCarvedWeirdedBrick, 0, new ModelResourceLocation(
						LovecraftMain.itemBlockCarvedWeirdedBrick.getRegistryName(),
						"inventory"));
	}
}

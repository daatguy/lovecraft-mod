package daatguy.lovecraft.event;

import daatguy.lovecraft.client.sound.MovingSoundDrugged;
import daatguy.lovecraft.client.sound.SoundEventHandler;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.networking.PotionDruggedMessage;
import daatguy.lovecraft.world.potion.PotionDrugged;
import daatguy.lovecraft.world.potion.PotionSimple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class PotionEventHandler {

	private static final ResourceLocation motionblur = new ResourceLocation(
			"shaders/post/phosphor.json");

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onPlayerLogIn(PlayerLoggedInEvent event) {
		if (event.player.getActivePotionEffect(LovecraftMain.potionDrugged) != null) {
			LovecraftMain.instance.motionBlurShader = 1;
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onPlayerLogOut(PlayerLoggedOutEvent event) {
		LovecraftMain.instance.motionBlurShader = 0;
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event) {
		if (LovecraftMain.instance.motionBlurShader == 1
				&& !Minecraft.getMinecraft().entityRenderer.isShaderActive()) {
			Minecraft.getMinecraft().entityRenderer.loadShader(motionblur);
		} else if (LovecraftMain.instance.motionBlurShader == -1) {
			Minecraft.getMinecraft().entityRenderer.stopUseShader();
			LovecraftMain.instance.motionBlurShader = 0;
		}
	}

	@SubscribeEvent
	public static void PotionAddedEvent(PotionEvent.PotionAddedEvent event) {
		if (event.getPotionEffect().getPotion() == LovecraftMain.potionDrugged
				&& event.getEntity() instanceof EntityPlayerMP) {
			LovecraftMain.packetHandler.INSTANCE.sendTo(
					(IMessage) new PotionDruggedMessage(true),
					(EntityPlayerMP) event.getEntity());
		}
		if (event.getPotionEffect().getPotion() == LovecraftMain.potionDrugged
				&& event.getEntity() instanceof EntityPlayerSP) {
			Minecraft
					.getMinecraft()
					.getSoundHandler()
					.playSound(
							new MovingSoundDrugged((EntityPlayerSP) event
									.getEntity()));
		}
	}

	@SubscribeEvent
	public static void PotionRemoveEvent(PotionEvent.PotionRemoveEvent event) {
		if (event.getPotionEffect().getPotion() instanceof PotionSimple) {
			if (!(event.getPotionEffect().getPotion() instanceof PotionDrugged)) {
				event.setCanceled(true);
				return;
			}
		}
		if (event.getPotionEffect().getPotion() == LovecraftMain.potionDrugged
				&& event.getEntity() instanceof EntityPlayerMP) {
			LovecraftMain.packetHandler.INSTANCE.sendTo(
					(IMessage) new PotionDruggedMessage(false),
					(EntityPlayerMP) event.getEntity());
		}
	}

	@SubscribeEvent
	public static void PotionExpiryEvent(PotionEvent.PotionExpiryEvent event) {
		if (event.getPotionEffect().getPotion() == LovecraftMain.potionDrugged
				&& event.getEntity() instanceof EntityPlayerMP) {
			//((EntityPlayer) event.getEntity())
			//		.addPotionEffect(new PotionEffect(
			//				LovecraftMain.potionAwake, 20 * 60 * 8, event
			//						.getPotionEffect().getAmplifier(), false,
			//				false));
			LovecraftMain.packetHandler.INSTANCE.sendTo(
					(IMessage) new PotionDruggedMessage(false),
					(EntityPlayerMP) event.getEntity());
		}
	}

}

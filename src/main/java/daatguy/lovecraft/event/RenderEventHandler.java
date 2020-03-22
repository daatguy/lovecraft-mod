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
import net.minecraftforge.client.event.EntityViewRenderEvent;
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
public class RenderEventHandler {

	/*@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onFogColorRender(EntityViewRenderEvent.FogColors event){
        event.setRed(255);
        event.setGreen(0);
        event.setBlue(0);
    }
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
		
		
	}*/

}

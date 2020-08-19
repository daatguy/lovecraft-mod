package daatguy.lovecraft.world.leyline;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.entity.EntityLeyline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class LeylineHandler {
	
	@SideOnly(Side.CLIENT)
	public static ArrayList<FogAffecter> fogAffectors = new ArrayList<FogAffecter>();
	public static ArrayList<LeylineType> leylineTypes = new ArrayList<LeylineType>();
	public final static float weakScale = 32f;
	public final static float strongScale = 1f;
	public final static float heightScale = 2f;
	
	public LeylineHandler() {
		leylineTypes.add(new LeylineType(new Color(150,20,240)));
		
	}
	
	public class LeylineType {
		
		public Color color;
		
		public LeylineType(Color color) {
			this.color = color;
		}
		
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onFogEvent(EntityViewRenderEvent.RenderFogEvent event) {
		GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
		float totalFog = 0.0f;
		for(FogAffecter fa : fogAffectors) {
			totalFog += fa.weight;
		}
		totalFog = MathHelper.clamp(totalFog, 0.0f, 1.0f);

		//0.018 is (approx) the value that the sigmoid curve is at 0
		//Big dick sigmoid f curve math
		totalFog = 1.036f/(1.0f+(float)Math.exp(4-8*totalFog))-0.018f;
		GlStateManager.setFogStart(event.getFarPlaneDistance()*MathHelper.clamp(0.75f-totalFog,0f,1f));
	    GlStateManager.setFogEnd(event.getFarPlaneDistance()*(1f-totalFog));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST) 
	public static void onPlayerTickEvent(PlayerTickEvent event){
		//Find all the Leyline entities loaded on the client
		ArrayList<EntityLeyline> leylines = new ArrayList<EntityLeyline>();
		for(Entity e : event.player.world.loadedEntityList) {
			if(e instanceof EntityLeyline) {
				leylines.add((EntityLeyline)e);
			}
		}
		//Add all them as affectors to the leyline handler
		for(EntityLeyline leyline : leylines) {
			float f = weakScale*strongScale*weakScale*strongScale;
			if(event.player.getPosition().distanceSq(leyline.getPosition())<f) {
				//Add an affecter if not present
				boolean present = false;
				for(FogAffecter fa : LovecraftMain.leylineHandler.fogAffectors) {
					if(fa.entityUUID == leyline.getUniqueID()) {
						present = true;
						break;
					}
				}
				if(!present) {
					Color color = leylineTypes.get(leyline.type).color;
					LovecraftMain.leylineHandler.fogAffectors.add(new FogAffecter(color, 0f, leyline.getUniqueID()));
				}
				for(FogAffecter fa : LovecraftMain.leylineHandler.fogAffectors) {
					if(fa.entityUUID == leyline.getUniqueID()) {
						fa.weight = leyline.getFogIntensity(event.player.posX,event.player.posY,event.player.posZ);
					}
				}
			} else {
				//Remove effectors
				for(FogAffecter fa : LovecraftMain.leylineHandler.fogAffectors) {
					if(fa.entityUUID == leyline.getUniqueID()) {
						LovecraftMain.leylineHandler.fogAffectors.remove(fa);
						break;
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onWorldUnload(WorldEvent.Unload event) {
		LovecraftMain.leylineHandler.fogAffectors.clear();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onFogColorsEvent(EntityViewRenderEvent.FogColors event) {
		float totalFog = 0.0f;
		float totalRed = 0.0f;
		float totalGreen = 0.0f;
		float totalBlue = 0.0f;
		for(FogAffecter fa : fogAffectors) {
			totalFog += fa.weight;
			totalRed += fa.color.getRed()/255f*fa.weight;
			totalGreen += fa.color.getGreen()/255f*fa.weight;
			totalBlue += fa.color.getBlue()/255f*fa.weight;
		}
		if(totalFog>0.0f) {
			//Average colors
			totalRed /= totalFog;
			totalGreen /= totalFog;
			totalBlue /= totalFog;
		}
		if(totalFog<1.0f) {
			totalRed = totalRed*totalFog + event.getRed()*(1.0f-totalFog);
			totalGreen = totalGreen*totalFog + event.getGreen()*(1.0f-totalFog);
			totalBlue = totalBlue*totalFog + event.getBlue()*(1.0f-totalFog);
		}
		float totalScale = 0.3f;
		event.setRed(event.getRed()*(1.0f-totalScale)+totalScale*totalRed);
		event.setGreen(event.getGreen()*(1.0f-totalScale)+totalScale*totalGreen);
		event.setBlue(event.getBlue()*(1.0f-totalScale)+totalScale*totalBlue);
	}
	
	
	
}

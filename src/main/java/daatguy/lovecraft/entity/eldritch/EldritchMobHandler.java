package daatguy.lovecraft.entity.eldritch;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import daatguy.lovecraft.client.sound.SoundEventHandler;
import daatguy.lovecraft.core.LovecraftMain;

@EventBusSubscriber
public class EldritchMobHandler {
	
	public static ArrayList<Item> VALID_ITEMS = new ArrayList<Item>();
	public static ArrayList<Class<?>> AFFECTED_ENTITIES = new ArrayList<Class<?>>();
	
	public EldritchMobHandler() {
	}
	
	public void init() {
		VALID_ITEMS.add(LovecraftMain.itemFossilKnife);
	}
	
	@SubscribeEvent
	public static void playerHitEntity(AttackEntityEvent event) {
		boolean evalid = false;
		for(Class<?> e : AFFECTED_ENTITIES) {
			if(event.getTarget().getClass().equals(e)) {
				evalid = true;
				break;
			}
		}
		//If it isn't a worthwhile entity we return
		if(!evalid) return;
		boolean ivalid = false;
		for(Item i : VALID_ITEMS) {
			if(event.getEntityPlayer().getHeldItemMainhand().getItem().equals(i)) {
				//Big if true.
				ivalid = true;
				break;
			}
		}
		if(!ivalid && event.getTarget() instanceof EntityEldritch) {
			doEldritchDodgeEffect((EntityEldritch)event.getTarget());
			event.setCanceled(true);
		}
	}
	
	public static void doEldritchDodgeEffect(EntityEldritch entity) {
		entity.opacity = 0.35f;
		entity.world.playSound(entity.posX+0.5f, entity.posY+0.25f, entity.posZ+0.5f, SoundEventHandler.ELDRITCH_DODGE, SoundCategory.HOSTILE, 0.7f, 1.0f, false);
	}
	
}

package daatguy.lovecraft.event;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.item.ItemDecay;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DropEventHandler {

	@SubscribeEvent
	public static void onMobDrops(LivingDropsEvent event) {
		if (event.getEntity() instanceof EntityBat) {
			if (event.getSource().getTrueSource() instanceof EntityPlayer) {
				event.getDrops().add(ItemDecay.createItemEntity(event.getEntity().world, event
								.getEntity(), new ItemStack(
								LovecraftMain.itemTokenBat)));
			}
		}
	}
}

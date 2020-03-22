package io.github.daatguy.lovecraft.client.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;


@ObjectHolder("lovecraft")
public class SoundEventHandler {

	@ObjectHolder("ambient.obelisk_hum")
	public static final SoundEvent OBELISK_HUM = createSoundEvent("ambient.obelisk_hum");
	
	@ObjectHolder("ambient.drugged")
	public static final SoundEvent DRUGGED = createSoundEvent("ambient.drugged");

	@ObjectHolder("block.sepulchral_break")
	public static final SoundEvent SEPULCHRAL_BREAK = createSoundEvent("block.sepulchral_break");
	
	@ObjectHolder("block.mortar")
	public static final SoundEvent MORTAR = createSoundEvent("block.mortar");
	
	@ObjectHolder("spell_cap")
	public static final SoundEvent SPELL_CAP = createSoundEvent("spell_cap");

	private static SoundEvent createSoundEvent(String soundName) {
		final ResourceLocation soundID = new ResourceLocation("lovecraft", soundName);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(
					OBELISK_HUM,
					DRUGGED,
					MORTAR,
					SEPULCHRAL_BREAK,
					SPELL_CAP
			);
		}
	}
}

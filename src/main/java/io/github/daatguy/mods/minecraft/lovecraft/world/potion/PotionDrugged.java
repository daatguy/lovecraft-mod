package io.github.daatguy.mods.minecraft.lovecraft.world.potion;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionDrugged extends PotionSimple {

	private static final ResourceLocation motionblur = new ResourceLocation(
			"shaders/post/phosphor.json");
	
	public PotionDrugged(boolean isBadEffectIn, int liquidColorIn, int texX,
			int texY) {
		super(isBadEffectIn, liquidColorIn, texX, texY);
	}

}

package io.github.daatguy.lovecraft.world.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionSimple extends Potion {

	private static final ResourceLocation texture = new ResourceLocation(
			"lovecraft", "textures/gui/potion_icons.png");
	private int texX;
	private int texY;
	
	public PotionSimple(boolean isBadEffectIn, int liquidColorIn, int texX, int texY) {
		super(isBadEffectIn, liquidColorIn);
		this.texX = texX;
		this.texY = texY;
	}
	
	@Override
	public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y,
			float z, float alpha) {
		super.renderHUDEffect(effect, gui, x, y, z, alpha);
		float scale = 0.265f;
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
		GlStateManager.scale(scale, scale, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		gui.drawTexturedModalRect((x+3)/scale, (y+3)/scale, this.texX*68, this.texY*68, 68, 68);
		GlStateManager.popMatrix();
	}
	
	@Override
	public void renderInventoryEffect(PotionEffect effect, Gui gui, int x,
			int y, float z) {
		super.renderInventoryEffect(effect, gui, x, y, z);
		float scale = 0.265f;
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.scale(scale, scale, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		gui.drawTexturedModalRect((x+6)/scale, (y+7)/scale, this.texX*68, this.texY*68, 68, 68);
		GlStateManager.popMatrix();
	}
	
/*	public void register() {
		REGISTRY.register(9, new ResourceLocation("nausea"), (new PotionDread(true, 5578058)).setPotionName("effect.confusion"));
	}*/

}

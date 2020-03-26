package daatguy.lovecraft.world.potion;

import org.jline.terminal.impl.jna.freebsd.FreeBsdNativePty.UtilLibrary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;

public class PotionStatus extends PotionSimple {

	public PotionStatus(boolean isBadEffectIn, int liquidColorIn, int texX,
			int texY) {
		super(isBadEffectIn, liquidColorIn, texX, texY);
	}

	@Override
	public boolean shouldRenderInvText(PotionEffect effect) {
		return false;
	}

	@Override
	public void renderInventoryEffect(PotionEffect effect, Gui gui, int x,
			int y, float z) {
		super.renderInventoryEffect(effect, gui, x, y, z);
		int amplifier = effect.getAmplifier();
		String potionName = I18n.format(effect.getEffectName()
				+ String.valueOf(amplifier));
		String duration = Potion.getPotionDurationString(effect, 1.0F);
		float scale = 0.265f;

		if (amplifier < 2) {
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
					potionName, (float) (x + 10 + 18), (float) (y + 6),
					16777215);
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
					duration, (float) (x + 10 + 18), (float) (y + 6 + 10),
					8355711);

			GlStateManager.pushMatrix();
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			GlStateManager.scale(scale, scale, 1.0f);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			gui.drawTexturedModalRect((x + 6) / scale, (y + 7) / scale,
					this.texX * 68, this.texY * 68, 68, 68);
			GlStateManager.popMatrix();
		} else if (amplifier >= 2) {
			int color = 16777215;
			int graycolor = 8355711;
			float randXOffset = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*0.3F;
			float randYOffset = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*0.3F;
			float randXOffset2 = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*0.3F;
			float randYOffset2 = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*0.3F;
			float randXOffset3 = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*3F;
			float randYOffset3 = (float) (Math.random() * amplifier - (float) (amplifier) / 2.0F)*3F;
			GlStateManager.pushMatrix();
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			GlStateManager.scale(scale, scale, 1.0f);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			gui.drawTexturedModalRect((x + 6 + randXOffset3) / scale, (y + 7 + randYOffset3) / scale,
					this.texX * 68, this.texY * 68, 68, 68);
			GlStateManager.popMatrix();
			if(amplifier>=3) {
				double threshhold = 1.5D-amplifier*0.2;
				if(Math.random()>threshhold) {
					randXOffset *= Math.random()*8*amplifier;
				}
				if(Math.random()>threshhold) {
					randYOffset *= Math.random()*8*amplifier;
				}
				if(Math.random()>threshhold) {
					randXOffset2 *= Math.random()*8*amplifier;
				}
				if(Math.random()>threshhold) {
					randYOffset2 *= Math.random()*8*amplifier;
				}
				if(Math.random()>threshhold) {
					randXOffset3 *= Math.random()*80*amplifier;
				}
				if(Math.random()>threshhold) {
					randYOffset3 *= Math.random()*80*amplifier;
				}
				if(amplifier>=4) {
					//Red text
					color = 16711680;
					//Second Timer Glow
					int frameAfterSecond = effect.getDuration()%20;
					if(frameAfterSecond==19) {
						graycolor = 16777215;
					} else if(frameAfterSecond==18) {
						graycolor = 15132390;
					} else if(frameAfterSecond==17) {
						graycolor = 12566463;
					} else if(frameAfterSecond==16) {
						graycolor = 10066329;
					} else {
						graycolor = 8355711;
					}
				}
			}
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
					potionName, (float) (x + 10 + 18 + randXOffset),
					(float) (y + 6 + randYOffset), color);
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(
					duration, (float) (x + 10 + 18 + randXOffset2),
					(float) (y + 6 + 10 + randYOffset2), graycolor);
		}

	}

}

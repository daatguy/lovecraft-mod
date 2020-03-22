package io.github.daatguy.minecraft.mods.lovecraft.networking;

import io.github.daatguy.minecraft.mods.lovecraft.core.LovecraftMain;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PotionDruggedMessage implements IMessage {

	private boolean shader;
	private boolean messageValid;

	public PotionDruggedMessage() {
		this.messageValid = false;

	}

	public PotionDruggedMessage(boolean shader) {
		this.shader = shader;
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.messageValid) {
			buf.writeBoolean(this.shader);
		} else {
			return;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.shader = buf.readBoolean();

		} catch (IndexOutOfBoundsException ioe) {
			return;
		}
		this.messageValid = true;
	}

	public static class Handler implements
			IMessageHandler<PotionDruggedMessage, IMessage> {

		@Override
		public IMessage onMessage(PotionDruggedMessage message,
				MessageContext ctx) {
			if (message.messageValid) {
				if (ctx.side == Side.CLIENT) {
					if(message.shader) {
						LovecraftMain.instance.motionBlurShader = 1;
					} else {
						LovecraftMain.instance.motionBlurShader = -1;
					}
				}
			}
			return null;
		}
	}

}

package io.github.daatguy.mods.minecraft.lovecraft.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;
import io.github.daatguy.mods.minecraft.lovecraft.tileentity.TileEntityHookah;

public class HookahRequestMessage implements IMessage {

	private TileEntityHookah te;
	private boolean messageValid;
	private int x;
	private int y;
	private int z;

	public HookahRequestMessage() {
		this.messageValid = false;

	}

	public HookahRequestMessage(TileEntityHookah tile, int hand) {
		this.te = tile;
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.messageValid) {
			buf.writeInt(te.getPos().getX());
			buf.writeInt(te.getPos().getY());
			buf.writeInt(te.getPos().getZ());
		} else {
			return;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.x = buf.readInt();
			this.y = buf.readInt();
			this.z = buf.readInt();
		} catch (IndexOutOfBoundsException ioe) {
			return;
		}
		this.messageValid = true;
	}

	public static class Handler implements
			IMessageHandler<HookahRequestMessage, IMessage> {

		@Override
		public IMessage onMessage(HookahRequestMessage message, MessageContext ctx) {
			if (!message.messageValid) {
				return null;
			} else {
				if(ctx.side==Side.SERVER) {
					TileEntityHookah teHookah = (TileEntityHookah) ctx.getServerHandler().player.world.getTileEntity(new BlockPos(
							message.x, message.y, message.z));
					if(teHookah==null) {
						return null;
					}
					System.out.println(teHookah.smokeCount);
					//LovecraftMain.packetHandler.INSTANCE.sendToAll((IMessage)new HookahMessage(teHookah.smokeCount, teHookah));
					LovecraftMain.packetHandler.INSTANCE.sendTo((IMessage)new HookahMessage(teHookah.smokeCount, teHookah, 0), ctx.getServerHandler().player);
				} 
				return null;	
			}
		}
	}
}

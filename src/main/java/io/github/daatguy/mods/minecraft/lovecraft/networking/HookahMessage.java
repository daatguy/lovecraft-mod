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
import io.github.daatguy.mods.minecraft.lovecraft.tileentity.TileEntityHookah;

public class HookahMessage implements IMessage {

	private TileEntityHookah te;
	private int timer;
	private int hand;
	private boolean messageValid;
	private int x;
	private int y;
	private int z;

	public HookahMessage() {
		this.messageValid = false;

	}

	public HookahMessage(int timer, TileEntityHookah tile, int hand) {
		this.timer = timer;
		this.te = tile;
		this.hand = hand;
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.messageValid) {
			buf.writeInt(this.timer);
			buf.writeInt(te.getPos().getX());
			buf.writeInt(te.getPos().getY());
			buf.writeInt(te.getPos().getZ());
			buf.writeInt(hand);
		} else {
			return;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.timer = buf.readInt();
			this.x = buf.readInt();
			this.y = buf.readInt();
			this.z = buf.readInt();
			this.hand = buf.readInt();

		} catch (IndexOutOfBoundsException ioe) {
			return;
		}
		this.messageValid = true;
	}

	public static class Handler implements
			IMessageHandler<HookahMessage, IMessage> {

		@Override
		public IMessage onMessage(HookahMessage message, MessageContext ctx) {
			if (!message.messageValid) {
				return null;
			} else {
				if(ctx.side==Side.CLIENT) {
					int t = message.timer;
					TileEntityHookah teHookah = (TileEntityHookah) Minecraft.getMinecraft().world.getTileEntity(new BlockPos(
							message.x, message.y, message.z));
					if(teHookah==null) {
						return null;
					}
					// System.out.println(LovecraftMain.spellHandler.spells.get(name).startCast(
					// teAltar));
					teHookah.smokeCount = t;
				} 
				return null;
			}
		}
	}
}

package io.github.daatguy.lovecraft.networking;

import io.github.daatguy.lovecraft.core.LovecraftMain;
import io.github.daatguy.lovecraft.entity.EntitySpell;
import io.github.daatguy.lovecraft.tileentity.TileEntityAltar;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SpellMessage implements IMessage {

	private String spellName;
	private TileEntityAltar te;
	private int x;
	private int y;
	private int z;
	private boolean messageValid;

	public SpellMessage() {
		this.messageValid = false;

	}

	public SpellMessage(String spellName, TileEntityAltar te) {
		this.spellName = spellName;
		this.te = te;
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.messageValid) {
			ByteBufUtils.writeUTF8String(buf, this.spellName);
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
			this.spellName = ByteBufUtils.readUTF8String(buf);
			this.x = buf.readInt();
			this.y = buf.readInt();
			this.z = buf.readInt();

		} catch (IndexOutOfBoundsException ioe) {
			return;
		}
		this.messageValid = true;
	}

	public static class Handler implements
			IMessageHandler<SpellMessage, IMessage> {

		@Override
		public IMessage onMessage(SpellMessage message, MessageContext ctx) {
			if (!message.messageValid) {
				return null;
			} else if (ctx.side == Side.SERVER) {

				// (FMLCommonHandler.instance().getSide().equals(Side.SERVER)) {
				// This is the player the packet was sent to the server from
				EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
				// The value that was sent
				String name = message.spellName;
				TileEntityAltar teAltar = (TileEntityAltar) serverPlayer
						.getServerWorld().getTileEntity(
								new BlockPos(message.x, message.y, message.z));
				// Execute the action on the main server thread by adding it as
				// a
				// scheduled task

				serverPlayer
						.getServerWorld()
						.addScheduledTask(
								() -> {
									LovecraftMain.packetHandler.INSTANCE
											.sendToAll((new SpellMessage(name,
													teAltar)));
									if (LovecraftMain.spellHandler.spells.get(
											name).canStart(
											serverPlayer.getServerWorld(),
											teAltar.getPos())) {
										serverPlayer
												.getServerWorld()
												.spawnEntity(
														new EntitySpell(
																serverPlayer
																		.getServerWorld(),
																LovecraftMain.spellHandler.spells
																		.get(name),
																teAltar));
									}
								});
				return null;
			} else {
				if (Minecraft.getMinecraft().world.isRemote) {
					String name = message.spellName;
					TileEntityAltar teAltar = (TileEntityAltar) Minecraft
							.getMinecraft().world.getTileEntity(new BlockPos(
							message.x, message.y, message.z));
					// System.out.println(LovecraftMain.spellHandler.spells.get(name).startCast(
					// teAltar));
					if (LovecraftMain.spellHandler.spells.get(name).canStart(
							teAltar.getWorld(), teAltar.getPos())) {
						Minecraft.getMinecraft().world
								.spawnEntity(new EntitySpell(Minecraft
										.getMinecraft().world,
										LovecraftMain.spellHandler.spells
												.get(name), teAltar));
					}
				}
				return null;
			}
		}
	}
}

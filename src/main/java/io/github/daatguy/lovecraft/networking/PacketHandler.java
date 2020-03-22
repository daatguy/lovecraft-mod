package io.github.daatguy.lovecraft.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("lovecraft");
	
	public PacketHandler() {
		int id = 0;
		INSTANCE.registerMessage(SpellMessage.Handler.class, SpellMessage.class, id++, Side.SERVER);
		INSTANCE.registerMessage(SpellMessage.Handler.class, SpellMessage.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(PotionDruggedMessage.Handler.class, PotionDruggedMessage.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HookahMessage.Handler.class, HookahMessage.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HookahRequestMessage.Handler.class, HookahRequestMessage.class, id++, Side.SERVER);
	}

}

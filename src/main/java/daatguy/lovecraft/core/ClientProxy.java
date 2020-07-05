package daatguy.lovecraft.core;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import daatguy.lovecraft.client.gui.GuiHandler;
import daatguy.lovecraft.entity.EntitySpell;
import daatguy.lovecraft.entity.RenderFactorySpell;
import daatguy.lovecraft.entity.eldritch.EntityUrhag;
import daatguy.lovecraft.entity.eldritch.RenderFactoryUrhag;

public class ClientProxy extends CommonProxy {

	@EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpell.class, new RenderFactorySpell());
        RenderingRegistry.registerEntityRenderingHandler(EntityUrhag.class, new RenderFactoryUrhag());
        NetworkRegistry.INSTANCE.registerGuiHandler(LovecraftMain.instance, new GuiHandler());
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
	
}

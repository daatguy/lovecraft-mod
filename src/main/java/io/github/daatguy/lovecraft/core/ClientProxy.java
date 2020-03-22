package io.github.daatguy.lovecraft.core;

import io.github.daatguy.lovecraft.client.gui.GuiHandler;
import io.github.daatguy.lovecraft.entity.EntitySpell;
import io.github.daatguy.lovecraft.entity.RenderFactorySpell;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	@EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpell.class, new RenderFactorySpell());
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

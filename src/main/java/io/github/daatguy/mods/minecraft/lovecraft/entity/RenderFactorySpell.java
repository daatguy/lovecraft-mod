package io.github.daatguy.mods.minecraft.lovecraft.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactorySpell implements IRenderFactory<EntitySpell> {
	
	/**
	 * Dummy class
	 */
	@Override
	public Render<? super EntitySpell> createRenderFor(RenderManager manager) {
		return new RenderSpell<EntitySpell>(manager);
	}
}

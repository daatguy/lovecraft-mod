package io.github.daatguy.lovecraft.entity;

import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;

public class RenderSpell <T extends EntitySpell> extends Render<T>{

	/**
	 * Dummy class
	 */
	protected RenderSpell(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}
	@Override
	public boolean shouldRender(T livingEntity, ICamera camera, double camX,
			double camY, double camZ) {
		return false;
	}
	
}

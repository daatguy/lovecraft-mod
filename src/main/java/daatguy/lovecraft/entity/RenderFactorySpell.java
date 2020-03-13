package daatguy.lovecraft.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactorySpell implements IRenderFactory<EntitySpell> {
	@Override
	public Render<? super EntitySpell> createRenderFor(RenderManager manager) {
		return new RenderSpell<EntitySpell>(manager);
	}
}
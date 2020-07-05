package daatguy.lovecraft.entity.eldritch;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryUrhag implements IRenderFactory{

	@Override
	public Render createRenderFor(RenderManager manager) {
		return new RenderUrhag(manager);
	}

}

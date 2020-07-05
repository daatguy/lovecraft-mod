package daatguy.lovecraft.entity.eldritch;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUrhag extends RenderLiving<EntityUrhag>{

	public static final ResourceLocation texture = new ResourceLocation("lovecraft:textures/entity/urhag.png");
	
	public RenderUrhag(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelUrhag(), 4f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityUrhag entity) {
		return texture;
	}

}

package daatguy.lovecraft.entity.eldritch;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.FogMode;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;

public class RenderUrhag extends RenderLiving<EntityUrhag>{

	public static final ResourceLocation texture = new ResourceLocation("lovecraft:textures/entity/urhag.png");
	
	public RenderUrhag(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelUrhag(), 0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityUrhag entity) {
		return texture;
	}
	
	/**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityUrhag entity, float partialTickTime)
    {
    	GlStateManager.enableAlpha();
    	GlStateManager.enableBlend();
    	GlStateManager.color(1.0f, 1.0f, 1.0f, entity.opacity);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }
}

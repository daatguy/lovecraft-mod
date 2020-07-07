package daatguy.lovecraft.entity.eldritch;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelUrhag extends ModelBase {
	
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;

	public ModelUrhag() {
		textureWidth = 528;
		textureHeight = 528;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone, 0.0F, 1.5708F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 196, 352, 57.0F, -60.0F, -12.0F, 24, 24, 24, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 272, 0, 24.0F, -88.0F, -40.0F, 48, 80, 80, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -40.0F, -84.0F, -36.0F, 64, 72, 72, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 192, 160, -56.0F, -72.0F, -24.0F, 16, 48, 48, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 256, -96.0F, -64.0F, -16.0F, 40, 32, 32, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 240, 256, -112.0F, -56.0F, -8.0F, 16, 16, 16, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 338, 434, -32.0F, -114.0F, -56.0F, 48, 48, 48, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone3, 0.0F, 1.5708F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 337, 315, 15.0F, -43.0F, -54.0F, 48, 48, 48, 0.0F, false));

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone4, 0.0F, 1.5708F, 0.0F);
		bone4.cubeList.add(new ModelBox(bone4, 0, 434, -2.0F, -69.0F, 13.0F, 48, 48, 48, 0.0F, false));
	}
	/**
     * Sets the models various rotation angles then renders the model.
     */
	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
		bone.render(scale+0.003F * MathHelper.sin(ageInTicks * 0.3F));
		bone2.render(scale+0.01F * MathHelper.sin(ageInTicks * 0.08F));
		bone3.render(scale+0.01F * MathHelper.sin(ageInTicks * 0.10F));
		bone4.render(scale+0.01F * MathHelper.sin(ageInTicks * 0.12F));
    }
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	} 
    
    
}
package daatguy.lovecraft.entity.eldritch;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

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
		bone.cubeList.add(new ModelBox(bone, 196, 352, 33.0F, -12.0F, -12.0F, 24, 24, 24, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 272, 0, 0.0F, -40.0F, -40.0F, 48, 80, 80, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -64.0F, -36.0F, -36.0F, 64, 72, 72, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 192, 160, -80.0F, -24.0F, -24.0F, 16, 48, 48, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 256, -120.0F, -16.0F, -16.0F, 40, 32, 32, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 240, 256, -136.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 338, 434, -32.0F, -66.0F, -32.0F, 48, 48, 48, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 337, 315, -32.0F, 0.0F, -56.0F, 48, 48, 48, 0.0F, false));

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone4.cubeList.add(new ModelBox(bone4, 0, 434, -32.0F, -13.0F, 10.0F, 48, 48, 48, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
		bone2.render(f5);
		bone3.render(f5);
		bone4.render(f5);
	}
	
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
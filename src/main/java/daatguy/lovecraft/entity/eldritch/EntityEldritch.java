package daatguy.lovecraft.entity.eldritch;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityEldritch extends EntityLiving {

	public float opacity = 0f;

	public EntityEldritch(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	};

	@Override
	protected boolean canDespawn() {
		return false;
	};

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("Opacity", this.opacity);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (compound.hasKey("Opacity", 5)) {
			this.opacity = compound.getInteger("Opacity");
		}
	}

	public void onUpdate() {
		super.onUpdate();

		this.opacity = MathHelper.clamp(this.opacity * 0.9f +
				0.10f * MathHelper.sin(this.ticksExisted / 25) + 0.04f
						* MathHelper.sin(this.ticksExisted / 5) + 0.09f
						* MathHelper.sin(this.ticksExisted / 17) + 0.06f
						* MathHelper.sin(this.ticksExisted / 70) + 0.03f
						* MathHelper.sin(this.ticksExisted / 4), 0, 1);

		if (!this.world.isRemote
				&& this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
	}

	@Override
	public boolean canBeHitWithPotion() {
		return false;
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		// Can't die unless killed by player or void
		return !(source.damageType == "player" || source.damageType == "outOfWorld");
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}

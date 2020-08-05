package daatguy.lovecraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.world.leyline.FogAffecter;
import daatguy.lovecraft.world.leyline.LeylineHandler;

public class EntityLeyline extends Entity {

	public EnumFacing direction;
	public int type;

	/**
	 * Entity placeholder for drawing spell-related effects, doing spell things
	 */
	public EntityLeyline(World worldIn) {
		super(worldIn);
		this.type = 0;
		this.direction = EnumFacing.WEST;
		this.setInvisible(true);
		this.setNoGravity(true);
	}

	public EntityLeyline(World worldIn, int typeIn, EnumFacing directionIn) {
		super(worldIn);
		this.type = typeIn;
		this.direction = directionIn;
		this.setInvisible(true);
		this.setNoGravity(true);
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	public boolean getIsInvulnerable() {
		return true;
	}

	@Override
	public boolean isInvisible() {
		return true;
	}

	public float getFogIntensity(double x, double y, double z) {
		//Rotate frame of reference
		double oldX = x;
		double oldY = y;
		double oldZ = z;
		BlockPos entityPos = this.getPosition();
		//Align with direction for easier math
		//Negative z is north
		//Negative x is west
		switch (this.direction) {
			case EAST:
				entityPos.rotate(Rotation.CLOCKWISE_90);
				x = oldZ;
				z = -oldX;
				break;
			case SOUTH:
				entityPos.rotate(Rotation.CLOCKWISE_180);
				x = -oldX;
				z = -oldZ;
				break;
			case WEST:
				entityPos.rotate(Rotation.COUNTERCLOCKWISE_90);
				x = -oldZ;
				z = oldX;
				break;
			default:
				break;
		}
		//System.out.println(this.direction);
		float f = 0.05f;
		y = (float) (entityPos.getY()+(y-entityPos.getY())/LeylineHandler.heightScale);
		if(z-entityPos.getZ()<0) {
			z = (float) (entityPos.getZ()+(z-entityPos.getZ())/LeylineHandler.strongScale);
		}
		f += (LeylineHandler.weakScale-Math.sqrt(entityPos.distanceSq(x, y, z)))/LeylineHandler.weakScale;
		f = MathHelper.clamp(f, 0.0f, 1.0f);
		
		return f;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onRemovedFromWorld() {
		//Remove effectors
		for(FogAffecter fa : LovecraftMain.leylineHandler.fogAffectors) {
			if(fa.entityUUID == this.entityUniqueID) {
				LovecraftMain.leylineHandler.fogAffectors.remove(fa);
				break;
			}
		}
		super.onRemovedFromWorld();
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Type", this.type);
		compound.setInteger("Direction", this.direction.getIndex());
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.type = compound.getInteger("Type");
		this.direction = EnumFacing.getFront(compound.getInteger("Direction"));
	}

	@Override
	protected void entityInit() {
	}
}

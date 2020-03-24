package daatguy.lovecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityHookah extends TileEntity implements ITickable{
	
	public int smokeCount = 0;
	public int lockout = 0;
	public final int smokeMax = 20*2*60;
	
	@Override
	public void update() {
		if(this.smokeCount>0) this.smokeCount--;
		if(this.lockout>0) this.lockout--;
		
		if (isSmoking()) {
			for (int i = 0; i < 2; i++) {
				double x = (double) pos.getX() + 0.5D;
				double y = (double) pos.getY() + 1.0D;
				double z = (double) pos.getZ() + 0.5D;
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z,
						0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	public boolean isSmoking() {
		return (this.smokeCount>0);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("timer", this.smokeCount);
		compound.setInteger("lockout", this.lockout);
		return compound;
	}
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.smokeCount = compound.getInteger("timer");
		this.lockout = compound.getInteger("lockout");
		super.readFromNBT(compound);
	}

}

package daatguy.lovecraft.tileentity;

import daatguy.lovecraft.item.SubItemsHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCarving extends TileEntity {

	public String carving;
	public int language;
	public boolean destroyedByCreativePlayer = false;
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.saveToNbt(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.carving = compound.getString("Carving");
		this.language = compound.getInteger("Language");
		super.readFromNBT(compound);
	}

	public NBTTagCompound saveToNbt(NBTTagCompound compound) {
		compound.setString("Carving", this.carving);
		compound.setInteger("Language", this.language);
		return compound;
	}

	
}

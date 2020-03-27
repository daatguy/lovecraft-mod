package daatguy.lovecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.core.LovecraftMain;

public class TileEntityChargedObelisk extends TileEntity implements ITickable {

	public int explosionTimer = 0;
	public int explosionMax = 20 * 60 * 5;
	public boolean enabled = true;

	@Override
	public void update() {
		//this.markDirty();
		if (this.enabled) {
			if (this.explosionTimer >= this.explosionMax
					&& this.getWorld().getBlockState(pos.up()).getBlock() != LovecraftMain.blockChargedObelisk
					&& this.getWorld().getBlockState(pos.up()).getBlock() != LovecraftMain.blockResolvedObeliskCap) {
				this.explosionTimer = -1;
				this.explode(this.getWorld(), this.getPos());
			} else if (this.explosionTimer >= 0 && this.explosionTimer < this.explosionMax) {
				this.explosionTimer++;
			}
		}
	}

	public static void explode(World worldIn, BlockPos pos) {
		worldIn.createExplosion((Entity) null, pos.getX(), pos.getY(),
				pos.getZ(), 10.0F, true);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("timer", this.explosionTimer);
		return compound;
	}
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.explosionTimer = compound.getInteger("timer");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	public void onBlockUpdate() {
		if (this.enabled) {
			TileEntityAltar altar = this.findAltar();
			if (altar != null) {
				if (altar.getObeliskType() < TileEntityAltar.CHARGED_OBELISK) {
					// altar.setToObelisk(altar.getPos(), altar.getWorld(),
					// LovecraftMain.blockChargedObelisk,
					// LovecraftMain.blockCarvedObelisk);
					// altar.setToObelisk(altar.getPos(), altar.getWorld(),
					// LovecraftMain.blockResolvedObeliskCap,
					// LovecraftMain.blockObeliskCap);
					this.explode(this.world, this.getPos());
				}
			} else {
				this.explode(this.world,this.getPos());
			}
		}
	}

	public TileEntityAltar findAltar() {
		for (int i = 0; i < 255; i++) {
			// The X's and Z's
			for (int j = -1; j < 2; j = j + 2) {
				BlockPos bPos = new BlockPos(this.pos.getX() + j * 3,
						this.pos.getY() - i, this.pos.getZ());
				Block block = getWorld().getBlockState(bPos).getBlock();
				if (block == LovecraftMain.blockAltar) {
					return (TileEntityAltar) getWorld().getTileEntity(bPos);
				}
			}
			for (int j = -1; j < 2; j = j + 2) {
				BlockPos bPos = new BlockPos(this.pos.getX(), this.pos.getY()
						- i, this.pos.getZ() + j * 3);
				Block block = getWorld().getBlockState(bPos).getBlock();
				if (block == LovecraftMain.blockAltar) {
					return (TileEntityAltar) getWorld().getTileEntity(bPos);
				}
			}
			// The Corner Blocks
			for (int j = -1; j < 2; j = j + 2) {
				for (int k = -1; k < 2; k = k + 2) {
					BlockPos bPos = new BlockPos(this.pos.getX() + j * 2,
							this.pos.getY() - i, this.pos.getZ() + k * 2);
					Block block = getWorld().getBlockState(bPos).getBlock();
					if (block == LovecraftMain.blockAltar) {
						return (TileEntityAltar) getWorld().getTileEntity(bPos);
					}
				}
			}
		}
		return null;
	}

}

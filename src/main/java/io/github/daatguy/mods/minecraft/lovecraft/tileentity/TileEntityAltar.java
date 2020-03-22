package io.github.daatguy.mods.minecraft.lovecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import io.github.daatguy.mods.minecraft.lovecraft.client.sound.SoundEventHandler;
import io.github.daatguy.mods.minecraft.lovecraft.core.LovecraftMain;

public class TileEntityAltar extends TileEntity implements ITickable {

	ItemStackHandler inventory = new ItemStackHandler(10);
	public static final int NO_OBELISK = 0;
	public static final int SIMPLE_OBELISK = 1;
	public static final int CARVED_OBELISK = 2;
	public static final int CHARGED_OBELISK = 3;
	private int counter = 0;

	// @Override
	// public void updateContainingBlockInfo() {
	// super.updateContainingBlockInfo();
	@Override
	public void update() {
		TileEntity tileW = (world
				.getTileEntity(new BlockPos(pos.getX() + 3, pos.getY(), pos
						.getZ())));
		TileEntityChargedObelisk obeliskTile = null;
		if(tileW instanceof TileEntityChargedObelisk) {
			obeliskTile = (TileEntityChargedObelisk)tileW;
		}
		if (obeliskTile != null) {
			float pitchRatio = ((float) obeliskTile.explosionTimer)
					/ ((float) obeliskTile.explosionMax);
			if (this.counter >= 12 - (6 * pitchRatio) && world.isRemote
					&& getObeliskType() >= 3) {
				this.counter = 0;
				world.playSound(pos.getX(), pos.getY(), pos.getZ(),
						SoundEventHandler.OBELISK_HUM, SoundCategory.AMBIENT,
						0.2F, pitchRatio * pitchRatio * 2.0F, false);
			} else {
				this.counter++;
			}
		}
	}

	public static void setToObelisk(BlockPos pos, World worldIn,
			Block blockFrom, Block blockTo, int height) {
		/*
		if (blockFrom == LovecraftMain.blockChargedObelisk
				|| blockTo == LovecraftMain.blockChargedObelisk) {
			for (int i = 0; i < height; i++) {
				// The X's and Z's
				for (int j = -1; j < 2; j = j + 2) {
					BlockPos bPos = new BlockPos(pos.getX() + j * 3, pos.getY()
							+ i, pos.getZ());
					Block block = worldIn.getBlockState(bPos).getBlock();
					if (block == blockFrom) {
						TileEntityChargedObelisk tile = ((TileEntityChargedObelisk) worldIn
								.getTileEntity(bPos));
						if (tile != null) tile.enabled = false;
					}
				}
				for (int j = -1; j < 2; j = j + 2) {
					BlockPos bPos = new BlockPos(pos.getX(), pos.getY() + i,
							pos.getZ() + j * 3);
					Block block = worldIn.getBlockState(bPos).getBlock();
					if (block == blockFrom) {
						TileEntityChargedObelisk tile = ((TileEntityChargedObelisk) worldIn
								.getTileEntity(bPos));
						if (tile != null) tile.enabled = false;
					}
				}
				// The Corner Blocks
				for (int j = -1; j < 2; j = j + 2) {
					for (int k = -1; k < 2; k = k + 2) {
						BlockPos bPos = new BlockPos(pos.getX() + j * 2,
								pos.getY() + i, pos.getZ() + k * 2);
						Block block = worldIn.getBlockState(bPos).getBlock();
						if (block == blockFrom) {
							TileEntityChargedObelisk tile = ((TileEntityChargedObelisk) worldIn
									.getTileEntity(bPos));
							if (tile != null) tile.enabled = false;
						}
					}
				}
			}
		}*/

		for (int i = 0; i < height; i++) {
			// The X's and Z's
			for (int j = -1; j < 2; j = j + 2) {
				BlockPos bPos = new BlockPos(pos.getX() + j * 3,
						pos.getY() + i, pos.getZ());
				Block block = worldIn.getBlockState(bPos).getBlock();
				if (block == blockFrom) {
					worldIn.setBlockState(bPos, blockTo.getDefaultState(), 18);
				}
			}
			for (int j = -1; j < 2; j = j + 2) {
				BlockPos bPos = new BlockPos(pos.getX(), pos.getY() + i,
						pos.getZ() + j * 3);
				Block block = worldIn.getBlockState(bPos).getBlock();
				if (block == blockFrom) {
					if (block == blockFrom) {
						worldIn.setBlockState(bPos, blockTo.getDefaultState(), 18);
					}
				}
			}
			// The Corner Blocks
			for (int j = -1; j < 2; j = j + 2) {
				for (int k = -1; k < 2; k = k + 2) {
					BlockPos bPos = new BlockPos(pos.getX() + j * 2, pos.getY()
							+ i, pos.getZ() + k * 2);
					Block block = worldIn.getBlockState(bPos).getBlock();
					if (block == blockFrom) {
						if (block == blockFrom) {
							worldIn.setBlockState(bPos,
									blockTo.getDefaultState(), 18);
						}
					}
				}
			}
		}
	}

	public static boolean checkForObeliskLayer(World world, Block block,
			BlockPos pos) {
		if (world.getBlockState(
				new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ()))
				.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ()))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() + 2))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() - 2))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() + 2))
						.getBlock() == block
				&& world.getBlockState(
						new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2))
						.getBlock() == block) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isObeliskCap(Block block) {
		return (block == LovecraftMain.blockObeliskCap || block == LovecraftMain.blockResolvedObeliskCap);
	}

	public static boolean checkForObeliskLayerCap(World world, BlockPos pos) {
		if (isObeliskCap(world.getBlockState(
				new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ()))
				.getBlock())
				&& isObeliskCap(world.getBlockState(
						new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ()))
						.getBlock())
				&& isObeliskCap(world.getBlockState(
						new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3))
						.getBlock())
				&& isObeliskCap(world.getBlockState(
						new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3))
						.getBlock())
				&& isObeliskCap(world
						.getBlockState(
								new BlockPos(pos.getX() + 2, pos.getY(), pos
										.getZ() + 2)).getBlock())
				&& isObeliskCap(world
						.getBlockState(
								new BlockPos(pos.getX() + 2, pos.getY(), pos
										.getZ() - 2)).getBlock())
				&& isObeliskCap(world
						.getBlockState(
								new BlockPos(pos.getX() - 2, pos.getY(), pos
										.getZ() + 2)).getBlock())
				&& isObeliskCap(world
						.getBlockState(
								new BlockPos(pos.getX() - 2, pos.getY(), pos
										.getZ() - 2)).getBlock())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkForObeliskType(TileEntityAltar te, Block block) {

		int height = 0;
		while (checkForObeliskLayer(te.getWorld(), block, new BlockPos(te
				.getPos().getX(), te.getPos().getY() + height, te.getPos()
				.getZ()))) {
			height++;
		}
		if (height < 2) {
			return false;
		}
		if (!this.checkForObeliskLayerCap(te.getWorld(), new BlockPos(te
				.getPos().getX(), te.getPos().getY() + height, te.getPos()
				.getZ()))) {
			return false;
		}

		return true;
	}

	public static int checkForObeliskHeight(World world, BlockPos pos) {

		int height = 0;
		while (checkForObeliskLayer(world, LovecraftMain.blockObelisk,
				new BlockPos(pos.getX(), pos.getY() + height, pos.getZ()))
				|| checkForObeliskLayer(
						world,
						LovecraftMain.blockCarvedObelisk,
						new BlockPos(pos.getX(), pos.getY() + height, pos
								.getZ()))
				|| checkForObeliskLayer(
						world,
						LovecraftMain.blockChargedObelisk,
						new BlockPos(pos.getX(), pos.getY() + height, pos
								.getZ()))) {
			height++;
		}
		if (height < 2) {
			return 0;
		}
		if (!checkForObeliskLayerCap(world, new BlockPos(pos.getX(), pos.getY()
				+ height, pos.getZ()))) {
			return 0;
		}

		return height + 1;
	}

	public int getObeliskType() {
		if (checkForObeliskType(this, LovecraftMain.blockChargedObelisk)) {
			return CHARGED_OBELISK;
		} else if (checkForObeliskType(this, LovecraftMain.blockCarvedObelisk)) {
			return CARVED_OBELISK;
		} else if (checkForObeliskType(this, LovecraftMain.blockObelisk)) {
			return SIMPLE_OBELISK;
		} else {
			return NO_OBELISK;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		// TODO Auto-generated method stub
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory
				: super.getCapability(capability, facing);
	}

}

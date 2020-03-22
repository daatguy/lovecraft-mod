package daatguy.lovecraft.generator;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import daatguy.lovecraft.block.BlockBasRelief;
import daatguy.lovecraft.book.spell.SpellHandler;
import daatguy.lovecraft.core.LovecraftMain;

public class ZigguratGenerator implements IWorldGenerator {

	//This class might get scrapped
	//Should at the very least by heavily re-written
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.getDimension()) {

		case -1:
			this.generateNether(world, random, chunkX, chunkZ);
			break;
		case 0:
			this.generateOverworld(world, random, chunkX, chunkZ);
			break;
		case 1:
			this.generateEnd(world, random, chunkX, chunkZ);
			break;

		}

	}

	public void generateNether(World world, Random random, int chunkX,
			int chunkZ) {
	}

	public void generateOverworld(World world, Random random, int chunkX,
			int chunkZ) {
		generateZiggurat(world, random, chunkX, chunkZ, 1500, -40, 0, 71,
					201);
	}

	public void generateEnd(World world, Random random, int chunkX, int chunkZ) {
	}

	public void generateZiggurat(World world, Random random, int chunkX,
			int chunkZ, int chance, int minY, int maxY, int minSize, int maxSize) {
		if (random.nextFloat() > (1.0f / (float)chance))
			return;
		int size;
		int sizeRange = maxSize - minSize;
		if (sizeRange == 0) {
			size = minSize;
		} else {
			size = minSize + 2 * random.nextInt((int)((float)sizeRange / 2.0f));
		}
		if(isEven(size)) size++;
		int heightRange = maxY - minY;
		int sx = chunkX * 16 + random.nextInt(16) + 1;
		int sy;
		if (heightRange == 0) {
			sy = (int) (minY * ((float)size)/((float)maxSize));
		} else {
			sy = (int) (minY + random.nextInt(heightRange) * ((float)size)/((float)maxSize));
		}
		for(int i = 0; i < 255+minY; i++) {
			BlockPos pos = new BlockPos(sx+size/2,255-i,sy+size/2);
			if(world.getBlockState(pos).getBlock()==Blocks.STONE) {
				sy+=pos.getY();
				break;
			} else {
				pos.down();
			}
		}
		int sz = chunkZ * 16 + random.nextInt(16) + 1;
		IBlockState block = LovecraftMain.blockWeirdedBrick.getDefaultState();
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				world.setBlockState(new BlockPos(sx + x, sy, sz + z), block, 2);
			}
		}
		for (int y = 0; y < size / 2; y++) {
			for (int x = 0; x < size - y * 2; x++) {
				for (int z = 0; z < size - y * 2; z++) {
					world.setBlockState(new BlockPos(sx + x + y, sy + y, sz + z
							+ y), block, 2);
				}
			}
		}
		
		System.out.println("Generating ziggurat of size "+String.valueOf(size)+" at "+String.valueOf(sx)+","+String.valueOf(sy)+","+String.valueOf(sz));
		size = size - 4;
		sx = sx + 2;
		sy++;
		sz = sz + 2;
		block = Blocks.SOUL_SAND.getDefaultState();
		for (int y = 0; y < size / 2; y++) {
			for (int x = 0; x < size - y * 2; x++) {
				for (int z = 0; z < size - y * 2; z++) {
					world.setBlockState(new BlockPos(sx + x + y, sy + y, sz + z
							+ y), block, 2);
				}
			}
		}

		size = size + 2;
		sx--;
		sz--;
		int tx = sx + (size / 2);
		int ty = sy + 4;
		int tz = sz + (size / 2);
		int ox = tx;
		int oy = ty + 3;
		int oz = tz;
		int dir = 0;
		carveBlocks(world, ox, oy, oz, 7);
		if(random.nextBoolean()) {
			if(random.nextBoolean()) {
				tx += 3;
				dir = 0;
				//Corner 1
				world.setBlockState(new BlockPos(ox-4,oy-1,oz-2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-2,oz-1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-2,oz-2), Blocks.STONEBRICK.getDefaultState());
				//Corner 2
				world.setBlockState(new BlockPos(ox-4,oy-1,oz+2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-2,oz+1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-2,oz+2), Blocks.STONEBRICK.getDefaultState());
				//Corner 3
				world.setBlockState(new BlockPos(ox-4,oy+1,oz-2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+2,oz-1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+2,oz-2), Blocks.STONEBRICK.getDefaultState());
				//Corner 4
				world.setBlockState(new BlockPos(ox-4,oy+1,oz+2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+2,oz+1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+2,oz+2), Blocks.STONEBRICK.getDefaultState());
				//Stonebrick details
				world.setBlockState(new BlockPos(ox-4,oy,oz+2), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-4,oy,oz-2), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-4,oy+2,oz), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-4,oy-2,oz), Blocks.STONEBRICK.getStateFromMeta(3));
				//Center indent
				world.setBlockState(new BlockPos(ox-4,oy+1,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+1,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+1,oz-1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+0,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+0,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy+0,oz-1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-1,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-1,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-4,oy-1,oz-1), Blocks.AIR.getDefaultState());
				//Center indent blocks
				world.setBlockState(new BlockPos(ox-5,oy+1,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy+1,oz+0), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy+1,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy+0,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy+0,oz+0), LovecraftMain.blockBasRelief.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-5,oy+0,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy-1,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy-1,oz+0), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-5,oy-1,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-2,oy-3,oz), LovecraftMain.blockAltar.getDefaultState());
			} else {
				tx -= 3;
				dir = 1;
				//Corner 1
				world.setBlockState(new BlockPos(ox+4,oy-1,oz-2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-2,oz-1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-2,oz-2), Blocks.STONEBRICK.getDefaultState());
				//Corner 2
				world.setBlockState(new BlockPos(ox+4,oy-1,oz+2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-2,oz+1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-2,oz+2), Blocks.STONEBRICK.getDefaultState());
				//Corner 3
				world.setBlockState(new BlockPos(ox+4,oy+1,oz-2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+2,oz-1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+2,oz-2), Blocks.STONEBRICK.getDefaultState());
				//Corner 4
				world.setBlockState(new BlockPos(ox+4,oy+1,oz+2), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+2,oz+1), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+2,oz+2), Blocks.STONEBRICK.getDefaultState());
				//Stonebrick details
				world.setBlockState(new BlockPos(ox+4,oy,oz+2), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox+4,oy,oz-2), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox+4,oy+2,oz), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox+4,oy-2,oz), Blocks.STONEBRICK.getStateFromMeta(3));
				//Center indent
				world.setBlockState(new BlockPos(ox+4,oy+1,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+1,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+1,oz-1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+0,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+0,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy+0,oz-1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-1,oz+1), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-1,oz+0), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+4,oy-1,oz-1), Blocks.AIR.getDefaultState());
				//Center indent blocks
				world.setBlockState(new BlockPos(ox+5,oy+1,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy+1,oz+0), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy+1,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy+0,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy+0,oz+0), LovecraftMain.blockBasRelief.getStateFromMeta(5));
				world.setBlockState(new BlockPos(ox+5,oy+0,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy-1,oz+1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy-1,oz+0), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+5,oy-1,oz-1), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+2,oy-3,oz), LovecraftMain.blockAltar.getDefaultState());
			}
		} else {
			if(random.nextBoolean()) {
				tz += 3;
				dir = 2;
				//Corner 1
				world.setBlockState(new BlockPos(ox-2,oy-1,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-2,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-2,oy-2,oz-4), Blocks.STONEBRICK.getDefaultState());
				//Corner 2
				world.setBlockState(new BlockPos(ox+2,oy-1,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-2,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+2,oy-2,oz-4), Blocks.STONEBRICK.getDefaultState());
				//Corner 3
				world.setBlockState(new BlockPos(ox-2,oy+1,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+2,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-2,oy+2,oz-4), Blocks.STONEBRICK.getDefaultState());
				//Corner 4
				world.setBlockState(new BlockPos(ox+2,oy+1,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+2,oz-4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+2,oy+2,oz-4), Blocks.STONEBRICK.getDefaultState());
				//Stonebrick details
				world.setBlockState(new BlockPos(ox+2,oy,oz-4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-2,oy,oz-4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox,oy+2,oz-4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox,oy-2,oz-4), Blocks.STONEBRICK.getStateFromMeta(3));
				//Center indent
				world.setBlockState(new BlockPos(ox+1,oy+1,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+1,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+1,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+0,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+0,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+0,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-1,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy-1,oz-4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-1,oz-4), Blocks.AIR.getDefaultState());
				//Center indent blocks
				world.setBlockState(new BlockPos(ox+1,oy+1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+0,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+0,oz-5), LovecraftMain.blockBasRelief.getStateFromMeta(2));
				world.setBlockState(new BlockPos(ox-1,oy+0,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy-1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-1,oz-5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox,oy-3,oz-2), LovecraftMain.blockAltar.getDefaultState());
			} else {
				tz -= 3;
				dir = 3;
				//Corner 1
				world.setBlockState(new BlockPos(ox-2,oy-1,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-2,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-2,oy-2,oz+4), Blocks.STONEBRICK.getDefaultState());
				//Corner 2
				world.setBlockState(new BlockPos(ox+2,oy-1,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-2,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+2,oy-2,oz+4), Blocks.STONEBRICK.getDefaultState());
				//Corner 3
				world.setBlockState(new BlockPos(ox-2,oy+1,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+2,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox-2,oy+2,oz+4), Blocks.STONEBRICK.getDefaultState());
				//Corner 4
				world.setBlockState(new BlockPos(ox+2,oy+1,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+2,oz+4), Blocks.STONEBRICK.getDefaultState());
				world.setBlockState(new BlockPos(ox+2,oy+2,oz+4), Blocks.STONEBRICK.getDefaultState());
				//Stonebrick details
				world.setBlockState(new BlockPos(ox+2,oy,oz+4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox-2,oy,oz+4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox,oy+2,oz+4), Blocks.STONEBRICK.getStateFromMeta(3));
				world.setBlockState(new BlockPos(ox,oy-2,oz+4), Blocks.STONEBRICK.getStateFromMeta(3));
				//Center indent
				world.setBlockState(new BlockPos(ox+1,oy+1,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+1,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+1,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+0,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+0,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+0,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-1,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy-1,oz+4), Blocks.AIR.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-1,oz+4), Blocks.AIR.getDefaultState());
				//Center indent blocks
				world.setBlockState(new BlockPos(ox+1,oy+1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy+1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy+0,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy+0,oz+5), LovecraftMain.blockBasRelief.getStateFromMeta(4));
				world.setBlockState(new BlockPos(ox-1,oy+0,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+1,oy-1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox+0,oy-1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox-1,oy-1,oz+5), LovecraftMain.blockCarvedObelisk.getDefaultState());
				world.setBlockState(new BlockPos(ox,oy-3,oz+2), LovecraftMain.blockAltar.getDefaultState());
			}
		}
		int loop = 0;
		boolean up = false;
		int upTimer = 0;
		int maxLoop = 1600 * size;
		int downTimer = 5;
		while (ty < sy + (size / 2) - 3 && loop < maxLoop) {
			loop++;
			int rx = 0;
			int ry = 0;
			int rz = 0;
			float num = (float) (0.8f - 0.6f * Math.pow(
					(double) ((ty - sy) / (size / 2.0f)), 1.5D));
			if (!up && random.nextFloat() > num) {
				ry = 1;
				up = true;
				upTimer = random.nextInt(3) + 2;
			} else if (!up && random.nextFloat() > 0.5f + 0.5f * num
					&& downTimer == 0) {
				ry = -1;
				up = true;
				upTimer = random.nextInt(3) + 2;
			} else {
				up = false;
				if (upTimer > 0)
					upTimer--;
				if (random.nextFloat() > 0.74f && upTimer == 0) {
					dir = random.nextInt(4);
				}
				if (dir == 0) {
					rx = 1;
				} else if (dir == 1) {
					rx = -1;
				} else if (dir == 2) {
					rz = 1;
				} else if (dir == 3) {
					rz = -1;
				}
				/*
				 * if(random.nextBoolean()) { rx = random.nextInt(2)*2 - 1; }
				 * else { rz = random.nextInt(2)*2 - 1; }
				 */
			}
			if (downTimer > 0)
				downTimer--;
			if (isPointInside(size - 2, tx + rx - sx, ty + ry - sy, tz + rz
					- sz)
					&& ((world.getBlockState(new BlockPos(tx, ty - 3, tz))
							.getBlock() == Blocks.SOUL_SAND && ry == -1)
							|| (world.getBlockState(
									new BlockPos(tx, ty + 2 * ry, tz))
									.getBlock() == Blocks.SOUL_SAND && ry == 1) || (ry == 0
							&& world.getBlockState(
									new BlockPos(tx + 2 * rx, ty, tz + 2 * rz))
									.getBlock() == Blocks.SOUL_SAND && world
							.getBlockState(
									new BlockPos(tx + 2 * rx, ty - 1, tz + 2
											* rz)).getBlock() == Blocks.SOUL_SAND))) {
				/*
				 * && (world .getBlockState( new BlockPos(tx + rx, ty - 2, tz +
				 * rz)) .getBlock() == Blocks.SOUL_SAND && world .getBlockState(
				 * new BlockPos(tx + rx, ty + 1, tz + rz)) .getBlock() ==
				 * Blocks.SOUL_SAND)
				 */
				tx += rx;
				ty += ry;
				tz += rz;
				world.setBlockToAir(new BlockPos(tx, ty, tz));
				world.setBlockToAir(new BlockPos(tx, ty - 1, tz));
			} else {
				continue;
			}
		}

		if (loop >= maxLoop)
			carveToEntrance(world, tx, ty, tz, ox, sy + size / 2 - 2, oz);

		

		for (int i = 0; i < (int) (size / 25.0f); i++) {
			while (true) {
				int roomX = sx + random.nextInt(size);
				int roomY = sy + random.nextInt(size / 2);
				int roomZ = sz + random.nextInt(size);
				if (isPointInside(size, roomX - sx, roomY - sy, roomZ - sz)
						&& world.getBlockState(
								new BlockPos(roomX, roomY, roomZ)).getBlock() == Blocks.AIR
								&& world.getBlockState(
										new BlockPos(roomX, roomY-1, roomZ)).getBlock() == Blocks.SOUL_SAND) {
					generateRoom(world, roomX, roomY, roomZ, random);
					break;
				}
			}
		}

		size = size + 2;
		sx--;
		sy--;
		sz--;

		carveBlocks(world, ox, sy + size / 2 - 2, oz, 5);
		block = LovecraftMain.blockWeirdedBrick.getDefaultState();
		// Set back to bricks
		for (int y = 0; y < size / 2; y++) {
			for (int x = 0; x < size - y * 2; x++) {
				for (int z = 0; z < size - y * 2; z++) {
					if (world.getBlockState(
							new BlockPos(sx + x + y, sy + y, sz + z + y))
							.getBlock() == Blocks.SOUL_SAND) {
						world.setBlockState(new BlockPos(sx + x + y, sy + y, sz
								+ z + y), block);
					}
				}
			}
		}

		// Cobwebs
		block = Blocks.WEB.getDefaultState();
		for (int y = 0; y < size / 2; y++) {
			for (int x = 0; x < size - y * 2; x++) {
				for (int z = 0; z < size - y * 2; z++) {
					if (world.getBlockState(
							new BlockPos(sx + x + y, sy + y, sz + z + y))
							.getBlock() == Blocks.AIR) {
						if (random.nextFloat() > 0.96f) {
							world.setBlockState(new BlockPos(sx + x + y,
									sy + y, sz + z + y), block);
						}
					}
				}
			}
		}

	}

	private void putChest(World world, Random random, int x, int y, int z) {
		world.setBlockState(new BlockPos(x, y, z),
				Blocks.CHEST.getDefaultState());
		TileEntityChest chest = (TileEntityChest) world
				.getTileEntity(new BlockPos(x, y, z));
		if (chest != null) {
			chest.setLootTable(new ResourceLocation(
					"minecraft:chests/jungle_temple"), random.nextLong());
			chest.fillWithLoot(null);
			chest.setInventorySlotContents(random.nextInt(27),
					SpellHandler.getWeightedRandomSpell(random));
		}
	}

	private boolean isEven(int i) {
		return ((int) (i / 2) == ((float) i / 2.0f));
	}

	private void carveToEntrance(World world, int sx, int sy, int sz, int dx,
			int dy, int dz) {
		float x = sx;
		float y = sy;
		float z = sz;
		float slopeYbyX = ((float) (dy - sy)) / ((float) (dx - sx));
		float slopeZbyX = ((float) (dz - sz)) / ((float) (dx - sx));
		float stepSize = 0.1f;
		if (dx < sx)
			stepSize *= -1;
		else if(dx==sx) {
			int loop = 0;
			int loopMax = Math.round(200 / Math.abs(stepSize));
			while (sy<dy
					&& loop <= loopMax) {
				y += 0.1f;
				BlockPos pos = new BlockPos(Math.round(x), Math.round(y),
						Math.round(z));
				carveBlocks(world, Math.round(x), Math.round(y), Math.round(z), 2);
			}
			return;
		}
		int loop = 0;
		int loopMax = Math.round(200 / Math.abs(stepSize));
		while (!(Math.abs(x - dx) < 1.5 && Math.abs(y - dy) < 1.5 && Math.abs(z
				- dz) < 1.5)
				|| loop >= loopMax) {
			x += stepSize;
			y += stepSize * slopeYbyX;
			z += stepSize * slopeZbyX;
			BlockPos pos = new BlockPos(Math.round(x), Math.round(y),
					Math.round(z));
			// System.out.println(pos);
			carveBlocks(world, Math.round(x), Math.round(y), Math.round(z), 2);
		}
	}

	private boolean isPointInside(int size, int x, int y, int z) {
		return x > y && x < size - y && y < size / 2 && z > y && z < size - y
				&& y > 0;
	}

	private void generateRoom(World world, int x, int y, int z, Random random) {
		int sizeX = 4 + random.nextInt(3);
		int sizeY = 2 + random.nextInt(3);
		int sizeZ = 4 + random.nextInt(3);
		carveBlocks(world, x, y, z, sizeX, sizeY, sizeZ);
		while (true) {
			BlockPos pos = new BlockPos(x - sizeX / 2 + random.nextInt(sizeX),
					y - sizeY / 2, z - sizeZ / 2 + random.nextInt(sizeZ));
			if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
				putChest(world, random, pos.getX(), pos.getY(), pos.getZ());
				break;
			}
		}
	}

	private void carveBlocks(World world, int x, int y, int z, int thickness) {
		carveBlocks(world, x, y, z, thickness, thickness, thickness);
	}

	private void carveBlocks(World world, int x, int y, int z, int thickness1,
			int thickness2, int thickness3) {
		for (int ay = 0; ay < thickness2; ay++) {
			for (int ax = 0; ax < thickness1; ax++) {
				for (int az = 0; az < thickness3; az++) {
					if (world.getBlockState(
							new BlockPos(ax + x - (int) (thickness1 / 2), ay
									+ y - (int) (thickness2 / 2), az + z
									- (int) (thickness3 / 2))).getBlock() == Blocks.SOUL_SAND) {
						world.setBlockToAir(new BlockPos(ax + x
								- (int) (thickness1 / 2), ay + y
								- (int) (thickness2 / 2), az + z
								- (int) (thickness3 / 2)));
					}
				}
			}
		}
	}

}

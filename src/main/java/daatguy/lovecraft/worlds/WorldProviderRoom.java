package daatguy.lovecraft.worlds;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.core.LovecraftMain;

public class WorldProviderRoom extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		
		return LovecraftMain.ROOM_DIM_TYPE;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
	@Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false; //so why can it still rain...?
    }
	
	@Override
	public boolean canDoLightning(Chunk chunk) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float foo, float bar) { //the parameters are probably x and z coordinates, for biomes
        return new Vec3d(1D, 1D, .91D);
    }
	
	protected void generateLightBrightnessTable() {
		for (int i = 0; i < 8; i++) {
			this.lightBrightnessTable[i] = .5f;
		}
		for (int i = 8; i < 16; i++) {
			this.lightBrightnessTable[i] = 1f;
		}
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorRoom(this.world);
	}
}

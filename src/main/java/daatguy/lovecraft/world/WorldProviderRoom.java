package daatguy.lovecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import daatguy.lovecraft.core.LovecraftMain;
import daatguy.lovecraft.generator.ChunkGeneratorRoom;

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
        return new Vec3d(1D, 1D, 1D);
    }
	
	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColor(Entity entity, float partialTicks) {
		return new Vec3d(1D, 1D, 1D);
	}
	
	@Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        return null;
    }
	
	public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.0F;
    }
	
	protected void generateLightBrightnessTable() {
		for (int i = 0; i < 16; i++) {
			this.lightBrightnessTable[i] = 1f;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 0F;
    }
	
	public boolean canRespawnHere() {
        return false;
    }
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorRoom(this.world);
	}
}

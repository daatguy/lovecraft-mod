package daatguy.lovecraft.worlds;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
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
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorRoom(this.world);
	}
}

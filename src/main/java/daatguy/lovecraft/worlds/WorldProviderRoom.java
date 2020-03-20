package daatguy.lovecraft.worlds;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;

import daatguy.lovecraft.core.LovecraftMain;

public class WorldProviderRoom extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		
		return LovecraftMain.ROOM_DIM_TYPE;
	}
	
	@Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }
}

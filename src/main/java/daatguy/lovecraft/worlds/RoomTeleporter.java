package daatguy.lovecraft.worlds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

//my dumb ass managed to make this a Teleporter subclass originally lmao
public class RoomTeleporter implements ITeleporter {
	
	//these should probably be hardcoded somewhere else, like with the side length and elevation that result in them
	public static final int X = 4;
	public static final int Y = 101;
	public static final int Z = 4;
	
	public void placeEntity(World world, Entity entity, float yaw) {
		if (entity instanceof EntityPlayerMP) {
			((EntityPlayerMP)entity).connection.setPlayerLocation(X+.5, Y, Z+.5, 0, 0); //facing straight south
		} else {
			entity.setLocationAndAngles(X+.5, Y, Z+.5, 0, 0);
			entity.motionX = 0.0D;
            entity.motionY = 0.0D;
            entity.motionZ = 0.0D;
		}
	}

}

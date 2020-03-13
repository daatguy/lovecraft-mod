package daatguy.lovecraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import daatguy.lovecraft.container.ContainerAltar;
import daatguy.lovecraft.container.ContainerDesk;
import daatguy.lovecraft.tileentity.TileEntityAltar;

@SideOnly(Side.CLIENT)
public class GuiHandler implements IGuiHandler {

	public static final int GUI_ALTAR = 0;
	public static final int GUI_DESK = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		BlockPos pos = new BlockPos(x,y,z);
		TileEntity te = world.getTileEntity(pos);
		
		switch (ID) {
		case GUI_ALTAR:
			return new ContainerAltar(player.inventory, (TileEntityAltar)te);
		case GUI_DESK:
			return new ContainerDesk(player.inventory, new BlockPos(x,y,z));
		default:
			return null;
		}
		
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		BlockPos pos = new BlockPos(x,y,z);
		TileEntity te = world.getTileEntity(pos);
		
		switch (ID) {
		case GUI_ALTAR:
			return new GuiAltar(player.inventory, (TileEntityAltar)te);
		case GUI_DESK:
			return new GuiDesk(player.inventory, pos);
		default:
			return null;
		}
	}

}

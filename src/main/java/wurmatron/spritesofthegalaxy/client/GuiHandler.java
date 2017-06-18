package wurmatron.spritesofthegalaxy.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.GuiIncome;
import wurmatron.spritesofthegalaxy.client.gui.GuiInfo;
import wurmatron.spritesofthegalaxy.client.gui.GuiOverview;
import wurmatron.spritesofthegalaxy.client.gui.GuiStats;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int OVERVIEW = 0;
	public static final int INCOME = 1;
	public static final int STATS = 2;
	public static final int INFO = 3;

	@Nullable
	@Override
	public Object getServerGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new ContainerPlayer (player.inventory,false,player);
	}

	@Nullable
	@Override
	public Object getClientGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		LogHandler.info ("" + world.isRemote);
		switch (ID) {
			case (OVERVIEW):
				return new GuiOverview ((TileHabitatController) world.getTileEntity (new BlockPos (x,y,z)));
			case (INCOME):
				return new GuiIncome ((TileHabitatController) world.getTileEntity (new BlockPos (x,y,z)));
			case (STATS):
				return new GuiStats ((TileHabitatController) world.getTileEntity (new BlockPos (x,y,z)));
			case (INFO):
				return new GuiInfo ((TileHabitatController) world.getTileEntity (new BlockPos (x,y,z)));
			default:
				return null;
		}
	}
}

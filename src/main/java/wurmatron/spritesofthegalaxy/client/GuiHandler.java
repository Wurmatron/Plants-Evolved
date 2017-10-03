package wurmatron.spritesofthegalaxy.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.manage.GuiAgriculture;
import wurmatron.spritesofthegalaxy.client.gui.manage.GuiManage;
import wurmatron.spritesofthegalaxy.client.gui.overview.GuiOverview;
import wurmatron.spritesofthegalaxy.client.gui.storage.GuiStorage;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	public static final int OVERVIEW = 0;
	public static final int INCOME = 1;
	public static final int STATS = 2;
	public static final int INFO = 3;
	public static final int MANAGE = 4;
	public static final int AGRICULTURE = 5;
	public static final int MINES = 6;
	public static final int ENERGY_PRODUCTION = 7;
	public static final int MAGIC_PRODUCTION = 8;
	public static final int MOB_FARM = 9;
	public static final int LIQUID_FARM = 10;
	public static final int NURSERY = 11;
	public static final int RESEARCH_ARGICULTURE = 12;
	public static final int RESEARCH_INDUSTRY = 13;
	public static final int RESEARCH_ENERGY = 14;
	public static final int RESEARCH_RESEARCH = 15;
	public static final int RESEARCH_MAGIC = 16;
	public static final int RESEARCH_UNIQUE = 17;
	public static final int RESEARCH = 18;
	public static final int POPULATION = 19;
	public static final int STORAGE = 20;

	@Nullable
	@Override
	public Object getServerGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new ContainerPlayer (player.inventory,false,player);
	}

	@Nullable
	@Override
	public Object getClientGuiElement (int ID,EntityPlayer player,World world,int x,int y,int z) {
		switch (ID) {
			case (OVERVIEW):
				return new GuiOverview ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (INCOME):
				//				return new GuiIncome ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (STATS):
				//				return new GuiStats ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (INFO):
				//				return new GuiInfo ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (AGRICULTURE):
				return new GuiAgriculture ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (MINES):
				//				return new GuiMines ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (ENERGY_PRODUCTION):
				//				return new GuiEnergyProduction ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (MAGIC_PRODUCTION):
				//				return new GuiMagicProduction ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (MOB_FARM):
				//				return new GuiMobFarm ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (LIQUID_FARM):
				//				return new GuiLiquidFarm ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (NURSERY):
				//				return new GuiNursery ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_ARGICULTURE):
				//				return new GuiResearhAgriculture ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_INDUSTRY):
				//				return new GuiResearchIndustry ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_ENERGY):
				//				return new GuiResearchEnergy ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_RESEARCH):
				//				return new GuiResearchResearch ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_MAGIC):
				//				return new GuiResearchMagic ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_UNIQUE):
				//				return new GuiResearchUnique ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (MANAGE):
				return new GuiManage ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH):
				//				return new GuiResearch ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (POPULATION):
				//				return new GuiPopulation ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			case (STORAGE):
				return new GuiStorage ((TileHabitatCore) world.getTileEntity (new BlockPos (x,y,z)));
			default:
				return null;
		}
	}
}

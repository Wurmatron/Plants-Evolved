package wurmatron.spritesofthegalaxy.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.client.gui.manage.GuiManage;
import wurmatron.spritesofthegalaxy.client.gui.manage.GuiStorage;
import wurmatron.spritesofthegalaxy.client.gui.manage.GuiStructure;
import wurmatron.spritesofthegalaxy.client.gui.overview.GuiOverview;
import wurmatron.spritesofthegalaxy.client.gui.production.GuiProduction;
import wurmatron.spritesofthegalaxy.client.gui.research.GuiDiscover;
import wurmatron.spritesofthegalaxy.client.gui.research.GuiResearch;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

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
	public static final int RESEARCH_MANAGE = 21;
	public static final int PRODUCTION = 22;

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
				return new GuiOverview ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (INCOME):
				//				return new GuiIncome ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (STATS):
				//				return new GuiStats ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (INFO):
				//				return new GuiInfo ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (AGRICULTURE):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.AGRICULTURE);
			case (MINES):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.MINE);
			case (ENERGY_PRODUCTION):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.ENERGY);
			case (MAGIC_PRODUCTION):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.MAGIC);
			case (MOB_FARM):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.MOB);
			case (LIQUID_FARM):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.lIQUID);
			case (NURSERY):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.NURSERY);
			case (RESEARCH_ARGICULTURE):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.ARGICULTURE);
			case (RESEARCH_INDUSTRY):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.INDUSTRY);
			case (RESEARCH_ENERGY):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.ENERGY);
			case (RESEARCH_RESEARCH):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.RESEARCH);
			case (RESEARCH_MAGIC):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.MAGIC);
			case (RESEARCH_UNIQUE):
				return new GuiDiscover ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),ResearchType.UNIQUE);
			case (MANAGE):
				return new GuiManage ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH):
				return new GuiResearch ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (STORAGE):
				return new GuiStorage ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			case (RESEARCH_MANAGE):
				return new GuiStructure ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)),StructureType.RESEARCH);
			case (PRODUCTION):
				return new GuiProduction ((TileHabitatCore2) world.getTileEntity (new BlockPos (x,y,z)));
			default:
				return null;
		}
	}
}

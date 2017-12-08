package wurmatron.spritesofthegalaxy.common.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.blocks.BlockMutiBlock;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;
import wurmatron.spritesofthegalaxy.common.tileentity.TileOutput;

import java.util.HashMap;

public class MutiBlockHelper {

	private static final int[] validSizes = new int[] {9,7,5,3};

	public static int isValid (World world,BlockPos pos) {
		if (world != null && pos != null && world.getTileEntity (pos) instanceof TileHabitatCore)
			for (int size : validSizes)
				if (isValid (world,pos,size))
					return size;
		return 0;
	}

	private static boolean isValid (World world,BlockPos pos,int size) {
		int direction = size / 2;
		for (int x = 0; x <= direction; x++)
			for (int y = 0; y <= direction; y++)
				for (int z = 0; z <= direction; z++) {
					if (x == 0 && y == 0 && z == 0)
						continue;
					if (!isBlockValid (world,pos.add (x,y,z)) || !isBlockValid (world,pos.add (-x,y,z)) || !isBlockValid (world,pos.add (x,-y,z)) || !isBlockValid (world,pos.add (x,y,-z)) || !isBlockValid (world,pos.add (-x,-y,z)) && !isBlockValid (world,pos.add (-x,-y,-z)) || !isBlockValid (world,pos.add (-x,y,-z)) || !isBlockValid (world,pos.add (x,-y,-z)))
						return false;
				}
		return true;
	}

	private static boolean isBlockValid (World world,BlockPos pos) {
		Block block = world.getBlockState (pos).getBlock ();
		return block instanceof BlockMutiBlock;
	}

	public static void setTilesCore (World world,BlockPos pos,int size) {
		int direction = size / 2;
		for (int x = 0; x <= direction; x++)
			for (int y = 0; y <= direction; y++)
				for (int z = 0; z <= direction; z++) {
					setCore (world,pos.add (x,y,z),pos);
					setCore (world,pos.add (-x,y,z),pos);
					setCore (world,pos.add (x,-y,z),pos);
					setCore (world,pos.add (x,y,-z),pos);
					setCore (world,pos.add (-x,-y,z),pos);
					setCore (world,pos.add (-x,-y,-z),pos);
					setCore (world,pos.add (-x,y,-z),pos);
					setCore (world,pos.add (x,-y,z),pos);
				}
	}

	private static void setCore (World world,BlockPos pos,BlockPos core) {
		if (world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof TileMutiBlock) {
			TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (pos);
			if (tile != null)
				tile.setCore (core);
		}
	}

	public static void delTilesCore (World world,BlockPos pos,int size) {
		if (pos != null && size > 0) {
			int direction = size / 2;
			for (int x = 0; x <= direction; x++)
				for (int y = 0; y <= direction; y++)
					for (int z = 0; z <= direction; z++) {
						setCore (world,pos.add (x,y,z),null);
						setCore (world,pos.add (-x,y,z),null);
						setCore (world,pos.add (x,-y,z),null);
						setCore (world,pos.add (x,y,-z),null);
						setCore (world,pos.add (-x,-y,z),null);
						setCore (world,pos.add (-x,-y,-z),null);
						setCore (world,pos.add (-x,y,-z),null);
						setCore (world,pos.add (x,-y,z),null);
					}
			if (world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof TileHabitatCore) {
				TileHabitatCore tile = (TileHabitatCore) world.getTileEntity (pos);
				if (tile != null)
					tile.requestUpdate ();
			}
		}
	}

	public static boolean canBuildStructure (TileHabitatCore tile,IStructure structure,int currentTier,int nextTier) {
		return tile.getColonyValue (NBT.MINERALS) >= calcMineralsForStructure (structure,currentTier,nextTier,0) && hasRequiredResearch (tile,structure);
	}

	public static boolean hasRequiredResearch (TileHabitatCore tile,IStructure structure) {
		return ResearchHelper.hasResearch (tile,structure.getRequiredResearch ());
	}

	public static boolean canBuildStorageType (TileHabitatCore tile,StorageType type,int currentTier,int nextTier) {
		return tile.getColonyValue (NBT.MINERALS) >= calcMineralsForStorage (type,currentTier,nextTier,0);
	}

	public static boolean canBuildResearchType (TileHabitatCore tile,IResearch res,int currentTier,int nextTier) {
		return tile.getResearchPoints (res.getResearchTab ()) >= calcPointsForResearch (res,currentTier,nextTier);
	}

	public static int calcMineralsForStructure (IStructure structure,int currentTier,int nextTier,int researchLevel) {
		int amountNeeded = 0;
		for (int index = currentTier + 1; index <= nextTier; index++)
			amountNeeded += structure.getCost (researchLevel,index);
		return Math.abs (amountNeeded);
	}

	public static int calcMineralsForStorage (StorageType type,int currentTier,int nextTier,int researchLevel) {
		int amountNeeded = 0;
		for (int index = currentTier + 1; index <= nextTier; index++)
			amountNeeded += type.getCost () * nextTier;
		return Math.abs (amountNeeded);
	}

	public static int calcPointsForResearch (IResearch res,int currentTier,int nextTier) {
		int amountNeeded = 0;
		if (currentTier + 1 == nextTier)
			return res.getBaseResearchCost () * nextTier;
		for (int index = currentTier + 1; index <= nextTier; index++)
			amountNeeded += res.getBaseResearchCost () * nextTier;
		return Math.abs (amountNeeded);
	}

	public static int getStructureLevel (TileHabitatCore tile,IStructure structure) {
		if (structure != null && tile != null) {
			HashMap <IStructure, Integer> currentStructure = tile.getStructures ();
			if (currentStructure != null && currentStructure.size () > 0 && currentStructure.containsKey (structure))
				return currentStructure.get (structure);
		}
		return 0;
	}

	public static int getStorageLevel (TileHabitatCore tile,StorageType type) {
		if (type != null && tile != null) {
			HashMap <StorageType, Integer> currentStorage = tile.getStorage ();
			if (currentStorage != null && currentStorage.size () > 0 && currentStorage.containsKey (type))
				return currentStorage.get (type);
		}
		return 0;
	}

	public static int getResearchLevel (TileHabitatCore tile,IResearch research) {
		if (research != null && tile != null) {
			HashMap <IResearch, Integer> currentResearch = tile.getResearch ();
			if (currentResearch != null && currentResearch.size () > 0 && currentResearch.containsKey (research))
				return currentResearch.get (research);
		}
		return 0;
	}

	public static int getOutputLevel (TileHabitatCore tile,IOutput output) {
		if (output != null && tile != null) {
			HashMap <IOutput, Integer> currentOutput = tile.getOutputSettings ();
			if (currentOutput != null && currentOutput.size () > 0 && currentOutput.containsKey (output))
				return currentOutput.get (output);
		}
		return 0;
	}

	public static int calculateSellBack (int price) {
		return (int) (price * .25);
	}

	public static BlockPos findOutput (World world,TileHabitatCore tile) {
		if (isValid (world,tile.getPos (),tile.mutiBlockSize)) {
			int direction = tile.mutiBlockSize / 2;
			for (int x = 0; x <= direction; x++)
				for (int y = 0; y <= direction; y++)
					for (int z = 0; z <= direction; z++) {
						if (x == 0 && y == 0 && z == 0)
							continue;
						if (world.getTileEntity (tile.getPos ().add (x,y,z)) instanceof TileOutput)
							return tile.getPos ().add (x,y,z);
					}
			return null;
		}
		return null;
	}

	public static int getBuildTime (IStructure structure,int tier) {
		return structure.getBuildTime (tier);
	}


	public static int getResearchBonus (TileHabitatCore tile,IStructure structure) {
		return 0;
	}

	public static String getType (StorageType type) {
		if (type.getDisplayKey ().equalsIgnoreCase (NBT.MAX_MINERALS))
			return NBT.MINERALS;
		return type.getDisplayKey ();
	}

	public static void recalcStorage (TileHabitatCore tile) {
		if (tile != null && tile.getStorage ().size () > 0) {
			for (StorageType type : tile.getStorage ().keySet ())
				if (tile.getStorage ().get (type) != 0)
					tile.setColonyValue (type.getDisplayKey (),(int) (tile.getStorage ().get (type) * type.getScale ()));
		}
	}

	public static int getMinimumLevel (IStructure structure) {
		if (structure != null) {
			for (IStructure minStr : Settings.defaultStructures.keySet ())
				if (structure.getName ().equalsIgnoreCase (minStr.getName ()))
					return Settings.defaultStructures.get (minStr);
		}
		return 0;
	}

	public static int getRequiredPopulation (IStructure structure,int tier) {
		return (int) ((structure.getPopulationRequirment () * tier) * Settings.structurePopulationRequirement);
	}

	// TODO Gem / Magic Support
	public static int getMineralIncome (TileHabitatCore tile) {
		int amount = 0;
		for (IStructure structure : tile.getStructures ().keySet ())
			if (structure.getStructureType ().equals (StructureType.MINE) && structure instanceof IProduction)
				amount += ((IProduction) structure).getAmountPerTier (tile,tile.getStructures ().get (structure));
		return amount;
	}
}

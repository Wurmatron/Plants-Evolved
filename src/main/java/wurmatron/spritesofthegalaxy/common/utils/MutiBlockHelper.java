package wurmatron.spritesofthegalaxy.common.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.common.blocks.BlockMutiBlock;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	public static List <IStructure> filterStructures (HashMap <IStructure, Integer> structureList,StructureType type) {
		List <IStructure> filtered = new ArrayList <> ();
		for (IStructure structure : structureList.keySet ())
			if (structure.getStructureType () == type)
				filtered.add (structure);
		return filtered;
	}

	public static boolean canBuildStructure (TileHabitatCore tile,IStructure structure,int currentTier,int nextTier) {
		return tile.getMinerals () >= calcMineralsForStructure (structure,currentTier,nextTier,0);
	}

	public static boolean canBuildStorageType (TileHabitatCore tile,StorageType type,int currentTier,int nextTier) {
		return tile.getMinerals () >= calcMineralsForStorage (type,currentTier,nextTier,0);
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
			amountNeeded += type.getMineral () * nextTier;
		return Math.abs (amountNeeded);
	}

	public static void addStorageType (TileHabitatCore tile,StorageType type,int tier) {
		switch (type) {
			case POPULATION:
				tile.setMaxPopulation ((tile.getMaxPopulation () + (int) (tier * StorageType.POPULATION.getScale ())));
				break;
			case MINERAL:
				tile.addMaxMinerals ((int) (tier * StorageType.MINERAL.getScale ()));
		}
	}

	public static void removeStorageType (TileHabitatCore tile,StorageType type,int tier) {
		switch (type) {
			case POPULATION: {
				tile.removeStorageType (type,tier);
				break;
			}
			case MINERAL:
				tile.setMaxMinerals (tile.getMaxMinerals () - (int) (tier * StorageType.MINERAL.getScale ()));
		}
	}

	public static int getStructureLevel (TileHabitatCore tile, IStructure structure) {
		if (structure != null && tile != null) {
			HashMap <IStructure, Integer> currentStructure = tile.getStructures ();
			if (currentStructure != null && currentStructure.size () > 0 && currentStructure.containsKey (structure))
				return currentStructure.get (structure);
		}
		return 0;
	}

	public static int getStorageLevel (TileHabitatCore tile,StorageType type) {
		if (type != null && tile != null) {
			HashMap <StorageType, Integer> currentStorage = tile.getStorageData ();
			if (currentStorage != null && currentStorage.size () > 0 && currentStorage.containsKey (type))
				return currentStorage.get (type);
		}
		return 0;
	}

	public static int calculateSellBack(int price) {
		return (int) (price * .25);
	}
}

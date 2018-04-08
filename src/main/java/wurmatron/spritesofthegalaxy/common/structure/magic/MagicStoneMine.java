package wurmatron.spritesofthegalaxy.common.structure.magic;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class MagicStoneMine implements IStructure, IProduction, ITickStructure {

	@Override
	public String getName () {
		return "magicStone";
	}

	@Override
	public String getDisplayName () {
		return "Magic Stone Mine";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.MAGIC;
	}

	@Override
	public HashMap<IResearch, Integer> getRequiredResearch () {
		return null;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		if (structureTier == 0)
			return 100;
		return structureTier * 100;
	}

	@Override
	public void tickStructure (TileHabitatCore core,int tier) {
		core.addColonyValue (NBT.MAGIC,NBT.MAX_MAGIC,tier);
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 5;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return tier;
	}

	@Override
	public int getBuildTime (int tier) {
		return 50 * tier;
	}

	@Override
	public double getPopulationRequirment () {
		return 6;
	}

}

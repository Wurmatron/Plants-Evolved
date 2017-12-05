package wurmatron.spritesofthegalaxy.common.structure.mine;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.util.HashMap;

public class MineStructure implements IStructure, IProduction, ITickStructure {

	@Override
	public String getName () {
		return "mine";
	}

	@Override
	public String getDisplayName () {
		return "Basic Mine";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.MINE;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		return null;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		if (structureTier == 0)
			return 10;
		return structureTier * 10;
	}

	@Override
	public void tickStructure (TileHabitatCore2 core,int tier) {
		core.addColonyValue (NBT.MINERALS,NBT.MAX_MINERALS,tier * 5);
	}

	@Override
	public void addProduction (TileHabitatCore2 core,int structureTier) {
	}

	@Override
	public void removeProduction (TileHabitatCore2 core,int structureTier) {
	}

	@Override
	public int getAmountPerTier (TileHabitatCore2 core,int tier) {
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
		return 10 * tier;
	}

	@Override
	public double getPopulationRequirment () {
		return 4;
	}
}

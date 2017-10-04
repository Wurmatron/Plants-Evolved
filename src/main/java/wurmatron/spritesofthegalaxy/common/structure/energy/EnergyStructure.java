package wurmatron.spritesofthegalaxy.common.structure.energy;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class EnergyStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "fuel";
	}

	@Override
	public String getDisplayName () {
		return "Fuel Power";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
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
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addEnergy (structureTier);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeEnergy (structureTier);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return 0;
	}

	@Override
	public int getBaseBuildTime (int tier) {
		return 10 * tier;
	}
}

package io.wurmatron.plants.common.structure.research;

import io.wurmatron.plants.api.mutiblock.*;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.api.research.ResearchType;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class ResearchStructure implements IStructure, IProduction, ITickStructure {

	@Override
	public String getName () {
		return "research";
	}

	@Override
	public String getDisplayName () {
		return "Basic Research";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.RESEARCH;
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
	public void tickStructure (TileHabitatCore core,int tier) {
		for (ResearchType type : ResearchType.values ())
			core.setResearchPoints (type,core.getResearchPoints (type) + tier);
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
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
		return tier;
	}

	@Override
	public int getBuildTime (int tier) {
		return 10 * tier;
	}

	@Override
	public double getPopulationRequirment () {
		return 8;
	}
}

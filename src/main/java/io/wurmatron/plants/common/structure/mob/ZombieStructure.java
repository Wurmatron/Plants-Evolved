package io.wurmatron.plants.common.structure.mob;

import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StructureType;
import io.wurmatron.plants.api.research.IResearch;

import java.util.HashMap;

public class ZombieStructure implements IStructure {

	@Override
	public String getName () {
		return "zombie";
	}

	@Override
	public String getDisplayName () {
		return "zombie";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.MOB;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		return null;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 100;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return tier;
	}

	@Override
	public int getBuildTime (int tier) {
		return tier * 100;
	}

	@Override
	public double getPopulationRequirment () {
		return 1;
	}
}

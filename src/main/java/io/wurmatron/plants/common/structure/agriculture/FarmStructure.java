package io.wurmatron.plants.common.structure.agriculture;

import io.wurmatron.plants.api.mutiblock.EnumProductionType;
import io.wurmatron.plants.api.mutiblock.IProduction;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StructureType;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class FarmStructure implements IStructure, IProduction {

	@Override
	public String getName () {
		return "farm";
	}

	@Override
	public String getDisplayName () {
		return "Farm Land";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.AGRICULTURE;
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
		core.addColonyValue (NBT.FOOD,structureTier * 4 * Settings.populationFoodRequirement);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeColonyValue (NBT.FOOD,structureTier * Settings.populationFoodRequirement);
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
		return 1;
	}
}

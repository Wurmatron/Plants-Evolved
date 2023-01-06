package io.wurmatron.plants.common.structure.energy;

import io.wurmatron.plants.api.mutiblock.*;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class SolarStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "solar";
	}

	@Override
	public String getDisplayName () {
		return "Solar";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.solar,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 5;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addColonyValue (NBT.ENERGY,structureTier * 5);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeColonyValue (NBT.ENERGY,structureTier * 5);
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
		return 0;
	}

	@Override
	public int getBuildTime (int tier) {
		return 10 * tier;
	}

	@Override
	public double getPopulationRequirment () {
		return 0;
	}
}

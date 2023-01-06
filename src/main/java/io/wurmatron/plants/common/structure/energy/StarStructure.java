package io.wurmatron.plants.common.structure.energy;

import io.wurmatron.plants.api.mutiblock.*;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class StarStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "star";
	}

	@Override
	public String getDisplayName () {
		return "Star";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.star,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 5000;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addColonyValue (NBT.ENERGY,structureTier * 5000);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeColonyValue (NBT.ENERGY,structureTier * 5000);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 5000;
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
		return 100 * tier;
	}

	@Override
	public double getPopulationRequirment () {
		return 1;
	}
}

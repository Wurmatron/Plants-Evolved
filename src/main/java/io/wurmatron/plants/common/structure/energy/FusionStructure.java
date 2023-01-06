package io.wurmatron.plants.common.structure.energy;

import io.wurmatron.plants.api.mutiblock.*;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class FusionStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "fusion";
	}

	@Override
	public String getDisplayName () {
		return "Fusion";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.fusion,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 100000;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addColonyValue (NBT.ENERGY,structureTier * 100);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeColonyValue (NBT.ENERGY,structureTier * 100);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 100;
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
		return 1;
	}
}

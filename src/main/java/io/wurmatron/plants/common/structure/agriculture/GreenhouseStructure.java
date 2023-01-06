package io.wurmatron.plants.common.structure.agriculture;

import io.wurmatron.plants.api.mutiblock.EnumProductionType;
import io.wurmatron.plants.api.mutiblock.IProduction;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StructureType;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class GreenhouseStructure implements IStructure, IProduction {

	@Override
	public String getName () {
		return "greenhouse";
	}

	@Override
	public String getDisplayName () {
		return "Greenhouse";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.AGRICULTURE;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.greenHouse,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		if (structureTier == 0)
			return 100;
		return structureTier * 100;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addColonyValue (NBT.FOOD,structureTier * 10);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeColonyValue (NBT.FOOD,structureTier * 10);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 10;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return 2 * tier;
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

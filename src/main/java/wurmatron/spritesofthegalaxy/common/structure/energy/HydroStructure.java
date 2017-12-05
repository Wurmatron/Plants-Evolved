package wurmatron.spritesofthegalaxy.common.structure.energy;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.util.HashMap;

public class HydroStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "hydro";
	}

	@Override
	public String getDisplayName () {
		return "Hydro";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.hydro,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 5;
	}

	@Override
	public void addProduction (TileHabitatCore2 core,int structureTier) {
		core.addColonyValue (NBT.ENERGY,structureTier * 5);
	}

	@Override
	public void removeProduction (TileHabitatCore2 core,int structureTier) {
		core.consumeColonyValue (NBT.ENERGY,structureTier * 5);
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

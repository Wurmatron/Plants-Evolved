package wurmatron.spritesofthegalaxy.common.structure.energy;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class WindStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "wind";
	}

	@Override
	public String getDisplayName () {
		return "Wind";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.wind,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 5;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addEnergy (structureTier * 5);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeEnergy (structureTier * 5);
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
	public int getBaseBuildTime (int tier) {
		return 10 * tier;
	}
}

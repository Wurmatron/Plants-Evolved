package wurmatron.spritesofthegalaxy.common.structure.energy;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class InterstellarStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "interstellar";
	}

	@Override
	public String getDisplayName () {
		return "....";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.interstellar,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 50000;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addEnergy (structureTier * 50000);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeEnergy (structureTier * 50000);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 50000;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return 0;
	}
}

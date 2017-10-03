package wurmatron.spritesofthegalaxy.common.structure.agriculture;

import wurmatron.spritesofthegalaxy.api.mutiblock.EnumProductionType;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

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
		HashMap <IResearch, Integer> req= new HashMap <> ();
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
		core.addFood (structureTier * 10);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.removeFood (structureTier * 10);
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
}

package wurmatron.spritesofthegalaxy.common.structure.agriculture;

import wurmatron.spritesofthegalaxy.api.mutiblock.EnumProductionType;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

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
	public void addProduction (TileHabitatCore2 core,int structureTier) {
		core.addColonyValue (NBT.FOOD,structureTier);
	}

	@Override
	public void removeProduction (TileHabitatCore2 core,int structureTier) {
		core.consumeColonyValue (NBT.FOOD,structureTier);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore2 core,int tier) {
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
}

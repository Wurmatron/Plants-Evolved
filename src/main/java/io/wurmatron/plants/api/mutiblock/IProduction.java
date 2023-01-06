package io.wurmatron.plants.api.mutiblock;

import io.wurmatron.plants.common.tileentity.TileHabitatCore;

public interface IProduction {

	void addProduction (TileHabitatCore core,int structureTier);

	void removeProduction (TileHabitatCore core,int structureTier);

	int getAmountPerTier (TileHabitatCore core,int tier);

	EnumProductionType getType ();
}
